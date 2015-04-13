package com.nomad.internethaber.bean;

import com.google.gson.annotations.SerializedName;
import com.nomad.internethaber.model.PhotoDetail;

import java.util.ArrayList;

public class PhotoDetailResponseBean {

    @SerializedName("item")
    protected ArrayList<PhotoDetail>  mPhotoDetail;


    public PhotoDetailResponseBean() {
        mPhotoDetail = new ArrayList<>();
    }

    public ArrayList<PhotoDetail> getNews() {
        return mPhotoDetail;
    }
}