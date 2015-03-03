package com.nomad.internethaber.bean;

import com.google.gson.annotations.SerializedName;
import com.nomad.internethaber.model.NewsDetail;

public final class NewsDetailResponseBean {

    @SerializedName("item")
    private NewsDetail mNewsDetail;

    public NewsDetail getNewsDetail() {
        return mNewsDetail;
    }

    public void setNewsDetail(NewsDetail newsDetail) {
        mNewsDetail = newsDetail;
    }
}
