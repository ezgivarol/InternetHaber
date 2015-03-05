package com.nomad.internethaber.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.nomad.internethaber.R;
import com.paging.listview.PagingListView;

public final class ExtendedPagingListView extends PagingListView {

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
        View view = LayoutInflater.from(getContext()).inflate(R.layout.segment_loading, this, false);

    }
}
