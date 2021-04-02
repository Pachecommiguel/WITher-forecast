package com.pacheco.weatherchallenge.retrofit;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pacheco.weatherchallenge.response.Response;
import com.pacheco.weatherchallenge.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class Repository implements Callback<Response> {

    private final Webservice webservice;
    private final MutableLiveData<List<Response>> response = new MutableLiveData<>();
    private volatile List<Response> responseList = new ArrayList<>();

    public Repository() {
        webservice = AppRetrofit.getInstance().create(Webservice.class);
        webservice.getWeatherByCityName("Lisbon", Constants.API_KEY).enqueue(this);
        webservice.getWeatherByCityName("London", Constants.API_KEY).enqueue(this);
        webservice.getWeatherByCityName("London", Constants.API_KEY).enqueue(this);
    }

    @Override
    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
        if (response.isSuccessful()) {
            responseList.add(response.body());
            this.response.setValue(responseList);
        }
    }

    @Override
    public void onFailure(Call<Response> call, Throwable t) {
        Log.e(getClass().getSimpleName(), "onFailure: " + t.getMessage());
    }

    public LiveData<List<Response>> getResponse() {
        return response;
    }
}
