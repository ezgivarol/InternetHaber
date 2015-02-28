package com.nomad.internethaber.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nomad.internethaber.helper.NavigationHelper;
import com.nomad.internethaber.provider.BusProvider;

import butterknife.ButterKnife;

abstract class BaseFragment extends Fragment {
    protected static final int NO_ID = -1;
    private Context mContext;

    @Override
    public void onAttach(Activity activity) {
        BusProvider.getInstance().register(this);
        setTitle();
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(getLayoutResource(), container, false);
        ButterKnife.inject(this, mView);
        return mView;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.reset(this);
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        BusProvider.getInstance().unregister(this);
        super.onDetach();
    }

    protected Context getContext() {
        if (mContext == null)
            mContext = getActivity().getBaseContext();

        return mContext;
    }

    private void setTitle() {
        if (getTitleResource() == NO_ID)
            return;

        getActivity().setTitle(getTitleResource());
        NavigationHelper.setTitle(getActivity().getTitle());
    }

    protected abstract
    @NonNull
    @StringRes
    int getTitleResource();

    protected abstract
    @NonNull
    @LayoutRes
    int getLayoutResource();
}