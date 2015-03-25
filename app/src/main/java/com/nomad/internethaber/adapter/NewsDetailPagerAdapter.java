package com.nomad.internethaber.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nomad.internethaber.fragment.NewsDetailPagerFragment;
import com.nomad.internethaber.model.News;
import com.nomad.internethaber.model.Range;

import java.util.ArrayList;
import java.util.List;


public final class NewsDetailPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<News> mList;
    private OnPagingListener mListener;
    private boolean isLoading;
    private boolean hasMoreItems = true;

    public NewsDetailPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public NewsDetailPagerAdapter(@NonNull FragmentManager fm, @NonNull ArrayList<News> list) {
        super(fm);
        mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        checkPage(position);

        News news = mList.get(position);
        return NewsDetailPagerFragment.getInstance(news);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    private void checkPage(@NonNull int position) {
        if (!hasMoreItems)
            return;

        if (mListener == null)
            return;

        int diff = getCount() - position;
        if (!isLoading && diff < 5) {
            isLoading = true;

            int page = Range.turnPage();
            ;
            mListener.onMorePageLoad(page);
        }
    }

    public void addMoreItems(List<News> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void setNewsList(@NonNull ArrayList<News> list) {
        mList = list;
    }

    public void setOnPagingListener(@Nullable OnPagingListener listener) {
        mListener = listener;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setHasMoreItems(boolean hasMoreItems) {
        this.hasMoreItems = hasMoreItems;
    }

    public void setLoading(@NonNull boolean isLoading) {
        this.isLoading = isLoading;
    }

    public interface OnPagingListener {
        public void onMorePageLoad(@NonNull int position);
    }
}
