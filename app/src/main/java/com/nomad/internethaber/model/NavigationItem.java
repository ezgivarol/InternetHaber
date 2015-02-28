package com.nomad.internethaber.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

public final class NavigationItem {

    private
    @NonNull
    @StringRes
    int mTextResource;

    private
    @NonNull
    @DrawableRes
    int mDrawableResource;

    public NavigationItem(@NonNull @StringRes int textResource, @NonNull @DrawableRes int drawableResource) {
        mTextResource = textResource;
        mDrawableResource = drawableResource;
    }

    public
    @StringRes
    int getTextResource() {
        return mTextResource;
    }

    public void setTextResource(@StringRes int textResource) {
        mTextResource = textResource;
    }

    @NonNull
    public int getDrawableResource() {
        return mDrawableResource;
    }

    public void setDrawableResource(@NonNull @DrawableRes int drawableResource) {
        mDrawableResource = drawableResource;
    }
}