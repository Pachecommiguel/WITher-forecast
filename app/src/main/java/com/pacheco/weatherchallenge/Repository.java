package com.pacheco.weatherchallenge;

import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;

public class Repository implements Callback<Response> {

    private final Webservice webservice;
    private final MutableLiveData<Response> response = new MutableLiveData<>();

    public Repository() {
        webservice = AppRetrofit.getInstance().create(Webservice.class);
        webservice.getWeatherByCityName("Lisbon", Constants.API_KEY).enqueue(this);
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

    public MutableLiveData<Response> getResponse() {
        return response;
    }
}
