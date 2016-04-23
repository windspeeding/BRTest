package com.test.brtest.rest;

import com.test.brtest.model.Stores;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Feng on 4/21/2016.
 */
public interface RestInterface {

    @GET("BR_Android_CodingExam_2015_Server/stores.json ")
    Call<Stores> stores();
}
