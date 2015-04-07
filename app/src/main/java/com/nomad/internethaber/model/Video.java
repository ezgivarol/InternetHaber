package com.nomad.internethaber.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public final class Video implements Serializable {

    @SerializedName("id")
    private String mId;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("spot")
    private String mSpot;

    @SerializedName("thumbnail")
    private String mThumbnail;

    @SerializedName("site_url")
    private String mVideo;

    @SerializedName("video_url")
    private String mUrl;

    @SerializedName("date")
    private String mDate;

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

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getVideo() {
        return mVideo;
    }

    public void setVideo(String video) {
        mVideo = video;
    }
}
