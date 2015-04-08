package com.nomad.internethaber.interfaces;


import com.nomad.internethaber.bean.PhotosResponseBean;

import retrofit.http.GET;
import retrofit.http.Query;

public interface PhotosRestInterface {

    @GET("/mobile-api/serviceJson.php?site=internethaber.com&talep=foto_galeri_listesi")
    PhotosResponseBean get(
            @Query("category_id") String categoryId,
            @Query("from") String from,
            @Query("to") String to
    );

}
