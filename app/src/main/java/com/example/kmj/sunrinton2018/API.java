package com.example.kmj.sunrinton2018;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;

import retrofit2.http.POST;


public interface API {
    @POST("/earth")
    @FormUrlEncoded
    Call<List<Data>> earth(@Field("lat") String lat, @Field("lng") String lng);

    @POST("/army")
    @FormUrlEncoded
    Call<Data> good(@Field("x") String x, @Field("y") String y);
}
