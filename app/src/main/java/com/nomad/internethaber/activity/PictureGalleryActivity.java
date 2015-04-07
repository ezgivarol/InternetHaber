package com.nomad.internethaber.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.Spinner;

import com.nomad.internethaber.R;

import butterknife.InjectView;

public final class PictureGalleryActivity extends BaseActivity {

    @InjectView(R.id.layout_picture_gallery_toolbar)
    protected Toolbar mToolbar;

    @InjectView(R.id.layout_picture_gallery_spinner)
    protected Spinner mSpinner;

    @InjectView(R.id.layout_picture_gridview)
    protected ViewPager mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    protected int getLayoutResource() {
        return R.layout.layout_picture_gallery;
    }

}
