package com.nomad.internethaber.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public final class Category implements Serializable {

    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("sub")
    private ArrayList<SubCategory> mSubCategories;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public ArrayList<SubCategory> getSubCategories() {
        return mSubCategories;
    }

    public void setSubCategories(ArrayList<SubCategory> subCategories) {
        mSubCategories = subCategories;
    }

    public static class Builder {
        private String mId;
        private String mName;

        public Builder setId(String id) {
            mId = id;

            return this;
        }

        public Builder setName(String name) {
            mName = name;

            return this;
        }

        public Category build() {
            Category category = new Category();
            category.setId(mId);
            category.setName(mName);

            return category;
        }
    }
}
