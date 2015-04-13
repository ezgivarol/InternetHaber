package com.nomad.internethaber.model;

import com.google.gson.annotations.SerializedName;

public final class PhotoDetail {

    @SerializedName("id")
    private String mId;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("spot")
    private String mSpot;


    @SerializedName("photo")
    private String mPhoto;

    @SerializedName("share_url")
    private String mShare;


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

    public String getPhoto() {
        return mPhoto;
    }

    public void setPhoto(String photo) {
        mPhoto = photo;
    }


    public String getShare() {
        return mShare;
    }

    public void setShare(String share) {
        mShare = share;
    }

}