package com.nomad.internethaber.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.nomad.internethaber.R;
import com.nomad.internethaber.model.NewsDetail;


public final class NewsDetailPagerFragment extends BaseFragment {
    private NewsDetail mNewsDetail;

    public static NewsDetailPagerFragment getInstance(NewsDetail newsDetail) {
        NewsDetailPagerFragment fragment = new NewsDetailPagerFragment();
        fragment.setNewsDetail(newsDetail);
        return fragment;
    }

    @NonNull
    @Override
    protected int getTitleResource() {
        return NO_ID;
    }

    @NonNull
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_news_detail_pager;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void setNewsDetail(NewsDetail newsDetail) {
        mNewsDetail = newsDetail;
    }
}
