package com.nomad.internethaber.provider;

import retrofit.RestAdapter;

public final class RestAdapterProvider {
    private static RestAdapter sAdapter;

    public static RestAdapter getInstance() {
        if (sAdapter == null)
            sAdapter = new RestAdapter
                    .Builder()
                    .setEndpoint("")
                    .build();

        return sAdapter;
    }
}