package com.nomad.internethaber.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.listeners.ActionClickListener;
import com.nomad.internethaber.R;
import com.nomad.internethaber.adapter.NewsDetailPagerAdapter;
import com.nomad.internethaber.annotation.Platform;
import com.nomad.internethaber.bean.NewsResponseBean;
import com.nomad.internethaber.event.NewsMoreFailureResponseEvent;
import com.nomad.internethaber.event.NewsMoreNoItemResponseEvent;
import com.nomad.internethaber.event.NewsMoreResponseEvent;
import com.nomad.internethaber.event.NewsMoreSuccessResponseEvent;
import com.nomad.internethaber.helper.NavigationHelper;
import com.nomad.internethaber.model.Category;
import com.nomad.internethaber.model.Interval;
import com.nomad.internethaber.model.News;
import com.nomad.internethaber.task.NewsMoreAsyncTask;
import com.squareup.otto.Subscribe;
import com.xgc1986.parallaxPagerTransformer.ParallaxPagerTransformer;

import java.util.ArrayList;

import butterknife.InjectView;

public final class NewsDetailActivity extends BaseActivity implements NewsDetailPagerAdapter.OnPagingListener, ActionClickListener {

    @InjectView(R.id.layout_news_detail_pagerview)
    protected ViewPager mViewPager;

    private NewsDetailPagerAdapter mPagerAdapter;
    private Interval mInterval;

    private NewsMoreAsyncTask mAsyncTask;
    private ArrayList<News> mList;
    private Category mCategory;

    @NonNull
    @Override
    protected int getLayoutResource() {
        return R.layout.layout_news_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mList = (ArrayList<News>) getIntent().getExtras().get("list");
        mCategory = (Category) getIntent().getExtras().get("category");

        mInterval = new Interval();


        mPagerAdapter = new NewsDetailPagerAdapter(getSupportFragmentManager());
        mPagerAdapter.setOnPagingListener(this);
        mPagerAdapter.setNewsList(mList);

        ParallaxPagerTransformer parallaxPagerTransformer = new ParallaxPagerTransformer(R.id.fragment_news_detail_pager_photo_view);

        int position = NavigationHelper.getSelectedNewsPosition();
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(position);
        mViewPager.setPageTransformer(true, parallaxPagerTransformer);
    }

    @Override
    public void onMorePageLoad(@NonNull int page) {
        mPagerAdapter.setLoading(true);

        mInterval.calculate(page);
        String from = mInterval.getFrom();
        String to = mInterval.getTo();
        String category = mCategory.getId();

        mAsyncTask = new NewsMoreAsyncTask();
        mAsyncTask.setFrom(from);
        mAsyncTask.setTo(to);
        mAsyncTask.setCategoryId(category);
        mAsyncTask.execute();
    }

    @Platform(device = Platform.Device.BOTH)
    @Subscribe
    public void onNewsMoreResponseEvent(NewsMoreResponseEvent event) {
        mPagerAdapter.setLoading(false);
    }

    @Platform(device = Platform.Device.BOTH)
    @Subscribe
    public void onNewsMoreSuccessResponseEvent(NewsMoreSuccessResponseEvent event) {
        NewsResponseBean bean = event.getBean();
        ArrayList<News> news = bean.getNews();

        mPagerAdapter.addMoreItems(news);
    }

    @Platform(device = Platform.Device.BOTH)
    @Subscribe
    public void onNewsMoreFailureResponseEvent(NewsMoreFailureResponseEvent event) {
        Snackbar
                .with(this)
                .actionColor(R.color.color_home_accent)
                .actionListener(this)
                .actionLabel("Retry")
                .text("Could not get more news")
                .animation(true)
                .duration(Snackbar.SnackbarDuration.LENGTH_INDEFINITE)
                .show(this);
    }

    @Platform(device = Platform.Device.BOTH)
    @Subscribe
    public void onNewsNoItemResponseEvent(NewsMoreNoItemResponseEvent event) {
        mPagerAdapter.setHasMoreItems(false);
    }

    @Override
    public void onActionClicked(Snackbar snackbar) {

    }
}
