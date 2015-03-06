package com.nomad.internethaber.helper;

import android.graphics.drawable.Drawable;

public final class SelectedImageHelper {
    private static Drawable sPhoto;

    public static Drawable getPhoto() {
        return sPhoto;
    }

    public static void setPhoto(Drawable mPhoto) {
        SelectedImageHelper.sPhoto = mPhoto;
    }
}
