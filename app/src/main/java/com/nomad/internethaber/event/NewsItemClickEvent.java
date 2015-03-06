package com.nomad.internethaber.event;

public abstract class NewsItemClickEvent {
    private int mPosition;

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    public static NewsItemClickEvent newInstance(boolean isTablet) {
        if (isTablet)
            return new NewsItemClickEventOnTablet();
        else
            return new NewsItemClickEventOnPhone();
    }
}
