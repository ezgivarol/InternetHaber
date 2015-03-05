package com.nomad.internethaber.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.nomad.internethaber.R;
import com.paging.listview.PagingListView;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

public final class ExtendedPagingListView extends PagingListView {

    private SmoothProgressBar mSmoothProgressBar;

    public ExtendedPagingListView(Context context) {
        super(context);

        if (!isInEditMode()) {
            init();
        }
    }

    public ExtendedPagingListView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            init();
        }
    }

    public ExtendedPagingListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        if (!isInEditMode()) {
            init();
        }
    }

    private void init() {
        mSmoothProgressBar = (SmoothProgressBar) LayoutInflater.from(getContext()).inflate(R.layout.segment_loading, this, false);
        addHeaderView(mSmoothProgressBar);
    }

    public void startPreload() {
        mSmoothProgressBar.setVisibility(VISIBLE);
    }

    public void stopPreload() {
        mSmoothProgressBar.setVisibility(GONE);
    }
}
