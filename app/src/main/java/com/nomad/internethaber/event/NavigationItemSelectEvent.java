package com.nomad.internethaber.event;

import com.nomad.internethaber.model.Category;

public final class NavigationItemSelectEvent {
    private int position;
    private Category mCategory;

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setCategory(Category category) {
        mCategory = category;
    }

    public Category getCategory() {
        return mCategory;
    }
}