package com.example.kmj.sunrinton2018;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface API {
    @POST("/earth")
    @FormUrlEncoded
    Call<ResponseBody> earth(@Field("lat") String lat, @Field("lng") String lng, @Field("name") String name);

    @POST("/army")
    @FormUrlEncoded
    Call<ResponseBody>  sibar(@Field("x") String x, @Field("y") String y);
}
