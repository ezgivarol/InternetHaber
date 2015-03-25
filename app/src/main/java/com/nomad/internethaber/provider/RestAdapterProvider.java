package com.nomad.internethaber.provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nomad.internethaber.constant.ApplicationConstants;
import com.nomad.internethaber.helper.LoggerConverter;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public final class RestAdapterProvider {
    private static RestAdapter sAdapter;

    public static RestAdapter getInstance() {
        if (sAdapter == null) {
            Gson gson = new GsonBuilder()
                    .create();

            LoggerConverter loggerConverter = new LoggerConverter(gson);

            sAdapter = new RestAdapter
                    .Builder()
                    .setEndpoint(ApplicationConstants.API_URL)
                    .setConverter(loggerConverter)
                    .build();
        }

        return sAdapter;
    }
}