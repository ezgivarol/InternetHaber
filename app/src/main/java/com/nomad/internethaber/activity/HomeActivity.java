package com.nomad.internethaber.activity;

import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;

import com.nomad.internethaber.R;
import com.nomad.internethaber.event.DrawerClosedEvent;
import com.nomad.internethaber.event.DrawerOpenedEvent;
import com.nomad.internethaber.event.NavigationItemSelectEvent;
import com.nomad.internethaber.fragment.NavigationDrawerFragment;
import com.nomad.internethaber.helper.NavigationHelper;
import com.nomad.internethaber.view.StyledToolbar;
import com.squareup.otto.Subscribe;

import butterknife.InjectView;

public final class HomeActivity extends BaseActivity {

    @InjectView(R.id.activity_home_toolbar)
    protected StyledToolbar mToolbar;

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

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.activity_home_fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.activity_home_fragment_drawer, mDrawerLayout, mToolbar);
    }

    @Subscribe
    public void onNavigationDrawerItemSelected(NavigationItemSelectEvent event) {
        String subtitle = event.getCategory().getName();
        NavigationHelper.setSubtitle(subtitle);

        mToolbar.setSubtitle(subtitle);
        invalidateOptionsMenu();
    }

    @Subscribe
    public void onDrawerOpenedEvent(DrawerOpenedEvent event) {
        mToolbar.setTitle(R.string.categories);
        mToolbar.setSubtitle("");

        invalidateOptionsMenu();
    }

    @Subscribe
    public void onDrawerClosedEvent(DrawerClosedEvent event) {
        mToolbar.setTitle(R.string.app_name);

        CharSequence subtitle = NavigationHelper.getSubtitle();
        mToolbar.setSubtitle(subtitle);

        invalidateOptionsMenu();
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }
}