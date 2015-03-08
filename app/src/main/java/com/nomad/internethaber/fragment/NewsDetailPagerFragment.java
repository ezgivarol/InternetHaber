package com.nomad.internethaber.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import com.nomad.internethaber.R;
import com.nomad.internethaber.model.News;

import butterknife.InjectView;


public final class NewsDetailPagerFragment extends BaseFragment {

    @InjectView(R.id.test)
    protected TextView mTextView;

    private News mNews;

    public static NewsDetailPagerFragment getInstance(News news) {
        NewsDetailPagerFragment fragment = new NewsDetailPagerFragment();
        fragment.setNews(news);
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

        String title = mNews.getTitle();
        Spanned spannedTitle = Html.fromHtml(title);
        mTextView.setText(spannedTitle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void setNews(News news) {
        mNews = news;
    }
}
