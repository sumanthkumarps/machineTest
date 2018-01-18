package com.effone.machinetest.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nagendra on 1/17/2018.
 */

public class ApiClient {
    // public static final String BASE_URL = "http://219.91.147.101:8060/api/";         //public
    //  public static final String BASE_URL = "http://192.168.11.35:8060/api/";    //local
    public static final String BASE_URL = "http://app.reservopia.com/ReservopiaAPI/api/";
    private  static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }



}
