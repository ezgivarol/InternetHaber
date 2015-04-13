package com.nomad.internethaber.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.listeners.ActionClickListener;
import com.nomad.internethaber.R;
import com.nomad.internethaber.adapter.CategoriesSpinnerAdapter;
import com.nomad.internethaber.adapter.PhotosGridAdapter;
import com.nomad.internethaber.annotation.Platform;
import com.nomad.internethaber.bean.PhotosResponseBean;
import com.nomad.internethaber.event.PhotosMoreFailureResponseEvent;
import com.nomad.internethaber.event.PhotosMoreNoItemResponseEvent;
import com.nomad.internethaber.event.PhotosMoreResponseEvent;
import com.nomad.internethaber.event.PhotosMoreSuccessResponseEvent;
import com.nomad.internethaber.event.PhotosRequestEvent;
import com.nomad.internethaber.event.PhotosSuccessResponseEvent;
import com.nomad.internethaber.helper.NavigationHelper;
import com.nomad.internethaber.model.Category;
import com.nomad.internethaber.model.Photo;
import com.nomad.internethaber.model.Range;
import com.nomad.internethaber.task.PhotosAsyncTask;
import com.nomad.internethaber.task.PhotosMoreAsyncTask;
import com.nomad.internethaber.util.ThreadUtils;
import com.paging.gridview.PagingGridView;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.InjectView;
import butterknife.OnItemClick;
import butterknife.OnItemSelected;

public final class PhotoGalleryActivity extends BaseActivity implements PagingGridView.Pagingable, ActionClickListener {

    @InjectView(R.id.layout_picture_gallery_toolbar)
    protected Toolbar mToolbar;

    @InjectView(R.id.layout_picture_gallery_spinner)
    protected Spinner mSpinner;

    @InjectView(R.id.layout_picture_gridview)
    protected PagingGridView mGridView;

    private PhotosAsyncTask mPhotosAsyncTask;
    private PhotosMoreAsyncTask mPhotosMoreAsyncTask;
    private PhotosGridAdapter mAdapter;
    private Range mRange;
    private Category mCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(mToolbar);

        mRange = new Range();

        CategoriesSpinnerAdapter adapter = new CategoriesSpinnerAdapter(getBaseContext());
        mSpinner.setAdapter(adapter);

        mGridView.setPagingableListener(this);
    }

    @NonNull
    @Override
    protected int getLayoutResource() {
        return R.layout.layout_photo_gallery;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ThreadUtils.kill(mPhotosAsyncTask);
        ThreadUtils.kill(mPhotosMoreAsyncTask);
    }

    @OnItemSelected(R.id.layout_picture_gallery_spinner)
    public void onCategorySelected(Spinner view, int position) {
        ThreadUtils.kill(mPhotosAsyncTask);
        ThreadUtils.kill(mPhotosMoreAsyncTask);

        if (position == NavigationHelper.getSelectedCategoryPosition())
            return;

        NavigationHelper.setSelectedCategoryPosition(position);

        SpinnerAdapter adapter = view.getAdapter();
        Category category = (Category) adapter.getItem(position);

        mCategory = category;

        String id = category.getId();
        String to = mRange.getTo();
        String from = mRange.getFrom();

        mPhotosAsyncTask = new PhotosAsyncTask();
        mPhotosAsyncTask.setCategoryId(id);
        mPhotosAsyncTask.setTo(to);
        mPhotosAsyncTask.setFrom(from);
        mPhotosAsyncTask.execute();
    }

    @Platform(device = Platform.Device.BOTH)
    @Subscribe
    public void onPhotosRequestEvent(PhotosRequestEvent event) {
        mGridView.setIsLoading(true);
        mGridView.setHasMoreItems(true);
    }

    @Platform(device = Platform.Device.BOTH)
    @Subscribe
    public void onPhotosResponseSuccessEvent(PhotosSuccessResponseEvent event) {
        mGridView.setIsLoading(false);

        PhotosResponseBean bean = event.getBean();
        ArrayList<Photo> photos = bean.getPhotos();

        mAdapter = new PhotosGridAdapter(getBaseContext(), photos);
        mGridView.setAdapter(mAdapter);
    }

    @Platform(device = Platform.Device.BOTH)
    @Override
    public void onLoadMoreItems() {
        mGridView.setIsLoading(true);
        mGridView.setHasMoreItems(true);

        mRange.nextPage();

        String id = mCategory.getId();
        String to = mRange.getTo();
        String from = mRange.getFrom();

        mPhotosMoreAsyncTask = new PhotosMoreAsyncTask();
        mPhotosMoreAsyncTask.setCategoryId(id);
        mPhotosMoreAsyncTask.setTo(to);
        mPhotosMoreAsyncTask.setFrom(from);
        mPhotosMoreAsyncTask.execute();
    }

    @Platform(device = Platform.Device.BOTH)
    @Subscribe
    public void oPhotosMoreResponseEvent(PhotosMoreResponseEvent event) {
        mGridView.setIsLoading(false);
    }

    @Platform(device = Platform.Device.BOTH)
    @Subscribe
    public void onPhotosMoreSuccessResponseEvent(PhotosMoreSuccessResponseEvent event) {
        PhotosResponseBean bean = event.getBean();
        ArrayList<Photo> photos = bean.getPhotos();

        mAdapter.addMoreItems(photos);
    }

    @Platform(device = Platform.Device.BOTH)
    @Subscribe
    public void onPhotosMoreFailureResponseEvent(PhotosMoreFailureResponseEvent event) {
        mGridView.setIsLoading(false);
        mGridView.setHasMoreItems(false);

        Snackbar
                .with(getBaseContext())
                .actionColor(R.color.color_home_accent)
                .actionListener(this)
                .actionLabel("Retry")
                .text("Could not get more photos")
                .animation(true)
                .attachToAbsListView(mGridView)
                .duration(Snackbar.SnackbarDuration.LENGTH_INDEFINITE)
                .show(this);
    }

    @Platform(device = Platform.Device.BOTH)
    @Subscribe
    public void onPhotosNoItemResponseEvent(PhotosMoreNoItemResponseEvent event) {
        mGridView.setHasMoreItems(false);
        mGridView.setIsLoading(false);
    }

    @Override
    public void onActionClicked(Snackbar snackbar) {

    }

    @OnItemClick(R.id.layout_picture_gridview)
    public void onPictureSelected(int position) {
        Photo photo = mAdapter.getItem(position);

        Intent intent = new Intent(this, PhotoGalleryDetailActivity.class);
        intent.putExtra("photo", photo);
        startActivity(intent);
    }
}
