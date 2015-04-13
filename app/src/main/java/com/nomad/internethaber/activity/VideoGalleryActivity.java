package com.nomad.internethaber.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.listeners.ActionClickListener;
import com.nomad.internethaber.R;
import com.nomad.internethaber.adapter.CategoriesSpinnerAdapter;
import com.nomad.internethaber.adapter.VideosGridAdapter;
import com.nomad.internethaber.annotation.Platform;
import com.nomad.internethaber.bean.VideosResponseBean;
import com.nomad.internethaber.event.VideosMoreFailureResponseEvent;
import com.nomad.internethaber.event.VideosMoreNoItemResponseEvent;
import com.nomad.internethaber.event.VideosMoreResponseEvent;
import com.nomad.internethaber.event.VideosMoreSuccessResponseEvent;
import com.nomad.internethaber.event.VideosRequestEvent;
import com.nomad.internethaber.event.VideosSuccessResponseEvent;
import com.nomad.internethaber.helper.NavigationHelper;
import com.nomad.internethaber.model.Category;
import com.nomad.internethaber.model.Range;
import com.nomad.internethaber.model.Video;
import com.nomad.internethaber.task.VideosAsyncTask;
import com.nomad.internethaber.task.VideosMoreAsyncTask;
import com.nomad.internethaber.util.ThreadUtils;
import com.paging.gridview.PagingGridView;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.InjectView;
import butterknife.OnItemClick;
import butterknife.OnItemSelected;

public final class VideoGalleryActivity extends BaseActivity implements PagingGridView.Pagingable, ActionClickListener {

    @InjectView(R.id.layout_video_gallery_toolbar)
    protected Toolbar mToolbar;

    @InjectView(R.id.layout_video_gallery_spinner)
    protected Spinner mSpinner;

    @InjectView(R.id.layout_video_gallery_gridview)
    protected PagingGridView mGridView;

    private VideosAsyncTask mVideosAsyncTask;
    private VideosMoreAsyncTask mVideosMoreAsyncTask;
    private VideosGridAdapter mAdapter;
    private Range mRange;
    private Category mCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRange = new Range();

        CategoriesSpinnerAdapter adapter = new CategoriesSpinnerAdapter(getBaseContext());
        mSpinner.setAdapter(adapter);

        mGridView.setPagingableListener(this);
    }

    @NonNull
    @Override
    protected int getLayoutResource() {
        return R.layout.layout_video_gallery;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                // TODO Use "NavUtils.navigateUpFromSameTask(this)" and restore states.
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ThreadUtils.kill(mVideosAsyncTask);
        ThreadUtils.kill(mVideosMoreAsyncTask);
    }

    @OnItemSelected(R.id.layout_video_gallery_spinner)
    public void onCategorySelected(Spinner view, int position) {
        ThreadUtils.kill(mVideosAsyncTask);
        ThreadUtils.kill(mVideosMoreAsyncTask);

        if (position == NavigationHelper.getSelectedCategoryPosition())
            return;

        NavigationHelper.setSelectedCategoryPosition(position);

        SpinnerAdapter adapter = view.getAdapter();
        Category category = (Category) adapter.getItem(position);

        mCategory = category;

        String id = category.getId();
        String to = mRange.getTo();
        String from = mRange.getFrom();

        mVideosAsyncTask = new VideosAsyncTask();
        mVideosAsyncTask.setCategoryId(id);
        mVideosAsyncTask.setTo(to);
        mVideosAsyncTask.setFrom(from);
        mVideosAsyncTask.execute();
    }

    @Platform(device = Platform.Device.BOTH)
    @Subscribe
    public void onVideosRequestEvent(VideosRequestEvent event) {
        mGridView.setIsLoading(true);
        mGridView.setHasMoreItems(true);
    }

    @Platform(device = Platform.Device.BOTH)
    @Subscribe
    public void onVideosResponseSuccessEvent(VideosSuccessResponseEvent event) {
        mGridView.setIsLoading(false);

        VideosResponseBean bean = event.getBean();
        ArrayList<Video> videos = bean.getVideos();

        mAdapter = new VideosGridAdapter(getBaseContext(), videos);
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

        mVideosMoreAsyncTask = new VideosMoreAsyncTask();
        mVideosMoreAsyncTask.setCategoryId(id);
        mVideosMoreAsyncTask.setTo(to);
        mVideosMoreAsyncTask.setFrom(from);
        mVideosMoreAsyncTask.execute();
    }

    @Platform(device = Platform.Device.BOTH)
    @Subscribe
    public void oVideosMoreResponseEvent(VideosMoreResponseEvent event) {
        mGridView.setIsLoading(false);
    }

    @Platform(device = Platform.Device.BOTH)
    @Subscribe
    public void onVideosMoreSuccessResponseEvent(VideosMoreSuccessResponseEvent event) {
        VideosResponseBean bean = event.getBean();
        ArrayList<Video> videos = bean.getVideos();

        mAdapter.addMoreItems(videos);
    }

    @Platform(device = Platform.Device.BOTH)
    @Subscribe
    public void onVideosMoreFailureResponseEvent(VideosMoreFailureResponseEvent event) {
        mGridView.setIsLoading(false);
        mGridView.setHasMoreItems(false);

        Snackbar
                .with(getBaseContext())
                .actionColor(R.color.color_home_accent)
                .actionListener(this)
                .actionLabel("Retry")
                .text("Could not get more videos")
                .animation(true)
                .attachToAbsListView(mGridView)
                .duration(Snackbar.SnackbarDuration.LENGTH_INDEFINITE)
                .show(this);
    }

    @Platform(device = Platform.Device.BOTH)
    @Subscribe
    public void onVideosNoItemResponseEvent(VideosMoreNoItemResponseEvent event) {
        mGridView.setHasMoreItems(false);
        mGridView.setIsLoading(false);
    }

    @Override
    public void onActionClicked(Snackbar snackbar) {

    }

    @OnItemClick(R.id.layout_video_gallery_gridview)
    public void onVideoSelected(int position) {
        Video video = mAdapter.getItem(position);

        String url = video.getUrl();
        Uri uri = Uri.parse(url);

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "video/mp4");
        startActivity(intent);
    }
}
