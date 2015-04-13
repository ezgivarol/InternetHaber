package com.nomad.internethaber.interfaces;

import com.nomad.internethaber.bean.PhotosDetailResponseBean;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface PhotosDetailPagerRestInterface {

    @GET("/mobile-api/serviceJson.php?site=internethaber.com&talep=foto_galeri_detay")
    void get(
            @Query("id") String newsId,
            Callback<PhotosDetailResponseBean> callback
    );
}