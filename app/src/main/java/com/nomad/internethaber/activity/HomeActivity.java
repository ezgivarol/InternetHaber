package com.nomad.internethaber.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;

import com.nomad.internethaber.R;
import com.nomad.internethaber.bean.CategoryResponseBean;
import com.nomad.internethaber.event.CategoriesSuccessResponseEvent;
import com.nomad.internethaber.event.DrawerClosedEvent;
import com.nomad.internethaber.event.DrawerOpenedEvent;
import com.nomad.internethaber.event.NavigationItemSelectEvent;
import com.nomad.internethaber.fragment.NavigationDrawerFragment;
import com.nomad.internethaber.helper.NavigationHelper;
import com.nomad.internethaber.model.Category;
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
    }

    @Subscribe
    public void onNavigationDrawerItemSelected(NavigationItemSelectEvent event) {
        int position = event.getPosition();
        if (NavigationHelper.getPosition() == position)
            return;

        NavigationHelper.setPosition(position);

        String subtitle = event.getCategory().getName();
        NavigationHelper.setSubtitle(subtitle);
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

    @Subscribe
    public void onCategoriesSuccessResponseEvent(CategoriesSuccessResponseEvent event) {
        CategoryResponseBean bean = event.getBean();
        Category category = bean.getCategories().get(0);

        CharSequence subtitle = category.getName();
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