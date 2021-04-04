package com.pacheco.weatherchallenge.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.pacheco.weatherchallenge.Repository;
import com.pacheco.weatherchallenge.retrofit.response.City;

public class DetailsViewModel extends AndroidViewModel {

    public final LiveData<City> city;
    private final Repository repository;

    public DetailsViewModel(@NonNull Application application, int id) {
        super(application);
        repository = Repository.getInstance();
        city = repository.getCityById(id);
    }

    public LiveData<City> getCity() {
        return city;
    }

    public void onDeleteItemClick() {
        repository.deleteCity(city.getValue());
    }
}
