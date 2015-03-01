package com.nomad.internethaber.interfaces;

import retrofit.http.GET;

public interface PushRestInterface {

    @GET("/mobile-api/serviceJson.php?site=internethaber.com&talep=push")
    void get();
}
