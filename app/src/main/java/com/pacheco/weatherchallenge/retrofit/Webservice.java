package com.pacheco.weatherchallenge.retrofit;

import com.pacheco.weatherchallenge.response.City;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Webservice {

    @GET("/data/2.5/weather")
    Call<City> getWeatherByCityId(
            @Query("id") String cityId,
            @Query("appid") String apiKey
    );

    @GET("/data/2.5/weather")
    Call<City> getWeatherByCoordinates(
            @Query("lat") String lat,
            @Query("lon") String lon,
            @Query("appid") String apiKey
    );
}
