package com.test.brtest.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Feng on 4/21/2016.
 */
public class RestClient {
    private static final String BASE_URL = "http://sandbox.bottlerocketapps.com/";
    private RestInterface apiService;

    public RestClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(RestInterface.class);
    }

    public RestInterface getApiService() {
        return apiService;
    }
}
