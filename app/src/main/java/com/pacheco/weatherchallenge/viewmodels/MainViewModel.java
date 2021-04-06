package com.pacheco.weatherchallenge.viewmodels;

import android.app.Application;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.pacheco.weatherchallenge.Repository;
import com.pacheco.weatherchallenge.models.City;
import com.pacheco.weatherchallenge.utils.CityEnum;
import com.pacheco.weatherchallenge.utils.Constants;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    public final LiveData<List<City>> allCities;
    private final Repository repository;
    private final FusedLocationProviderClient client;

    public MainViewModel(@NonNull Application application, FusedLocationProviderClient client) {
        super(application);
        this.client = client;
        repository = Repository.getInstance(application);
        allCities = repository.getAllCities();
    }

    public LiveData<List<City>> getAllCities() {
        return allCities;
    }

    public void onRequestPermissionsResult(int requestCode, int[] grantResults) {
        if (isPermissionsGranted(requestCode, grantResults)) {
            getLastLocation();
        }
    }

    public void onPermissionsGranted() {
        getLastLocation();
    }

    public void onRefreshItemClick() {
        getLastLocation();
        repository.refreshAllCities();
    }

    public void onFirstTimeLaunch() {
        for (CityEnum city : CityEnum.values()) {
            repository.addCityById(city.getId());
        }
    }

    private void getLastLocation() {
        client.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                repository.addCityByCoordinates(location.getLatitude(), location.getLongitude());
            }
        });
    }

    private boolean isPermissionsGranted(int requestCode, int[] grantResults) {
        return (requestCode == Constants.LOCAL_REQUEST_CODE) &&
                (grantResults.length != Constants.EMPTY) &&
                (grantResults[Constants.FINE] == PackageManager.PERMISSION_GRANTED) &&
                (grantResults[Constants.COARSE] == PackageManager.PERMISSION_GRANTED);
    }
}
