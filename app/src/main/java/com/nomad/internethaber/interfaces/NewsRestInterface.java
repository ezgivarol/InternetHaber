package com.nomad.internethaber.interfaces;

import com.nomad.internethaber.bean.NewsResponseBean;

import retrofit.http.GET;
import retrofit.http.Query;

public interface NewsRestInterface {

    @GET("/mobile-api/serviceJson.php?site=internethaber.com&talep=kategorideki_haberler&from=0&to=20")
    NewsResponseBean get(
            @Query("category_id") String categoryId,
            @Query("from") String from,
            @Query("to") String to
    );
}
