package com.nomad.internethaber.interfaces;

import com.nomad.internethaber.bean.CategoryResponseBean;

import retrofit.http.GET;

public interface CategoryRestInterface {

    @GET("/mobile-api/serviceJson.php?site=internethaber.com&talep=kategori_listesi")
    CategoryResponseBean get();

}
