package com.pacheco.weatherchallenge.retrofit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.pacheco.weatherchallenge.response.City;
import com.pacheco.weatherchallenge.utils.CityEnum;
import com.pacheco.weatherchallenge.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    private static Repository instance;
    private final Webservice webservice;
    private volatile MutableLiveData<List<City>> allCities = new MutableLiveData<>();

    private Repository() {
        webservice = AppRetrofit.getInstance().create(Webservice.class);
        allCities.setValue(new ArrayList<>());

        for (CityEnum city : CityEnum.values()) {
            webservice.getWeatherByCityId(city.getId(), Constants.API_KEY).enqueue(new CityCallback());
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

    public LiveData<City> getCityById(Integer id) {
        return Transformations.map(allCities, input -> input.stream().filter(response ->
                response.getId().equals(id)).findFirst().get());
    }

    public void refreshCityById(Integer id) {
        webservice.getWeatherByCityId(String.valueOf(id), Constants.API_KEY).enqueue(new CityCallback());
    }

    public synchronized void handleResponse(City city) {
        List<City> allCities = new ArrayList<>(this.allCities.getValue());

        if (allCities.contains(city)) {
            allCities.set(allCities.indexOf(city), city);
        } else {
            allCities.add(city);
        }

        allCities.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
        this.allCities.setValue(allCities);
    }
}
