package com.nomad.internethaber.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public final class Categories {

    @SerializedName("items")
    private ArrayList<Category> mCategoriesList;

    public Categories() {
        mCategoriesList = new ArrayList<>();
    }

    public ArrayList<Category> getCategoriesList() {
        return mCategoriesList;
    }

    public void setCategoriesList(ArrayList<Category> categoriesList) {
        mCategoriesList = categoriesList;
    }
}
