package com.nomad.internethaber.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.nomad.internethaber.R;
import com.nomad.internethaber.annotation.Platform;
import com.nomad.internethaber.event.DrawerClosedEvent;
import com.nomad.internethaber.event.DrawerOpenedEvent;
import com.nomad.internethaber.event.NavigationItemSelectEvent;
import com.nomad.internethaber.event.PictureGalleryClickEvent;
import com.nomad.internethaber.event.VideoGalleryClickEvent;
import com.nomad.internethaber.fragment.NavigationDrawerFragment;
import com.nomad.internethaber.helper.NavigationHelper;
import com.nomad.internethaber.view.SubtitledToolbar;
import com.squareup.otto.Subscribe;

import butterknife.InjectView;

public final class HomeActivity extends BaseActivity {

    @InjectView(R.id.activity_toolbar)
    protected SubtitledToolbar mToolbar;

    @InjectView(R.id.activity_home_drawerlayout)
    protected DrawerLayout mDrawerLayout;

    private NavigationDrawerFragment mNavigationDrawerFragment;

    @NonNull
    @Override
    protected int getLayoutResource() {
        return R.layout.layout_home;
    }

    @Override
    public void onSupportContentChanged() {
        super.onSupportContentChanged();

        mToolbar = (SubtitledToolbar) findViewById(R.id.activity_toolbar);
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.activity_home_fragment_drawer);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setLogo(R.drawable.image_internet_haber);

        mNavigationDrawerFragment.setup(R.id.activity_home_fragment_drawer, mDrawerLayout, mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Toast.makeText(this, "Feature is not implemented yet.", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Subscribe
    public void onNavigationDrawerItemSelected(NavigationItemSelectEvent event) {
        String subtitle = event.getCategory().getName();
        NavigationHelper.setSubtitle(subtitle);

        mToolbar.setSubtitle(subtitle);

        // Removes titles
        mToolbar.setSubtitle("");

        invalidateOptionsMenu();
    }

    @Subscribe
    public void onDrawerOpenedEvent(DrawerOpenedEvent event) {
        mToolbar.setTitle(R.string.categories);
        mToolbar.setSubtitle("");

        // Removes titles
        mToolbar.setTitle("");
        mToolbar.setSubtitle("");

        invalidateOptionsMenu();
    }

    @Subscribe
    public void onDrawerClosedEvent(DrawerClosedEvent event) {
        mToolbar.setTitle(R.string.app_name);

        CharSequence subtitle = NavigationHelper.getSubtitle();
        mToolbar.setSubtitle(subtitle);

        // Removes titles
        mToolbar.setTitle("");
        mToolbar.setSubtitle("");

        invalidateOptionsMenu();
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }

    @Platform(device = Platform.Device.BOTH)
    @Subscribe
    public void onPictureGalleryClickedEvent(PictureGalleryClickEvent event) {
        Intent intent = new Intent(this, PhotoGalleryActivity.class);
        startActivity(intent);
    }

    @Platform(device = Platform.Device.BOTH)
    @Subscribe
    public void onVideoGalleryClickedEvent(VideoGalleryClickEvent event) {
        Intent intent = new Intent(this, VideoGalleryActivity.class);
        startActivity(intent);
    }
}