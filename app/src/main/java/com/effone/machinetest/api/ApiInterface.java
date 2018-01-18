package com.effone.machinetest.api;

import com.effone.machinetest.model.Login;
import com.effone.machinetest.model.User;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Nagendra on 1/17/2018.
 */

public interface ApiInterface {

    @GET("access/login")
    Call<Login> getLogin(@Header("Token") String apiKey, @Query("email") String email, @Query("password") String pass);


    @Headers("Content-type: application/json")
    @POST("Register/ManageUser")
    Call<Response> createAcount(@Header("Token") String apiKey, @Body User UserDetails);

}
