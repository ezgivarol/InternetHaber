package com.nomad.internethaber.fragment;

import android.support.annotation.NonNull;

import com.nomad.internethaber.R;
import com.nomad.internethaber.event.NewsDetailSuccessResponseEvent;
import com.nomad.internethaber.event.NewsSelectEvent;
import com.nomad.internethaber.model.News;
import com.nomad.internethaber.task.NewsDetailAsyncTask;
import com.squareup.otto.Subscribe;

public final class NewsDetailFragment extends BaseFragment {

    private NewsDetailAsyncTask mAsyncTask;

    @NonNull
    @Override
    protected int getTitleResource() {
        return NO_ID;
    }

    @NonNull
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_news_detail;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mAsyncTask.cancel(true);
    }

    @Subscribe
    public void onNewsSelectEvent(NewsSelectEvent event) {
        News news = event.getNews();

        String id = news.getId();
        String type = news.getType();

        mAsyncTask = new NewsDetailAsyncTask();
        mAsyncTask.setNewsId(id);
        mAsyncTask.setNewsType(type);
        mAsyncTask.execute();
    }

    @Subscribe
    public void onNewsDetailSuccessResponseEvent(NewsDetailSuccessResponseEvent event) {


    }

}
