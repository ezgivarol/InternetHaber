package com.nomad.internethaber.helper;

public final class NavigationHelper {
    private static int sDrawerPosition = -1;
    private static int sSelectedNewsPosition = -1;
    private static int sSelectedCategoryPosition = -1;
    private static CharSequence sTitle;
    private static CharSequence sSubtitle;

    public static int getDrawerPosition() {
        return sDrawerPosition;
    }

    public static void setDrawerPosition(int position) {
        sDrawerPosition = position;
    }

    public static int getSelectedNewsPosition() {
        return sSelectedNewsPosition;
    }

    public static int getSelectedCategoryPosition() {
        return sSelectedCategoryPosition;
    }

    public static void setSelectedCategoryPosition(int position) {
        NavigationHelper.sSelectedCategoryPosition = position;
    }

    public static void setSelectedNewsPosition(int selectedNewsPosition) {
        sSelectedNewsPosition = selectedNewsPosition;
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