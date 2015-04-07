package com.nomad.internethaber.bean;

import com.google.gson.annotations.SerializedName;
import com.nomad.internethaber.model.Photo;

import java.util.ArrayList;

public final class PhotoGalleryResponseBean {

    @SerializedName("item")
    private ArrayList<Photo> mPhotos;

    public PhotoGalleryResponseBean() {
        mPhotos = new ArrayList<>();
    }

    public ArrayList<Photo> getPhotos() {
        return mPhotos;
    }

}
