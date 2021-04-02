package com.pacheco.weatherchallenge.retrofit;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.pacheco.weatherchallenge.response.Response;
import com.pacheco.weatherchallenge.utils.Cities;
import com.pacheco.weatherchallenge.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class Repository {

    private static Repository instance;
    private final Webservice webservice;
    private volatile MutableLiveData<List<Response>> responseLiveList = new MutableLiveData<>();

    private Callback<Response> callback = new Callback<Response>() {
        @Override
        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
            if (response.isSuccessful()) {
                handleResponse(response.body());
            }
        }

        @Override
        public void onFailure(Call<Response> call, Throwable t) {
            Log.e(getClass().getSimpleName(), "onFailure: " + t.getMessage());
        }
    };

    private Repository() {
        webservice = AppRetrofit.getInstance().create(Webservice.class);
        responseLiveList.setValue(new ArrayList<>());

        for (Cities city : Cities.values()) {
            webservice.getWeatherByCityId(city.getId(), Constants.API_KEY).enqueue(callback);
        }
    }

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }

        return instance;
    }

    public LiveData<List<Response>> getResponseLiveList() {
        return responseLiveList;
    }

    public LiveData<Response> getResponseById(Integer id) {
        return Transformations.map(responseLiveList, input -> input.stream().filter(
                response -> response.getId().equals(id)).findFirst().get());
    }

    public void refreshResponseById(Integer id) {
        webservice.getWeatherByCityId(String.valueOf(id), Constants.API_KEY).enqueue(callback);
    }

    private void handleResponse(Response response) {
        List<Response> responseList = new ArrayList<>(responseLiveList.getValue());

        if (responseList.contains(response)) {
            responseList.set(responseList.indexOf(response), response);
        } else {
            responseList.add(response);
        }

        responseLiveList.setValue(responseList);
    }
}
