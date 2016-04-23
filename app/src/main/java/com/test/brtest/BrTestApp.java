package com.test.brtest;

import android.app.Application;

import com.test.brtest.rest.RestClient;

/**
 * Created by Feng on 4/21/2016.
 */
public class BrTestApp extends Application{
    private static RestClient restClient;

    @Override
    public void onCreate() {
        super.onCreate();
        restClient = new RestClient();
    }

    public static RestClient getRestClient() {
        return restClient;
    }


}
