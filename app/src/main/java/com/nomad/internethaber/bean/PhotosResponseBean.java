package com.nomad.internethaber.bean;

import com.google.gson.annotations.SerializedName;
import com.nomad.internethaber.model.Photo;

import java.util.ArrayList;

public final class PhotosResponseBean {

    @SerializedName("item")
    private ArrayList<Photo> mPhotos;

    public PhotosResponseBean() {
        mPhotos = new ArrayList<>();
    }

    public ArrayList<Photo> getPhotos() {
        return mPhotos;
    }

}
