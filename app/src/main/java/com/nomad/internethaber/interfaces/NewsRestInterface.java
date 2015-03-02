package com.nomad.internethaber.interfaces;

import com.nomad.internethaber.bean.NewsResponseBean;

import retrofit.http.GET;
import retrofit.http.Path;

public interface NewsRestInterface {

    @GET("/mobile-api/serviceJson.php?site=internethaber.com&talep=kategorideki_haberler&category_id={category_id}&from={from}&to={to}")
    NewsResponseBean get(
            @Path("category_id") String category,
            @Path("from") String from,
            @Path("to") String to
    );
}
