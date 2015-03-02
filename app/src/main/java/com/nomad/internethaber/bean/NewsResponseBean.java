package com.nomad.internethaber.bean;

import com.google.gson.annotations.SerializedName;
import com.nomad.internethaber.model.Category;
import com.nomad.internethaber.model.News;

import java.util.ArrayList;

public final class NewsResponseBean {

    @SerializedName("item")
    private ArrayList<News> mNews;

    public NewsResponseBean() {
        mNews = new ArrayList<>();
    }

    public ArrayList<News> getNews() {
        return mNews;
    }
}
