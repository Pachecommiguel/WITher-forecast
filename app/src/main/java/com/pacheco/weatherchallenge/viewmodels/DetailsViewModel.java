package com.pacheco.weatherchallenge.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.pacheco.weatherchallenge.response.City;
import com.pacheco.weatherchallenge.retrofit.Repository;

public class DetailsViewModel extends AndroidViewModel {

    public final LiveData<City> city;
    private final Repository repository;

    public DetailsViewModel(@NonNull Application application, Integer id) {
        super(application);
        repository = Repository.getInstance();
        city = repository.getCityById(id);
    }

    public void onRefreshItemClick() {
        repository.refreshCityById(city.getValue().getId());
    }
}
