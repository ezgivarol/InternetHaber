package com.nomad.internethaber.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.nomad.internethaber.R;
import com.nomad.internethaber.adapter.NewsListAdapter;
import com.nomad.internethaber.bean.NewsResponseBean;
import com.nomad.internethaber.event.NavigationItemSelectEvent;
import com.nomad.internethaber.event.NewsFailureResponseEvent;
import com.nomad.internethaber.event.NewsMoreFailureResponseEvent;
import com.nomad.internethaber.event.NewsMoreResponseEvent;
import com.nomad.internethaber.event.NewsMoreSuccessResponseEvent;
import com.nomad.internethaber.event.NewsNoItemResponseEvent;
import com.nomad.internethaber.event.NewsResponseEvent;
import com.nomad.internethaber.event.NewsSelectEvent;
import com.nomad.internethaber.event.NewsSuccessResponseEvent;
import com.nomad.internethaber.model.Category;
import com.nomad.internethaber.model.News;
import com.nomad.internethaber.model.Range;
import com.nomad.internethaber.provider.BusProvider;
import com.nomad.internethaber.task.NewsAsyncTask;
import com.nomad.internethaber.task.NewsMoreAsyncTask;
import com.nomad.internethaber.util.ThreadUtils;
import com.nomad.internethaber.view.StyledSwipeRefreshLayout;
import com.paging.listview.PagingListView;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.InjectView;
import butterknife.OnItemClick;

public final class NewsFragment extends BaseFragment implements PagingListView.Pagingable, SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.fragment_news_listview)
    protected PagingListView mListView;

    @InjectView(R.id.fragment_news_swipe_refresh_layout)
    protected StyledSwipeRefreshLayout mRefreshLayout;

    private NewsListAdapter mAdapter;

    private NewsAsyncTask mNewsAsyncTask;
    private NewsMoreAsyncTask mNewsMoreAsyncTask;

    private Range mRange;
    private Category mCategory;

    @NonNull
    @Override
    protected int getTitleResource() {
        return NO_ID;
    }

    @NonNull
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_news;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        mRange = new Range();

        mRefreshLayout.setOnRefreshListener(this);
        mListView.setPagingableListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ThreadUtils.kill(mNewsAsyncTask);
        ThreadUtils.kill(mNewsMoreAsyncTask);
    }

    @OnItemClick(R.id.fragment_news_listview)
    public void onNewsItemClicked(int position) {
        News news = mAdapter.getItem(position);

        NewsSelectEvent event = new NewsSelectEvent();
        event.setNews(news);
        BusProvider.getInstance().post(event);
    }

    @Subscribe
    public void onNavigationItemSelectEvent(NavigationItemSelectEvent event) {
        mListView.setHasMoreItems(true);
        mListView.setIsLoading(true);
        mListView.setAdapter(null);

        ThreadUtils.kill(mNewsAsyncTask);
        ThreadUtils.kill(mNewsMoreAsyncTask);

        mRange.resetPage();
        mCategory = event.getCategory();

        String id = mCategory.getId();
        String from = mRange.getFrom();
        String to = mRange.getTo();

        mNewsAsyncTask = new NewsAsyncTask();
        mNewsAsyncTask.setCategoryId(id);
        mNewsAsyncTask.setFrom(from);
        mNewsAsyncTask.setTo(to);
        mNewsAsyncTask.execute();
    }

    @Subscribe
    public void onNewsResponseEvent(NewsResponseEvent event) {
        mListView.setIsLoading(false);

        mRefreshLayout.setRefreshing(false);
    }

    @Subscribe
    public void onNewsSuccessResponseEvent(NewsSuccessResponseEvent event) {
        NewsResponseBean bean = event.getBean();
        ArrayList<News> news = bean.getNews();

        mAdapter = new NewsListAdapter(getContext(), news);
        mListView.setAdapter(mAdapter);
        mListView.setHasMoreItems(true);
    }

    @Subscribe
    public void onNewsFailureSuccessResponseEvent(NewsFailureResponseEvent event) {
        // TODO Set empty view.
    }


    @Override
    public void onLoadMoreItems() {
        mListView.setIsLoading(true);
        mListView.setHasMoreItems(true);

        mRange.nextPage();

        String id = mCategory.getId();
        String from = mRange.getFrom();
        String to = mRange.getTo();

        mNewsMoreAsyncTask = new NewsMoreAsyncTask();
        mNewsMoreAsyncTask.setCategoryId(id);
        mNewsMoreAsyncTask.setFrom(from);
        mNewsMoreAsyncTask.setTo(to);
        mNewsMoreAsyncTask.execute();
    }

    @Subscribe
    public void onNewsMoreResponseEvent(NewsMoreResponseEvent event) {
        mListView.setIsLoading(false);
        // TODO Stop pre-loader animating.
    }

    @Subscribe
    public void onNewsMoreSuccessResponseEvent(NewsMoreSuccessResponseEvent event) {
        NewsResponseBean bean = event.getBean();
        ArrayList<News> news = bean.getNews();

        mAdapter.addMoreItems(news);
    }

    @Subscribe
    public void onNewsMoreFailureSuccessResponseEvent(NewsMoreFailureResponseEvent event) {
        // TODO Set empty view.
    }

    @Subscribe
    public void onNewsNoItemResponseEvent(NewsNoItemResponseEvent event) {
        NewsResponseBean bean = event.getBean();
        ArrayList<News> news = bean.getNews();

        mAdapter.addMoreItems(news);
        mListView.setHasMoreItems(false);
    }

    @Override
    public void onRefresh() {
        ThreadUtils.kill(mNewsAsyncTask);
        ThreadUtils.kill(mNewsMoreAsyncTask);

        mRange.resetPage();

        String id = mCategory.getId();
        String from = mRange.getFrom();
        String to = mRange.getTo();

        mNewsAsyncTask = new NewsAsyncTask();
        mNewsAsyncTask.setCategoryId(id);
        mNewsAsyncTask.setFrom(from);
        mNewsAsyncTask.setTo(to);
        mNewsAsyncTask.execute();
    }

}
