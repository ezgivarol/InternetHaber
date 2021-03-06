package com.nomad.internethaber.bean;

import com.google.gson.annotations.SerializedName;
import com.nomad.internethaber.model.Category;

import java.util.ArrayList;

public final class CategoryResponseBean {

    @SerializedName("item")
    private ArrayList<Category> mCategories;

    public CategoryResponseBean() {
        mCategories = new ArrayList<>();
    }

    public ArrayList<Category> getCategories() {
        return mCategories;
    }
}
