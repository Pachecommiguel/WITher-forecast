package com.pacheco.weatherchallenge.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.location.LocationServices;
import com.pacheco.weatherchallenge.R;
import com.pacheco.weatherchallenge.databinding.ActivityMainBinding;
import com.pacheco.weatherchallenge.ui.AndroidViewModelFactory;
import com.pacheco.weatherchallenge.ui.recycler.DiffCallback;
import com.pacheco.weatherchallenge.ui.recycler.RecyclerListAdapter;
import com.pacheco.weatherchallenge.ui.viewmodels.MainViewModel;
import com.pacheco.weatherchallenge.utils.Constants;

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
        setSupportActionBar(findViewById(R.id.toolbar));

        setUpRecyclerView(adapter, binding);
        setUpViewModel(adapter);
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
        viewModel.onActivityResult(requestCode, resultCode);;
    }

    public void onRefreshItemClick(MenuItem item) {
        viewModel.onRefreshItemClick();
    }

    public void onAddItemClick(MenuItem item) {
        startActivity(new Intent(this, AddActivity.class));
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
        viewModel.getGpsStatus().observe(this, this::showAlertDialog);
        viewModel.getPermissionStatus().observe(this, this::requestPermissions);
    }

    private void requestPermissions(Boolean status) {
        if (status) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, Constants.LOCAL_REQUEST_CODE);
        }
    }

    private void showAlertDialog(Boolean status) {
        if (status) {
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
}