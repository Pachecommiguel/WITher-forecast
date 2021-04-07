package com.pacheco.weatherchallenge.network.retrofit;

import com.google.gson.GsonBuilder;
import com.pacheco.weatherchallenge.db.entities.City;
import com.pacheco.weatherchallenge.utils.Constants;

import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class AppRetrofit {

    private static Retrofit instance;

    public static Retrofit getInstance() {
        if (instance == null) {
            instance = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(createGsonConverter())
                    .build();
        }

        return instance;
    }

    private static Converter.Factory createGsonConverter() {
        return GsonConverterFactory.create(new GsonBuilder()
                .registerTypeAdapter(City.class, new ResponseDeserializer()).create());
    }
}
