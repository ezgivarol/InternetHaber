package com.nomad.internethaber.view;


import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nomad.internethaber.R;
import com.nomad.internethaber.event.PictureGalleryClickEvent;
import com.nomad.internethaber.event.VideoGalleryClickEvent;
import com.nomad.internethaber.provider.BusProvider;

public final class GalleryView extends LinearLayout implements View.OnClickListener {
    private TextView mPictureTextView;
    private TextView mVideoTextView;

    public GalleryView(Context context) {
        super(context);

        if (!isInEditMode())
            init();
    }

    public GalleryView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode())
            init();
    }

    public GalleryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (!isInEditMode())
            init();
    }

    private void init() {
        setOrientation(VERTICAL);
        inflate(getContext(), R.layout.segment_gallery, this);

        mPictureTextView = (TextView) findViewById(R.id.segment_gallery_picture_gallery_textview);
        mPictureTextView.setOnClickListener(this);

        mVideoTextView = (TextView) findViewById(R.id.segment_gallery_video_gallery_textview);
        mVideoTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.segment_gallery_picture_gallery_textview:
                PictureGalleryClickEvent pictureEvent = new PictureGalleryClickEvent();
                BusProvider.getInstance().post(pictureEvent);
                return;
            case R.id.segment_gallery_video_gallery_textview:
                VideoGalleryClickEvent videoEvent = new VideoGalleryClickEvent();
                BusProvider.getInstance().post(videoEvent);
                return;
        }
    }
}
