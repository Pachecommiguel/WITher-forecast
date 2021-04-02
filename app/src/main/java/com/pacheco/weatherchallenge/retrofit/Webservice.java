package com.pacheco.weatherchallenge.retrofit;

import com.pacheco.weatherchallenge.response.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Webservice {

    @GET("/data/2.5/weather")
    Call<Response> getWeatherByCityName(
            @Query("id") String cityId,
            @Query("appid") String apiKey
    );
}
