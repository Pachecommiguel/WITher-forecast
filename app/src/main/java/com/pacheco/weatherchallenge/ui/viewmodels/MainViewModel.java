package com.pacheco.weatherchallenge.ui.viewmodels;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.pacheco.weatherchallenge.Repository;
import com.pacheco.weatherchallenge.db.entities.City;
import com.pacheco.weatherchallenge.utils.CityEnum;
import com.pacheco.weatherchallenge.utils.Constants;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MainViewModel extends AndroidViewModel {

    public final LiveData<List<City>> allCities;
    private final Repository repository;
    private final FusedLocationProviderClient client;
    private final MutableLiveData<Boolean> gpsStatus = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> permissionStatus = new MutableLiveData<>(false);
    private final LocationManager manager;

    public MainViewModel(@NonNull Application application, FusedLocationProviderClient client) {
        super(application);
        this.client = client;
        repository = Repository.getInstance(application);
        allCities = repository.getAllCities();
        manager = (LocationManager) getApplication().getApplicationContext()
                .getSystemService(Context.LOCATION_SERVICE);

        checkFirstTimeLaunch();
        checkWifi();
        checkGps();
    }

    public LiveData<List<City>> getAllCities() {
        return allCities;
    }

    public MutableLiveData<Boolean> getGpsStatus() {
        return gpsStatus;
    }

    public MutableLiveData<Boolean> getPermissionStatus() {
        return permissionStatus;
    }

    public void onRequestPermissionsResult(int requestCode, int[] grantResults) {
        if (isPermissionsGranted(requestCode, grantResults)) {
            permissionStatus.setValue(false);
            getLastLocation();
        }
    }

    public void onRefreshItemClick() {
        getLastLocation();
        repository.refreshAllCities();
    }

    public void onActivityResult(int requestCode, int resultCode) {
        if ((requestCode == Constants.GPS_REQUEST_CODE) && (resultCode == Constants.RESULT_OK) &&
                (manager.isProviderEnabled(LocationManager.GPS_PROVIDER))) {
            gpsStatus.setValue(false);
            checkPermissions();
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
                (grantResults[Constants.FINE] == PackageManager.PERMISSION_GRANTED);
    }

    private void checkWifi() {
        WifiManager manager = (WifiManager) getApplication().getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);

        if (!manager.isWifiEnabled()) {
            manager.setWifiEnabled(true);
        }
    }

    private void checkGps() {
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            checkPermissions();
        } else {
            gpsStatus.setValue(true);
        }
    }

    private void checkPermissions() {
        if (isPermissionsGiven()) {
            getLastLocation();
        } else {
            permissionStatus.setValue(true);
        }
    }

    private boolean isPermissionsGiven() {
        return ActivityCompat.checkSelfPermission(getApplication().getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void checkFirstTimeLaunch() {
        SharedPreferences preferences = getApplication().getApplicationContext()
                .getSharedPreferences("com.pacheco.weatherchallenge", MODE_PRIVATE);

        if (preferences.getBoolean(Constants.FIRST_RUN, true)) {
            preferences.edit().putBoolean(Constants.FIRST_RUN, false).apply();
            for (CityEnum city : CityEnum.values()) {
                repository.addCityById(city.getId());
            }
        }
    }
}
