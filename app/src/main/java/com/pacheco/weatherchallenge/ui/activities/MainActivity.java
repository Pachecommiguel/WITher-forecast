package com.pacheco.weatherchallenge.ui.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.location.LocationServices;
import com.pacheco.weatherchallenge.R;
import com.pacheco.weatherchallenge.AndroidViewModelFactory;
import com.pacheco.weatherchallenge.databinding.ActivityMainBinding;
import com.pacheco.weatherchallenge.ui.DiffCallback;
import com.pacheco.weatherchallenge.ui.RecyclerListAdapter;
import com.pacheco.weatherchallenge.utils.Constants;
import com.pacheco.weatherchallenge.viewmodels.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private LocationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        RecyclerListAdapter adapter = new RecyclerListAdapter(new DiffCallback(), response ->
                startActivity(new Intent(this, DetailsActivity.class)
                .putExtra(Constants.CITY_ID, response.getId())));

        setContentView(binding.getRoot());
        setSupportActionBar(findViewById(R.id.toolbar));

        setUpRecyclerView(adapter, binding);
        setUpViewModel(adapter);

        checkWifi();
        checkGps();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_item, menu);
        getMenuInflater().inflate(R.menu.refresh_item, menu);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        viewModel.onRequestPermissionsResult(requestCode, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == Constants.GPS_REQUEST_CODE) && (resultCode == Constants.RESULT_OK) &&
                (manager.isProviderEnabled(LocationManager.GPS_PROVIDER))) {
            checkPermissions();
        }
    }

    public void onRefreshItemClick(MenuItem item) {
        checkGps();
        viewModel.onRefreshItemClick();
    }

    public void onAddItemClick(MenuItem item) {
        startActivity(new Intent(this, AddActivity.class));
    }

    private void checkPermissions() {
        if (isPermissionsGiven()) {
            viewModel.onPermissionsGranted();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.LOCAL_REQUEST_CODE);
        }
    }

    private boolean isPermissionsGiven() {
        boolean isFineGranted = ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        boolean isCoarseGranted = ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        return isFineGranted && isCoarseGranted;
    }

    private void setUpRecyclerView(RecyclerListAdapter adapter, ActivityMainBinding binding) {
        binding.recyclerview.setAdapter(adapter);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpViewModel(RecyclerListAdapter adapter) {
        viewModel = new AndroidViewModelFactory(getApplication(),
                LocationServices.getFusedLocationProviderClient(this))
                .create(MainViewModel.class);
        viewModel.getAllCities().observe(this, adapter::submitList);
    }

    private void checkGps() {
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            checkPermissions();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage(R.string.gps_disabled)
                    .setCancelable(false)
                    .setPositiveButton(R.string.yes, (dialog, id) -> startActivityForResult(
                            new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),
                            Constants.GPS_REQUEST_CODE));

            builder.setNegativeButton(R.string.cancel, (dialog, id) -> dialog.cancel());
            builder.create().show();
        }
    }

    private void checkWifi() {
        WifiManager manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if (!manager.isWifiEnabled()) {
            manager.setWifiEnabled(true);
            Toast.makeText(this, R.string.wifi, Toast.LENGTH_SHORT).show();
        }
    }
}