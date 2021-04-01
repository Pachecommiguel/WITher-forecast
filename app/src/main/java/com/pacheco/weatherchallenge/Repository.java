package com.pacheco.weatherchallenge;

import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;

public class Repository implements Callback<Response> {

    private static final String API_KEY = "433f0a05409361125a827f77654820e4";
    private final Webservice webservice;
    private final MutableLiveData<Response> response = new MutableLiveData<>();

    public Repository() {
        webservice = AppRetrofit.getInstance().create(Webservice.class);
        webservice.getWeatherByCityName("Lisbon", API_KEY).enqueue(this);
    }

    @Override
    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
        if (response.isSuccessful()) {
            this.response.setValue(response.body());
        }
    }

    @Override
    public void onFailure(Call<Response> call, Throwable t) {
    }
}
