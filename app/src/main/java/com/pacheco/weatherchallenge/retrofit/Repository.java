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

public class Repository implements Callback<Response> {

    private static Repository instance;
    private final Webservice webservice;
    private MutableLiveData<List<Response>> responseLiveList = new MutableLiveData<>();

    private Repository() {
        webservice = AppRetrofit.getInstance().create(Webservice.class);
        responseLiveList.setValue(new ArrayList<>());

        for (Cities city : Cities.values()) {
            webservice.getWeatherByCityId(city.getId(), Constants.API_KEY).enqueue(this);
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
        webservice.getWeatherByCityId(String.valueOf(id), Constants.API_KEY).enqueue(this);
    }

    @Override
    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
        if (response.isSuccessful()) {
            List<Response> responseList = new ArrayList<>(responseLiveList.getValue());

            //TODO refactor this
            if (responseList.size() == 10) {
                for (int i = 0; i < responseList.size(); i++) {
                    if (responseList.get(i).getId().intValue() == response.body().getId().intValue()) {
                        responseList.set(i, response.body());
                    }
                }
            } else {
                responseList.add(response.body());
            }

            responseLiveList.setValue(responseList);
            Log.e("TAG", "onResponse: " + response.body().getId() + " " + responseList.size() + " " + responseLiveList.getValue().size() + " " + response.body().getMain().getTemp());
        }
    }

    @Override
    public void onFailure(Call<Response> call, Throwable t) {
        Log.e(getClass().getSimpleName(), "onFailure: " + t.getMessage());
    }
}
