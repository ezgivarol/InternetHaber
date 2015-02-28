package com.nomad.internethaber.provider;

import com.google.gson.Gson;
import com.nomad.internethaber.constant.ApplicationConstants;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public final class RestAdapterProvider {
    private static RestAdapter sAdapter;

    public static RestAdapter getInstance() {
        if (sAdapter == null)
            sAdapter = new RestAdapter
                    .Builder()
                    .setEndpoint(ApplicationConstants.API_URL)
                    .setConverter(new GsonConverter(new Gson()))
                    .build();

        return sAdapter;
    }
}