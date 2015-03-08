package com.nomad.internethaber.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

import com.nomad.internethaber.R;

public final class StyledSwipeRefreshLayout extends SwipeRefreshLayout {

    public StyledSwipeRefreshLayout(Context context) {
        super(context);

        init();
    }

    public StyledSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        setColorScheme(R.color.color_home_primary_dark, R.color.color_home_accent);
    }
}
