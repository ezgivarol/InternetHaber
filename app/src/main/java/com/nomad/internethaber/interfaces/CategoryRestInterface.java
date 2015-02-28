package com.nomad.internethaber.interfaces;

import com.nomad.internethaber.bean.CategoryResponseBean;

import retrofit.Callback;
import retrofit.http.GET;

public interface CategoryRestInterface {

    @GET("/mobile-api/serviceJson.php?site=internethaber.com&talep=kategori_listesi")
    void send(Callback<CategoryResponseBean> callback);

}
