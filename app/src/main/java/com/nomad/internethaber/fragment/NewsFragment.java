package com.nomad.internethaber.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Toast;

import com.nomad.internethaber.R;
import com.nomad.internethaber.adapter.NewsListAdapter;
import com.nomad.internethaber.annotation.Platform;
import com.nomad.internethaber.bean.NewsResponseBean;
import com.nomad.internethaber.event.NavigationItemSelectEvent;
import com.nomad.internethaber.event.NewsFailureResponseEvent;
import com.nomad.internethaber.event.NewsItemClickEventOnPhone;
import com.nomad.internethaber.event.NewsItemClickEventOnTablet;
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
import com.nomad.internethaber.view.CompositePagingListView;
import com.nomad.internethaber.view.StyledSwipeRefreshLayout;
import com.paging.listview.PagingListView;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.InjectView;
import tr.xip.errorview.RetryListener;

public final class NewsFragment extends BaseFragment implements PagingListView.Pagingable, SwipeRefreshLayout.OnRefreshListener, RetryListener {

    @InjectView(R.id.fragment_news_composite_listview)
    protected CompositePagingListView mListView;

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
        mListView.getListView().setPagingableListener(this);
        mListView.getErrorView().setOnRetryListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ThreadUtils.kill(mNewsAsyncTask);
        ThreadUtils.kill(mNewsMoreAsyncTask);
    }

    @Platform(device = Platform.Device.PHONE)
    @Subscribe
    public void onNewsItemClickedOnPhone(NewsItemClickEventOnPhone event) {
        Toast.makeText(getContext(), event.getPosition() + "", Toast.LENGTH_SHORT).show();
    }

    @Platform(device = Platform.Device.TABLET)
    @Subscribe
    public void onNewsItemClickedOnTablet(NewsItemClickEventOnTablet event) {
        int position = event.getPosition();
        News news = mAdapter.getItem(position);

        NewsSelectEvent eventNewsSelect = new NewsSelectEvent();
        eventNewsSelect.setNews(news);
        BusProvider.getInstance().post(eventNewsSelect);
    }

    @Platform(device = Platform.Device.BOTH)
    @Subscribe
    public void onNavigationItemSelectEvent(NavigationItemSelectEvent event) {
        mListView.getListView().setHasMoreItems(true);
        mListView.getListView().setIsLoading(true);
        mListView.getListView().setAdapter(null);
        mListView.hideEmptyView();

        mRefreshLayout.setEnabled(true);

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

    @Platform(device = Platform.Device.BOTH)
    @Subscribe
    public void onNewsResponseEvent(NewsResponseEvent event) {
        mListView.getListView().setIsLoading(false);

        mRefreshLayout.setRefreshing(false);
    }

    @Platform(device = Platform.Device.BOTH)
    @Subscribe
    public void onNewsSuccessResponseEvent(NewsSuccessResponseEvent event) {
        NewsResponseBean bean = event.getBean();
        ArrayList<News> news = bean.getNews();

        mAdapter = new NewsListAdapter(getContext(), news);
        mListView.getListView().setAdapter(mAdapter);
        mListView.getListView().setHasMoreItems(true);
    }

    @Platform(device = Platform.Device.BOTH)
    @Subscribe
    public void onNewsFailureSuccessResponseEvent(NewsFailureResponseEvent event) {
        mListView.getListView().setIsLoading(false);
        mListView.getListView().setHasMoreItems(false);
        mListView.showEmptyView();

        mRefreshLayout.setEnabled(false);
    }


    @Platform(device = Platform.Device.BOTH)
    @Override
    public void onLoadMoreItems() {
        mListView.getListView().setIsLoading(true);
        mListView.getListView().setHasMoreItems(true);

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

    @Platform(device = Platform.Device.BOTH)
    @Subscribe
    public void onNewsMoreResponseEvent(NewsMoreResponseEvent event) {
        mListView.getListView().setIsLoading(false);
    }

    @Platform(device = Platform.Device.BOTH)
    @Subscribe
    public void onNewsMoreSuccessResponseEvent(NewsMoreSuccessResponseEvent event) {
        NewsResponseBean bean = event.getBean();
        ArrayList<News> news = bean.getNews();

        mAdapter.addMoreItems(news);
    }

    @Platform(device = Platform.Device.BOTH)
    @Subscribe
    public void onNewsMoreFailureResponseEvent(NewsMoreFailureResponseEvent event) {
        // TODO Set empty view.
    }

    @Platform(device = Platform.Device.BOTH)
    @Subscribe
    public void onNewsNoItemResponseEvent(NewsNoItemResponseEvent event) {
        NewsResponseBean bean = event.getBean();
        ArrayList<News> news = bean.getNews();

        mAdapter.addMoreItems(news);
        mListView.getListView().setHasMoreItems(false);
    }

    @Platform(device = Platform.Device.BOTH)
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

    @Override
    public void onRetry() {
        mListView.hideEmptyView();
        mRefreshLayout.setEnabled(true);
        onRefresh();
    }
}
