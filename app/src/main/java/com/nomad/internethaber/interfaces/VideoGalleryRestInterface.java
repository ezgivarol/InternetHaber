package com.nomad.internethaber.interfaces;


import com.nomad.internethaber.bean.VideoGalleryResponseBean;

import retrofit.http.GET;
import retrofit.http.Query;

public interface VideoGalleryRestInterface {

    @GET("/mobile-api/serviceJson.php?site=internethaber.com&talep=video_galeri_listesi")
    VideoGalleryResponseBean get(
            @Query("category_id") String categoryId,
            @Query("from") String from,
            @Query("to") String to
    );

}
