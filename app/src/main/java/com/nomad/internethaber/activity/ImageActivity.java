package com.nomad.internethaber.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.nomad.internethaber.R;
import com.nomad.internethaber.helper.SelectedImageHelper;

import butterknife.InjectView;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;

public final class ImageActivity extends BaseActivity {

    @InjectView(R.id.layout_image_imageview)
    protected ImageViewTouch mImageView;

    @NonNull
    @Override
    protected int getLayoutResource() {
        return R.layout.layout_image;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onSupportContentChanged() {
        super.onSupportContentChanged();

        Drawable photo = SelectedImageHelper.getPhoto();

        mImageView = (ImageViewTouch) findViewById(R.id.layout_image_imageview);
        mImageView.setImageDrawable(photo);
    }
}
