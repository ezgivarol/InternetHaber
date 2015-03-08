package com.nomad.internethaber.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.nomad.internethaber.fragment.NewsDetailPagerFragment;
import com.nomad.internethaber.model.NewsDetail;

import java.util.ArrayList;

public final class NewsDetailPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<NewsDetail> mList;

    public NewsDetailPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        NewsDetail newsDetail = mList.get(position);
        return NewsDetailPagerFragment.getInstance(newsDetail);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    public void setNewsDetailList(ArrayList<NewsDetail> list) {
        mList = list;
    }
}
