package com.nomad.internethaber.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.listeners.ActionClickListener;
import com.nomad.internethaber.R;
import com.nomad.internethaber.adapter.PictureGalleryAdapter;
import com.nomad.internethaber.model.Range;
import com.nomad.internethaber.view.CompositePagingListView;
import com.paging.listview.PagingListView;
import com.smartadserver.android.library.model.SASAdElement;
import com.smartadserver.android.library.ui.SASAdView;

import butterknife.InjectView;
import tr.xip.errorview.RetryListener;

public class PictureGalleryFragment extends BaseFragment implements PagingListView.Pagingable, SwipeRefreshLayout.OnRefreshListener, RetryListener, ActionClickListener {

    @InjectView(R.id.fragment_picture_gallery_composite_listview)
    protected CompositePagingListView mListView;

    private Range mRange;

    private PictureGalleryAdapter mAdapter;

    @Override
    public void onActionClicked(Snackbar snackbar) {

    }

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
    public void onRefresh() {

    }

    @Override
    public void onLoadMoreItems() {

    }

    @Override
    public void onRetry() {

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRange = new Range();

        mListView.getSwipeRefreshLayout().setOnRefreshListener(this);
        mListView.getListView().setPagingableListener(this);
        mListView.getErrorView().setOnRetryListener(this);

    }

}
