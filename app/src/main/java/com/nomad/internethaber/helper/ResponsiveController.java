package com.nomad.internethaber.helper;

import android.content.res.Resources;

import com.nomad.internethaber.R;

public final class ResponsiveController {
    private boolean isTablet;

    public ResponsiveController(Resources resources) {
        isTablet = resources.getBoolean(R.bool.is_tablet);
    }

    public boolean isTablet() {
        return isTablet;
    }
}
