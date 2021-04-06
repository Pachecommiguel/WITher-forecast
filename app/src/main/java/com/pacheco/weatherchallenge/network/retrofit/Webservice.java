package com.pacheco.weatherchallenge.network.retrofit;

import com.pacheco.weatherchallenge.network.models.City;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Webservice {

    @GET("/data/2.5/weather")
    Call<City> getWeatherByCityId(
            @Query("id") String cityId,
            @Query("appid") String apiKey,
            @Query("units") String units
    );

    @GET("/data/2.5/weather")
    Call<City> getWeatherByCityCoordinates(
            @Query("lat") String lat,
            @Query("lon") String lon,
            @Query("appid") String apiKey,
            @Query("units") String units
    );

    @GET("/data/2.5/weather")
    Call<City> getWeatherByCityName(
            @Query("q") String name,
            @Query("appid") String apiKey,
            @Query("units") String units
    );
}
