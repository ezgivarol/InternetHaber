package com.nomad.internethaber.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public final class RectangularImageView extends ImageView {

    public RectangularImageView(Context context) {
        super(context);
    }

    public RectangularImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth() / 2);
    }
}