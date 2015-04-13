package com.nomad.internethaber.bean;

import com.google.gson.annotations.SerializedName;
import com.nomad.internethaber.model.Video;

import java.util.ArrayList;

public final class VideosResponseBean {

    @SerializedName("item")
    private ArrayList<Video> mVideos;

    public VideosResponseBean() {
        mVideos = new ArrayList<>();
    }

    public ArrayList<Video> getVideos() {
        return mVideos;
    }

}
