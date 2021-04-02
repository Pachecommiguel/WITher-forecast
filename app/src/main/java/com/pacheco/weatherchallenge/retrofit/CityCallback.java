package com.pacheco.weatherchallenge.retrofit;

import android.util.Log;

import com.pacheco.weatherchallenge.response.City;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityCallback implements Callback<City> {

    @Override
    public void onResponse(Call<City> call, Response<City> response) {
        if (response.isSuccessful()) {
            Repository.getInstance().handleResponse(response.body());
        }
    }

    @Override
    public void onFailure(Call<City> call, Throwable t) {
        Log.e(getClass().getSimpleName(), "onFailure: " + t.getMessage());
    }
}
