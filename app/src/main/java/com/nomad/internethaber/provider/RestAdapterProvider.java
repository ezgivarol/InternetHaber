package com.nomad.internethaber.provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nomad.internethaber.constant.ApplicationConstants;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public final class RestAdapterProvider {
    private static RestAdapter sAdapter;

    public static RestAdapter getInstance() {
        if (sAdapter == null) {
            Gson gson = new GsonBuilder()
                    .create();

            GsonConverter gsonConverter = new GsonConverter(gson);

            sAdapter = new RestAdapter
                    .Builder()
                    .setEndpoint(ApplicationConstants.API_URL)
                    .setConverter(gsonConverter)
                    .build();
        }


        return sAdapter;
    }
}