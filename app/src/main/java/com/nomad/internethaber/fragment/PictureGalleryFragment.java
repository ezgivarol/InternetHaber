package com.nomad.internethaber.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.listeners.ActionClickListener;
import com.nomad.internethaber.R;
import com.nomad.internethaber.activity.NewsDetailActivity;
import com.nomad.internethaber.adapter.NewsListAdapter;
import com.nomad.internethaber.annotation.Platform;
import com.nomad.internethaber.bean.NewsResponseBean;
import com.nomad.internethaber.event.NavigationItemSelectEvent;
import com.nomad.internethaber.event.NewsFailureResponseEvent;
import com.nomad.internethaber.event.NewsItemClickEventOnPhone;
import com.nomad.internethaber.event.NewsItemClickEventOnTablet;
import com.nomad.internethaber.event.NewsMoreFailureResponseEvent;
import com.nomad.internethaber.event.NewsMoreNoItemResponseEvent;
import com.nomad.internethaber.event.NewsMoreResponseEvent;
import com.nomad.internethaber.event.NewsMoreSuccessResponseEvent;
import com.nomad.internethaber.event.NewsResponseEvent;
import com.nomad.internethaber.event.NewsSelectEvent;
import com.nomad.internethaber.event.NewsSuccessResponseEvent;
import com.nomad.internethaber.helper.NavigationHelper;
import com.nomad.internethaber.model.Category;
import com.nomad.internethaber.model.News;
import com.nomad.internethaber.model.Range;
import com.nomad.internethaber.task.NewsAsyncTask;
import com.nomad.internethaber.task.NewsMoreAsyncTask;
import com.nomad.internethaber.util.ThreadUtils;
import com.nomad.internethaber.view.CompositePagingListView;
import com.paging.listview.PagingListView;
import com.smartadserver.android.library.SASBannerView;
import com.smartadserver.android.library.model.SASAdElement;
import com.smartadserver.android.library.ui.SASAdView;
import com.smartadserver.android.library.ui.SASCloseButton;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import tr.xip.errorview.RetryListener;

public class PictureGalleryFragment extends BaseFragment implements PagingListView.Pagingable, SwipeRefreshLayout.OnRefreshListener, RetryListener, ActionClickListener {

    @InjectView(R.id.fragment_picture_gallery_composite_listview)
    protected CompositePagingListView mListView;


    @InjectView(R.id.fragment_picture_banner)
    protected SASBannerView mBanner;

    @InjectView(R.id.fragment_picture_banner_closeButton)
    protected SASCloseButton mBannerCloseButton;

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
        return R.layout.fragment_picture_gallery;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRange = new Range();

        mListView.getSwipeRefreshLayout().setOnRefreshListener(this);
        mListView.getListView().setPagingableListener(this);
        mListView.getErrorView().setOnRetryListener(this);

        mBanner.loadAd(71463, "539772", 30304, true, "", new SASAdView.AdResponseHandler() {
            @Override
            public void adLoadingCompleted(final SASAdElement sasAdElement) {

                mBannerCloseButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBanner.close();
                        mBannerCloseButton.setVisibility(View.INVISIBLE);
                    }
                });

            }

            @Override
            public void adLoadingFailed(Exception e) {

            }
        });


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
        ThreadUtils.kill(mNewsAsyncTask);
        ThreadUtils.kill(mNewsMoreAsyncTask);

        int position = event.getPosition();
        NavigationHelper.setSelectedNewsPosition(position);

        List<News> items = mAdapter.getItems();
        ArrayList list = new ArrayList(items);

        Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
        intent.putExtra("list", list);
        intent.putExtra("category", mCategory);
        startActivity(intent);

    }

    @Platform(device = Platform.Device.TABLET)
    @Subscribe
    public void onNewsItemClickedOnTablet(NewsItemClickEventOnTablet event) {
        int position = event.getPosition();
        if (NavigationHelper.getSelectedNewsPosition() == position)
            return;

        NavigationHelper.setSelectedNewsPosition(position);
        News news = mAdapter.getItem(position);

        NewsSelectEvent eventNewsSelect = new NewsSelectEvent();
        eventNewsSelect.setNews(news);
        getBus().post(eventNewsSelect);
    }

    @Platform(device = Platform.Device.BOTH)
    @Subscribe
    public void onNavigationItemSelectEvent(NavigationItemSelectEvent event) {
        ThreadUtils.kill(mNewsAsyncTask);
        ThreadUtils.kill(mNewsMoreAsyncTask);

        mListView.hideEmptyView();

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

        mListView.getSwipeRefreshLayout().setRefreshing(false);
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
        mListView.showEmptyView();
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
        mListView.getListView().setIsLoading(false);
        mListView.getListView().setHasMoreItems(false);

        Snackbar
                .with(getContext())
                .actionColor(R.color.color_home_accent)
                .actionListener(this)
                .actionLabel("Retry")
                .text("Could not get more news")
                .animation(true)
                .attachToAbsListView(mListView.getListView())
                .duration(Snackbar.SnackbarDuration.LENGTH_INDEFINITE)
                .show(getActivity());
    }

    @Platform(device = Platform.Device.BOTH)
    @Subscribe
    public void onNewsNoItemResponseEvent(NewsMoreNoItemResponseEvent event) {
        mListView.getListView().setHasMoreItems(false);
        mListView.getListView().setIsLoading(false);
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

        onRefresh();
    }
    @Override
    public void onActionClicked(Snackbar snackbar) {
        mListView.getListView().setIsLoading(true);
        mListView.getListView().setHasMoreItems(true);

        String id = mCategory.getId();
        String from = mRange.getFrom();
        String to = mRange.getTo();

        mNewsMoreAsyncTask = new NewsMoreAsyncTask();
        mNewsMoreAsyncTask.setCategoryId(id);
        mNewsMoreAsyncTask.setFrom(from);
        mNewsMoreAsyncTask.setTo(to);
        mNewsMoreAsyncTask.execute();
    }
}
