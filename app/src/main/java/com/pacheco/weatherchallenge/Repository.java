package com.pacheco.weatherchallenge;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.pacheco.weatherchallenge.retrofit.response.City;
import com.pacheco.weatherchallenge.retrofit.AppRetrofit;
import com.pacheco.weatherchallenge.retrofit.Webservice;
import com.pacheco.weatherchallenge.utils.CityEnum;
import com.pacheco.weatherchallenge.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private static Repository instance;
    private final Webservice webservice;
    private final MutableLiveData<List<City>> allCities = new MutableLiveData<>();

    private final Callback<City> callback = new Callback<City>() {
        @Override
        public void onResponse(@NotNull Call<City> call, Response<City> response) {
            if (response.isSuccessful()) {
                handleResponse(response.body());
            }
        }

        @Override
        public void onFailure(@NotNull Call<City> call, Throwable t) {
            Log.e(getClass().getSimpleName(), "onFailure: " + t.getMessage());
        }
    };

    private Repository() {
        webservice = AppRetrofit.getInstance().create(Webservice.class);
        allCities.setValue(new ArrayList<>());

        for (CityEnum city : CityEnum.values()) {
            webservice.getWeatherByCityId(city.getId(), BuildConfig.API_KEY, Constants.METRIC)
                    .enqueue(callback);
        }
    }

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }

        return instance;
    }

    public LiveData<List<City>> getAllCities() {
        return allCities;
    }

    public LiveData<City> getCityById(int id) {
        return Transformations.map(allCities, input -> input.stream().filter(response ->
                response.getId() == id).findFirst().orElse(null));
    }

    public void refreshCityById(int id) {
        webservice.getWeatherByCityId(String.valueOf(id), BuildConfig.API_KEY, Constants.METRIC)
                .enqueue(callback);
    }

    public void handleResponse(City city) {
        List<City> allCities = new ArrayList<>(this.allCities.getValue());

        if (allCities.contains(city)) {
            allCities.set(allCities.indexOf(city), city);
        } else {
            allCities.add(city);
        }

        allCities.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
        this.allCities.setValue(allCities);
    }

    public void addCityByCoordinates(double lat, double lon) {
        webservice.getWeatherByCoordinates(String.valueOf(lat), String.valueOf(lon),
                BuildConfig.API_KEY, Constants.METRIC).enqueue(callback);
    }

    public void refreshAllCities() {
        allCities.getValue().forEach(city -> webservice.getWeatherByCityId(
                String.valueOf(city.getId()), BuildConfig.API_KEY, Constants.METRIC).enqueue(callback));
    }
}
