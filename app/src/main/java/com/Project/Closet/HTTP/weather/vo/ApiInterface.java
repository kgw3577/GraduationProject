package com.Project.Closet.HTTP.weather.vo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("/data/2.5/weather")
    Call<Repo> repo(@Query("appid") String appid, @Query("lat") double lat, @Query("lon") double lon);
}
