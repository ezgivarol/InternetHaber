package com.nomad.internethaber.model;

import com.google.gson.annotations.SerializedName;

public final class Category {

    @SerializedName("id")
    protected String mId;

    @SerializedName("name")
    protected String mName;

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
}
