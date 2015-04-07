package com.nomad.internethaber.fragment;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.nomad.internethaber.R;
import com.nomad.internethaber.adapter.NavigationDrawerListAdapter;
import com.nomad.internethaber.bean.CategoryResponseBean;
import com.nomad.internethaber.event.CategoriesFailureResponseEvent;
import com.nomad.internethaber.event.CategoriesRequestEvent;
import com.nomad.internethaber.event.CategoriesSuccessResponseEvent;
import com.nomad.internethaber.event.DrawerClosedEvent;
import com.nomad.internethaber.event.DrawerOpenedEvent;
import com.nomad.internethaber.event.NavigationItemSelectEvent;
import com.nomad.internethaber.helper.NavigationHelper;
import com.nomad.internethaber.model.Category;
import com.nomad.internethaber.task.CategoriesAsyncTask;
import com.nomad.internethaber.view.GalleryView;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.InjectView;
import butterknife.OnItemClick;

public final class NavigationDrawerFragment extends BaseFragment implements Runnable {

    @InjectView(R.id.fragment_navigation_drawer_list)
    protected ListView mDrawerList;

    private View mFragmentContainerView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    private CategoriesAsyncTask mCategoriesAsyncTask;

    @NonNull
    @Override
    protected int getTitleResource() {
        return NO_ID;
    }

    @NonNull
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_drawer;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCategoriesAsyncTask = new CategoriesAsyncTask();
        mCategoriesAsyncTask.execute();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mCategoriesAsyncTask.cancel(true);
    }

    @Subscribe
    public void onCategoriesRequestEvent(CategoriesRequestEvent event) {
        // TODO Animate spinner.
    }

    @Subscribe
    public void onCategoriesSuccessResponseEvent(CategoriesSuccessResponseEvent event) {
        CategoryResponseBean categoryResponseBean = event.getBean();
        ArrayList<Category> categories = categoryResponseBean.getCategories();

        GalleryView galleryView = new GalleryView(getContext());
        mDrawerList.addFooterView(galleryView);

     //   HeaderView headerView = new HeaderView(getContext());
      //  mDrawerList.addHeaderView(headerView);

        NavigationDrawerListAdapter adapter = new NavigationDrawerListAdapter(getContext(), categories);
        mDrawerList.setAdapter(adapter);

        onDrawerListItemSelected(1);
    }

    @Subscribe
    public void onCategoriesFailureResponseEvent(CategoriesFailureResponseEvent event) {
        // TODO Show error view.
    }

    @OnItemClick(R.id.fragment_navigation_drawer_list)
    public void onDrawerListItemSelected(int position) {
        if (NavigationHelper.getDrawerPosition() == position)
            return;

        mDrawerList.setItemChecked(position, true);
        NavigationHelper.setDrawerPosition(position);

        navigate(position);

        closeDrawer();

    }

    public void setup(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mActionBarDrawerToggle = new ToolbarDrawerToggle(getActivity(), mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mDrawerLayout.post(this);
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
    }

    private void navigate(int position) {
        Category category = (Category) mDrawerList.getAdapter().getItem(position);

        NavigationItemSelectEvent event = new NavigationItemSelectEvent();
        event.setPosition(position);
        event.setCategory(category);
        getBus().post(event);
    }

    public void openDrawer() {
        mDrawerLayout.openDrawer(mFragmentContainerView);
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawer(mFragmentContainerView);
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void run() {
      mActionBarDrawerToggle.syncState();
    }


    private final class ToolbarDrawerToggle extends ActionBarDrawerToggle {

        public ToolbarDrawerToggle(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
            super(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);

            if (!isAdded())
                return;

            DrawerClosedEvent event = new DrawerClosedEvent();
            getBus().post(event);
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);

            if (!isAdded())
                return;

            DrawerOpenedEvent event = new DrawerOpenedEvent();
            getBus().post(event);
        }
    }
public void toaster(String values){

    Toast.makeText(getContext(),values,Toast.LENGTH_LONG).show();

}
}