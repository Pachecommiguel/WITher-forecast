package com.pacheco.weatherchallenge.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.pacheco.weatherchallenge.response.City;
import com.pacheco.weatherchallenge.retrofit.Repository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    public final LiveData<List<City>> allCities;

    public MainViewModel(@NonNull Application application) {
        super(application);
        allCities = Repository.getInstance().getAllCities();
    }

    public LiveData<List<City>> getAllCities() {
        return allCities;
    }
}
