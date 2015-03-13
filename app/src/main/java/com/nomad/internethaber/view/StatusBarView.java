package com.nomad.internethaber.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

public final class StatusBarView extends View {

    public StatusBarView(Context context) {
        super(context);
    }

    public StatusBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StatusBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getStatusBarHeight());
    }

    public int getStatusBarHeight() {
        int result = 0;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            return result;

        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
