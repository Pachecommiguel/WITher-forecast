package com.pacheco.weatherchallenge.ui.viewmodels;

import android.app.Application;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.pacheco.weatherchallenge.ui.LocationListener;
import com.pacheco.weatherchallenge.utils.AppUtil;
import com.pacheco.weatherchallenge.network.GpsManager;
import com.pacheco.weatherchallenge.Repository;
import com.pacheco.weatherchallenge.db.entities.City;
import com.pacheco.weatherchallenge.utils.CityEnum;
import com.pacheco.weatherchallenge.utils.Constants;

import java.util.List;

public class MainViewModel extends AndroidViewModel implements LocationListener {

    private final Repository repository;
    private final GpsManager gpsManager;
    private final MutableLiveData<Boolean> gpsStatus = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> permissionStatus = new MutableLiveData<>(false);
    public final LiveData<List<City>> allCities;

    public MainViewModel(@NonNull Application application, FusedLocationProviderClient client) {
        super(application);
        repository = Repository.getInstance(application);
        allCities = repository.getAllCities();
        gpsManager = new GpsManager(getApplication().getApplicationContext(), client);

        if (AppUtil.isFirstTimeLaunch(application.getApplicationContext())) {
            populateDb();
        }

        setUpGps();
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

    @Override
    public void onNewLocation(Location location) {
        repository.addCityByCoordinates(location.getLatitude(), location.getLongitude());
    }

    public void onRequestPermissionsResult(int requestCode, int[] grantResults) {
        if (gpsManager.isPermissionsGranted(requestCode, grantResults)) {
            gpsManager.getLastLocation();
        }
    }

    public void onRefreshItemClick() {
        gpsManager.getLastLocation();
        repository.refreshAllCities();
    }

    public void onActivityResult(int requestCode, int resultCode) {
        if ((requestCode == Constants.GPS_REQUEST_CODE) && (resultCode == Constants.RESULT_OK) &&
                (gpsManager.isProviderEnabled())) {
            checkPermissions();
        }
    }

    private void setUpGps() {
        boolean isProviderEnabled = gpsManager.isProviderEnabled();
        gpsStatus.setValue(!isProviderEnabled);

        if (isProviderEnabled) {
            checkPermissions();
        }
    }

    private void checkPermissions() {
        boolean isPermissionsGiven = gpsManager.isPermissionsGiven();
        permissionStatus.setValue(!isPermissionsGiven);

        if (isPermissionsGiven) {
            gpsManager.getLastLocation();
        }
    }

    private void populateDb() {
        for (CityEnum city : CityEnum.values()) {
            repository.addCityById(city.getId());
        }
    }
}
