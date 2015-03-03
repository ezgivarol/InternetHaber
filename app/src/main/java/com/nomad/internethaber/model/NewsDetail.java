package com.nomad.internethaber.model;

import com.google.gson.annotations.SerializedName;

public final class NewsDetail {

    @SerializedName("id")
    private String mId;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("spot")
    private String mSpot;

    @SerializedName("content")
    private String mContent;

    @SerializedName("thumbnail")
    private String mThumbnail;

    @SerializedName("big_photo")
    private String mPhoto;

    @SerializedName("content_url")
    private String mUrl;

    @SerializedName("video_url")
    private String mVideo;

    @SerializedName("gallery_url")
    private String mGallery;

    @SerializedName("share_url")
    private String mShare;

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

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(String thumbnail) {
        mThumbnail = thumbnail;
    }

    public String getPhoto() {
        return mPhoto;
    }

    public void setPhoto(String photo) {
        mPhoto = photo;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getVideo() {
        return mVideo;
    }

    public void setVideo(String video) {
        mVideo = video;
    }

    public String getGallery() {
        return mGallery;
    }

    public void setGallery(String gallery) {
        mGallery = gallery;
    }

    public String getShare() {
        return mShare;
    }

    public void setShare(String share) {
        mShare = share;
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
