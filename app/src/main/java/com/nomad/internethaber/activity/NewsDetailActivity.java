package com.nomad.internethaber.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.nomad.internethaber.R;
import com.nomad.internethaber.adapter.NewsDetailPagerAdapter;

import butterknife.InjectView;

public final class NewsDetailActivity extends BaseActivity {

    @InjectView(R.id.layout_news_detail_pagerview)
    protected ViewPager mViewPager;

    private NewsDetailPagerAdapter mPagerAdapter;

    @NonNull
    @Override
    protected int getLayoutResource() {
        return R.layout.layout_news_detail;
    }

    @Override
    public void onSupportContentChanged() {
        super.onSupportContentChanged();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
