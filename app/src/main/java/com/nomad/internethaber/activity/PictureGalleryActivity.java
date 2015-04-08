package com.nomad.internethaber.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.nomad.internethaber.R;
import com.nomad.internethaber.adapter.CategoriesSpinnerAdapter;
import com.nomad.internethaber.adapter.PhotosGridAdapter;
import com.nomad.internethaber.bean.PhotosResponseBean;
import com.nomad.internethaber.event.PhotosSuccessResponseEvent;
import com.nomad.internethaber.model.Category;
import com.nomad.internethaber.model.Photo;
import com.nomad.internethaber.task.PhotosAsyncTask;
import com.paging.gridview.PagingGridView;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.InjectView;
import butterknife.OnItemSelected;

public final class PictureGalleryActivity extends BaseActivity {

    @InjectView(R.id.layout_picture_gallery_toolbar)
    protected Toolbar mToolbar;

    @InjectView(R.id.layout_picture_gallery_spinner)
    protected Spinner mSpinner;

    @InjectView(R.id.layout_picture_gridview)
    protected PagingGridView mGridView;

    protected PhotosAsyncTask mPhotosAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(mToolbar);

        CategoriesSpinnerAdapter adapter = new CategoriesSpinnerAdapter(getBaseContext());
        mSpinner.setAdapter(adapter);
    }

    @NonNull
    @Override
    protected int getLayoutResource() {
        return R.layout.layout_picture_gallery;
    }

    @OnItemSelected(R.id.layout_picture_gallery_spinner)
    public void onCategorySelected(Spinner view, int position) {
        SpinnerAdapter adapter = view.getAdapter();
        Category category = (Category) adapter.getItem(position);

        String id = category.getId();

        mPhotosAsyncTask = new PhotosAsyncTask();
        mPhotosAsyncTask.setCategoryId(id);
        // TODO Set interval values.

        mPhotosAsyncTask.execute();
    }

    @Subscribe
    public void onPhotosResponseSuccessEvent(PhotosSuccessResponseEvent event) {
        PhotosResponseBean bean = event.getBean();
        ArrayList<Photo> photos = bean.getPhotos();

        PhotosGridAdapter adapter = new PhotosGridAdapter(getBaseContext(), photos);
        mGridView.setAdapter(adapter);
    }

}
