package com.pacheco.weatherchallenge;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.pacheco.weatherchallenge.retrofit.AppRetrofit;
import com.pacheco.weatherchallenge.retrofit.Webservice;
import com.pacheco.weatherchallenge.retrofit.response.City;
import com.pacheco.weatherchallenge.room.AppRoom;
import com.pacheco.weatherchallenge.room.CityDao;
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
    private final CityDao cityDao;
    private final MutableLiveData<List<City>> allCities = new MutableLiveData<>();
    private static final MutableLiveData<Integer> statusCode = new MutableLiveData<>();

    private final Callback<City> callback = new Callback<City>() {
        @Override
        public void onResponse(@NotNull Call<City> call, Response<City> response) {
            statusCode.setValue(response.code());

            if (response.isSuccessful()) {
                handleResponse(response.body());
            }
        }

        @Override
        public void onFailure(@NotNull Call<City> call, Throwable t) {
            Log.e(getClass().getSimpleName(), "onFailure: " + t.getMessage());
        }
    };

    private Repository(Application application) {
        webservice = AppRetrofit.getInstance().create(Webservice.class);
        allCities.setValue(new ArrayList<>());
        cityDao = AppRoom.getInstance(application.getApplicationContext()).cityDao();

        for (CityEnum city : CityEnum.values()) {
            webservice.getWeatherByCityId(city.getId(), BuildConfig.API_KEY, Constants.METRIC)
                    .enqueue(callback);
        }
    }

    public static Repository getInstance(Application application) {
        statusCode.setValue(Constants.NOT_DEFINED);

        if (instance == null) {
            instance = new Repository(application);
        }

        return instance;
    }

    public MutableLiveData<Integer> getStatusCode() {
        return statusCode;
    }

    public LiveData<List<City>> getAllCities() {
        return allCities;
    }

    public LiveData<City> getCityById(int id) {
        return Transformations.map(allCities, input -> input.stream().filter(response ->
                response.getId() == id).findFirst().orElse(null));
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
        webservice.getWeatherByCityCoordinates(String.valueOf(lat), String.valueOf(lon),
                BuildConfig.API_KEY, Constants.METRIC).enqueue(callback);
    }

    public void refreshAllCities() {
        allCities.getValue().forEach(city -> webservice.getWeatherByCityId(
                String.valueOf(city.getId()), BuildConfig.API_KEY, Constants.METRIC).enqueue(callback));
    }

    public void deleteCity(City city) {
        List<City> allCities = new ArrayList<>(this.allCities.getValue());
        allCities.remove(city);
        this.allCities.setValue(allCities);
    }

    public void addCityByName(String name) {
        webservice.getWeatherByCityName(name, BuildConfig.API_KEY, Constants.METRIC).enqueue(callback);
    }
}
