package com.pacheco.weatherchallenge;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Webservice {

    @GET("data/2.5/weather?q={cityName}&appid={apiKey}")
    Call<Response> getWeatherByCityName(
            @Path("cityName") String cityName,
            @Path("apiKey") Integer apiKey
    );
}
