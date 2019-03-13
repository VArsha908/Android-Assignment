package com.example.varsha.chatserver;

import android.util.Log;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Varsha on 13-03-2019.
 */

public interface Service {

    @POST("user/login")
    Call<LoginResponse> login(@Body User user);


    @GET("user")
    Call<ListOfUsers> list(@Body User user);

}
