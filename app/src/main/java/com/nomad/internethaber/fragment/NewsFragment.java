package com.nomad.internethaber.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.nomad.internethaber.R;
import com.nomad.internethaber.adapter.NewsListAdapter;
import com.nomad.internethaber.bean.NewsResponseBean;
import com.nomad.internethaber.event.NavigationItemSelectEvent;
import com.nomad.internethaber.event.NewsSelectEvent;
import com.nomad.internethaber.event.NewsSuccessResponseEvent;
import com.nomad.internethaber.helper.PaginationHelper;
import com.nomad.internethaber.helper.PaginationHelper.Range;
import com.nomad.internethaber.model.Category;
import com.nomad.internethaber.model.News;
import com.nomad.internethaber.provider.BusProvider;
import com.nomad.internethaber.task.NewsAsyncTask;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.InjectView;
import butterknife.OnItemClick;

public final class NewsFragment extends BaseFragment {

    @InjectView(R.id.fragment_news_listview)
    protected ListView mListView;

    private NewsListAdapter mAdapter;

    private PaginationHelper mPaginationHelper;
    private NewsAsyncTask mAsyncTask;

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

        mPaginationHelper = new PaginationHelper();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mAsyncTask.cancel(true);
    }

    @Subscribe
    public void onNavigationItemSelectEvent(NavigationItemSelectEvent event) {
        Range range = mPaginationHelper.getRange();
        Category category = event.getCategory();

        String id = category.getId();
        String from = range.getFrom();
        String to = range.getTo();

        mAsyncTask = new NewsAsyncTask();
        mAsyncTask.setCategoryId(id);
        mAsyncTask.setFrom(from);
        mAsyncTask.setTo(to);
        mAsyncTask.execute();
    }

    @Subscribe
    public void onNewsSuccessResponseEvent(NewsSuccessResponseEvent event) {
        NewsResponseBean bean = event.getBean();
        ArrayList<News> news = bean.getNews();

        mAdapter = new NewsListAdapter(getContext(), news);
        mListView.setAdapter(mAdapter);
    }

    @OnItemClick(R.id.fragment_news_listview)
    public void onNewsItemClicked(int position) {
        News news = mAdapter.getItem(position);

        NewsSelectEvent event = new NewsSelectEvent();
        event.setNews(news);
        BusProvider.getInstance().post(event);
    }
}
