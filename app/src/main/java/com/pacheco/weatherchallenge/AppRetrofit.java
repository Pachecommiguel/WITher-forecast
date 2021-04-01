package com.pacheco.weatherchallenge;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class AppRetrofit {

    private static volatile Retrofit instance;
    public static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

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
