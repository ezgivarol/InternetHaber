package com.nomad.internethaber.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.nomad.internethaber.R;

public class HeaderView extends LinearLayout {

    public HeaderView(Context context) {
        super(context);

        if (!isInEditMode())
            init();
    }

    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode())
            init();
    }

    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (!isInEditMode())
            init();
    }

    private void init() {
        inflate(getContext(), R.layout.segment_header, this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return true;
    }

}
