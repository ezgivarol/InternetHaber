package com.nomad.internethaber.helper;

public final class NavigationHelper {
    private static int sPosition = -1;
    private static CharSequence sTitle;

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
}