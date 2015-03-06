package com.nomad.internethaber.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.nomad.internethaber.R;

import tr.xip.errorview.ErrorView;

public final class CompositePagingListView extends FrameLayout {

    private ErrorView mErrorView;
    private ResponsivePagingListView mListView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public CompositePagingListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CompositePagingListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CompositePagingListView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.segment_extended_listview, this);

        mErrorView = (ErrorView) findViewById(R.id.segment_extended_listview_error);
        mListView = (ResponsivePagingListView) findViewById(R.id.segment_extended_listview_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.segment_extended_refreshlayout);
    }

    public void showEmptyView() {
        mErrorView.setVisibility(VISIBLE);
    }

    public void hideEmptyView() {
        mErrorView.setVisibility(GONE);
    }

    public ErrorView getErrorView() {
        return mErrorView;
    }

    public ResponsivePagingListView getListView() {
        return mListView;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }
}
