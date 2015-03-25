package com.nomad.internethaber.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

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
import com.nomad.internethaber.view.DrawInsetsFrameLayout;
import com.squareup.otto.Subscribe;
import com.xgc1986.parallaxPagerTransformer.ParallaxPagerTransformer;

import java.util.ArrayList;

import butterknife.InjectView;

public final class NewsDetailActivity extends BaseActivity implements NewsDetailPagerAdapter.OnPagingListener, ActionClickListener, DrawInsetsFrameLayout.OnInsetsCallback {

    @InjectView(R.id.layout_news_detail_pagerview)
    protected ViewPager mViewPager;

    @InjectView(R.id.activity_toolbar)
    protected Toolbar mToolbar;

    @InjectView(R.id.layout_news_detail_inset_framelayout)
    protected DrawInsetsFrameLayout mInsetsFrameLayout;

    private NewsDetailPagerAdapter mPagerAdapter;
    private Interval mInterval;

    private NewsMoreAsyncTask mAsyncTask;
    private ArrayList<News> mList;
    private Category mCategory;

    private int temp;

    @NonNull
    @Override
    protected int getLayoutResource() {
        return R.layout.layout_news_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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

        mInsetsFrameLayout.setOnInsetsCallback(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                // TODO Use "NavUtils.navigateUpFromSameTask(this)" and restore states.
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        mPagerAdapter.setLoading(true);

        String from = mInterval.getFrom();
        String to = mInterval.getTo();
        String category = mCategory.getId();

        mAsyncTask = new NewsMoreAsyncTask();
        mAsyncTask.setFrom(from);
        mAsyncTask.setTo(to);
        mAsyncTask.setCategoryId(category);
        mAsyncTask.execute();
    }

    @Override
    public void onInsetsChanged(Rect insets) {
        mToolbar.setPadding(insets.left, insets.top, insets.right, insets.bottom);
    }
}
