package com.nomad.internethaber.interfaces;

import com.nomad.internethaber.bean.NewsDetailResponseBean;
import com.nomad.internethaber.bean.NewsResponseBean;

import retrofit.http.GET;
import retrofit.http.Query;

public interface NewsDetailRestInterface {

    @GET("/mobile-api/serviceJson.php?site=internethaber.com&talep=haber_detay")
    NewsDetailResponseBean get(
            @Query("id") String newsId,
            @Query("news_type") String type
    );
}
