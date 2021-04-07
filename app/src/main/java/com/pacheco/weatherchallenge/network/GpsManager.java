package com.pacheco.weatherchallenge.network;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.pacheco.weatherchallenge.ui.LocationListener;
import com.pacheco.weatherchallenge.utils.Constants;

public class GpsManager {

    private final FusedLocationProviderClient providerClient;
    private final Context context;
    private final LocationManager locationManager;
    private LocationListener listener;

    public GpsManager(Context context, FusedLocationProviderClient providerClient) {
        this.context = context;
        this.providerClient = providerClient;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public boolean isProviderEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public boolean isPermissionsGiven() {
        return ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean isPermissionsGranted(int requestCode, int[] grantResults) {
        return (requestCode == Constants.LOCAL_REQUEST_CODE) &&
                (grantResults.length != Constants.EMPTY) &&
                (grantResults[Constants.FINE] == PackageManager.PERMISSION_GRANTED);
    }

    public void getLastLocation() {
        providerClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                listener.onNewLocation(location);
            }
        });
    }
}
