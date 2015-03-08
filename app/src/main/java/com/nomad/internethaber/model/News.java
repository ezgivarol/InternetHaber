package com.nomad.internethaber.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public final class News implements Serializable {

    @SerializedName("id")
    private String mId;

    @SerializedName("type")
    private String mType;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("spot")
    private String mSpot;

    @SerializedName("thumbnail")
    private String mThumbnail;

    @SerializedName("site_url")
    private String mUrl;

    @SerializedName("big_photo")
    private String mPhoto;

    @SerializedName("date")
    private String mDate;

    @SerializedName("category_id")
    private String mCategoryId;

    @SerializedName("category")
    private String mCategory;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSpot() {
        return mSpot;
    }

    public void setSpot(String spot) {
        mSpot = spot;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(String thumbnail) {
        mThumbnail = thumbnail;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getPhoto() {
        return mPhoto;
    }

    public void setPhoto(String photo) {
        mPhoto = photo;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getCategoryId() {
        return mCategoryId;
    }

    public void setCategoryId(String categoryId) {
        mCategoryId = categoryId;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }
}
