package com.nomad.internethaber.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;

import com.nomad.internethaber.R;
import com.nomad.internethaber.event.NavigationItemSelectEvent;
import com.nomad.internethaber.fragment.NavigationDrawerFragment;
import com.nomad.internethaber.helper.NavigationHelper;
import com.squareup.otto.Subscribe;

import butterknife.InjectView;

public final class HomeActivity extends BaseActivity {

    @InjectView(R.id.activity_home_toolbar)
    protected Toolbar mToolbar;

    @InjectView(R.id.activity_home_drawerlayout)
    protected DrawerLayout mDrawerLayout;

    private NavigationDrawerFragment mNavigationDrawerFragment;

    @NonNull
    @Override
    protected int getLayoutResource() {
        return R.layout.layout_home;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.activity_home_fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.activity_home_fragment_drawer, mDrawerLayout, mToolbar);
//        mNavigationDrawerFragment.navigate(BellFragment.POSITION);
    }

    @Subscribe
    public void onNavigationDrawerItemSelected(NavigationItemSelectEvent event) {
        int position = event.getPosition();
        if (NavigationHelper.getPosition() == position)
            return;

//        switch (position) {
//            case BellFragment.POSITION:
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.activity_home_container, new BellFragment())
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                        .commit();
//                break;
//            case SnapshotFragment.POSITION:
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.activity_home_container, new SnapshotFragment())
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                        .commit();
//                break;
//            case SettingsFragment.POSITION:
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.activity_home_container, new SettingsFragment())
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                        .commit();
//                break;
//        }

        NavigationHelper.setPosition(position);
    }


    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }
}