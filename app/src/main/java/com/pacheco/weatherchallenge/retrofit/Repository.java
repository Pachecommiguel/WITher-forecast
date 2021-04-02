package com.pacheco.weatherchallenge.retrofit;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pacheco.weatherchallenge.response.Response;
import com.pacheco.weatherchallenge.utils.Cities;
import com.pacheco.weatherchallenge.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class Repository implements Callback<Response> {

    private final Webservice webservice;
    private final MutableLiveData<List<Response>> responseLiveList = new MutableLiveData<>();
    private volatile List<Response> responseList = new ArrayList<>();

    public Repository() {
        webservice = AppRetrofit.getInstance().create(Webservice.class);

        for (Cities city : Cities.values()) {
            webservice.getWeatherByCityId(city.getId(), Constants.API_KEY).enqueue(this);
        }
    }

    @Override
    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
        if (response.isSuccessful()) {
            responseList.add(response.body());
            responseLiveList.setValue(responseList);
        }
    }

    @Override
    public void onFailure(Call<Response> call, Throwable t) {
        Log.e(getClass().getSimpleName(), "onFailure: " + t.getMessage());
    }

    public LiveData<List<Response>> getResponseLiveList() {
        return responseLiveList;
    }
}
