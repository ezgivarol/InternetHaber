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

import com.nomad.internethaber.R;
import com.nomad.internethaber.adapter.NavigationDrawerListAdapter;
import com.nomad.internethaber.event.NavigationItemSelectEvent;
import com.nomad.internethaber.helper.NavigationHelper;
import com.nomad.internethaber.model.NavigationItem;
import com.nomad.internethaber.provider.BusProvider;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnItemClick;

public final class NavigationDrawerFragment extends BaseFragment {

    @InjectView(R.id.fragment_navigation_drawer_list)
    protected ListView mDrawerList;

    private View mFragmentContainerView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

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

        List<NavigationItem> navigationItems = getMenu();
        NavigationDrawerListAdapter adapter = new NavigationDrawerListAdapter(getContext(), navigationItems);
        mDrawerList.setAdapter(adapter);
    }

    @OnItemClick(R.id.fragment_navigation_drawer_list)
    public void onDrawerListItemSelected(int position) {
        NavigationItemSelectEvent event = new NavigationItemSelectEvent();
        event.setPosition(position);
        BusProvider.getInstance().post(event);

        closeDrawer();
        mDrawerList.setItemChecked(position, true);
    }

    public void setup(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mActionBarDrawerToggle = new ToolbarDrawerToggle(getActivity(), mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mActionBarDrawerToggle.syncState();
            }
        });
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
    }

    public void navigate(int position) {
        NavigationItemSelectEvent event = new NavigationItemSelectEvent();
        event.setPosition(position);
        BusProvider.getInstance().post(event);

        mDrawerList.setItemChecked(position, true);
    }

    public void openDrawer() {
        mDrawerLayout.openDrawer(mFragmentContainerView);
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawer(mFragmentContainerView);
    }

    private List<NavigationItem> getMenu() {
        ArrayList<NavigationItem> items = new ArrayList<>();
        items.add(new NavigationItem(R.string.abc_activitychooserview_choose_application, R.drawable.ic_drawer));
        items.add(new NavigationItem(R.string.abc_activitychooserview_choose_application, R.drawable.ic_drawer));
        items.add(new NavigationItem(R.string.abc_activitychooserview_choose_application, R.drawable.ic_drawer));
        return items;
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }


    private class ToolbarDrawerToggle extends ActionBarDrawerToggle {

        public ToolbarDrawerToggle(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
            super(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);

            if (!isAdded())
                return;

            CharSequence mTitle = NavigationHelper.getTitle();

            getActivity().setTitle(mTitle);
            getActivity().invalidateOptionsMenu();
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);

            if (!isAdded())
                return;

            getActivity().setTitle(R.string.app_name);
            getActivity().invalidateOptionsMenu();
        }
    }

}