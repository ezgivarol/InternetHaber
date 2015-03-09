package com.nomad.internethaber.interfaces;

import com.nomad.internethaber.bean.NewsDetailResponseBean;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface NewsDetailPagerRestInterface {

    @GET("/mobile-api/serviceJson.php?site=internethaber.com&talep=haber_detay")
    void get(
            @Query("id") String newsId,
            @Query("news_type") String type,
            Callback<NewsDetailResponseBean> callback
    );
}
