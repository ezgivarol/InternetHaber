package com.nomad.internethaber.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.nomad.internethaber.R;

public class VideoGalleryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    protected int getLayoutResource() {
        return R.layout.layout_video_gallery;
    }


}
