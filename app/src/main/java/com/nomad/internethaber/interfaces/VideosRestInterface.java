package com.nomad.internethaber.interfaces;


import com.nomad.internethaber.bean.VideosResponseBean;

import retrofit.http.GET;
import retrofit.http.Query;

public interface VideosRestInterface {

    @GET("/mobile-api/serviceJson.php?site=internethaber.com&talep=video_galeri_listesi")
    VideosResponseBean get(
            @Query("category_id") String categoryId,
            @Query("from") String from,
            @Query("to") String to
    );

}
