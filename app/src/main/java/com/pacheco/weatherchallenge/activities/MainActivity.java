package com.pacheco.weatherchallenge.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.location.LocationServices;
import com.pacheco.weatherchallenge.viewmodels.AndroidViewModelFactory;
import com.pacheco.weatherchallenge.databinding.ActivityMainBinding;
import com.pacheco.weatherchallenge.recycler.DiffCallback;
import com.pacheco.weatherchallenge.recycler.RecyclerListAdapter;
import com.pacheco.weatherchallenge.utils.Constants;
import com.pacheco.weatherchallenge.viewmodels.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        RecyclerListAdapter adapter = new RecyclerListAdapter(new DiffCallback(), response ->
                startActivity(new Intent(this, DetailsActivity.class)
                        .putExtra(Constants.CITY_ID, response.getId())));

        setContentView(binding.getRoot());

        binding.recyclerview.setAdapter(adapter);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new AndroidViewModelFactory(getApplication(), null,
                LocationServices.getFusedLocationProviderClient(this))
                .create(MainViewModel.class);
        viewModel.getAllCities().observe(this, adapter::submitList);

        checkPermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        viewModel.onRequestPermissionsResult(requestCode, grantResults);
    }

    private void checkPermissions() {
        if (isPermissionsGiven()) {
            viewModel.onPermissionsGranted();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.REQUEST_CODE);
        }
    }

    private boolean isPermissionsGiven() {
        boolean isFineGranted = ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        boolean isCoarseGranted = ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        return isFineGranted && isCoarseGranted;
    }
}