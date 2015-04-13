package com.nomad.internethaber.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nomad.internethaber.R;

public final class LoadingImageView extends FrameLayout {

    private ImageView mImageView;
    private ProgressBar mProgressBar;

    public LoadingImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode())
            init();
    }

    public LoadingImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (!isInEditMode())
            init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadingImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        if (!isInEditMode())
            init();
    }

    private void init() {
        inflate(getContext(), R.layout.segment_loading_imageview, this);

        mImageView = (ImageView) findViewById(R.id.segment_loading_imageview_imageview);
        mProgressBar = (ProgressBar) findViewById(R.id.segment_loading_imageview_progressbar);
    }

    public ImageView getImageView() {
        return mImageView;
    }
}
