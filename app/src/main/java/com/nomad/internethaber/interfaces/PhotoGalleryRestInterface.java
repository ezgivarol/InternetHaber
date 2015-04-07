package com.nomad.internethaber.interfaces;


import com.nomad.internethaber.bean.NewsResponseBean;

import retrofit.http.GET;
import retrofit.http.Query;

public interface PhotoGalleryRestInterface {

    @GET("/mobile-api/serviceJson.php?site=internethaber.com&talep=foto_galeri_listesi")
    NewsResponseBean get(
            @Query("category_id") String categoryId
    );

}
