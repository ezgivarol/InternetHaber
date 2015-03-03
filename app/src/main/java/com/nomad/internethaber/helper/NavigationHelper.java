package com.nomad.internethaber.helper;

public final class NavigationHelper {
    private static int sPosition = -1;
    private static CharSequence sTitle;
    private static CharSequence sSubtitle;

    public static int getPosition() {
        return sPosition;
    }

    public static void setPosition(int position) {
        sPosition = position;
    }

    public static CharSequence getTitle() {
        return sTitle;
    }

    public static void setTitle(CharSequence title) {
        sTitle = title;
    }

    public static CharSequence getSubtitle() {
        return sSubtitle;
    }

    public static void setSubtitle(CharSequence subtitle) {
        sSubtitle = subtitle;
    }
}