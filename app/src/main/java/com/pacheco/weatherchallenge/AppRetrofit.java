package com.pacheco.weatherchallenge;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class AppRetrofit {

    private static volatile Retrofit instance;

    public static Retrofit getInstance() {
        if (instance == null) {
            synchronized (AppRetrofit.class) {
                if (instance == null) {
                    instance = new Retrofit.Builder()
                            .baseUrl("https://api.openweathermap.org/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }

        return instance;
    }
}
