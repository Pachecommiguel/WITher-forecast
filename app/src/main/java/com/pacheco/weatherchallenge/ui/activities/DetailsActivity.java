package com.pacheco.weatherchallenge.ui.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.pacheco.weatherchallenge.viewmodels.AndroidViewModelFactory;
import com.pacheco.weatherchallenge.R;
import com.pacheco.weatherchallenge.databinding.ActivityDetailsBinding;
import com.pacheco.weatherchallenge.utils.Constants;
import com.pacheco.weatherchallenge.viewmodels.DetailsViewModel;

public class DetailsActivity extends AppCompatActivity {

    private DetailsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailsBinding binding = ActivityDetailsBinding.inflate(getLayoutInflater());

        setUpViewModel(binding);
        setContentView(binding.getRoot());
        setSupportActionBar(findViewById(R.id.toolbar));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_item, menu);
        return true;
    }

    public void onDeleteItemClick(MenuItem item) {
        viewModel.onDeleteItemClick();
        finish();
    }

    private void setUpViewModel(ActivityDetailsBinding binding) {
        viewModel = new AndroidViewModelFactory(getApplication(),
                getIntent().getExtras().getInt(Constants.CITY_ID))
                .create(DetailsViewModel.class);

        viewModel.getCity().observe(this, city -> {
            if (city != null) {
                getSupportActionBar().setTitle(city.getName());
            }
        });

        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
    }
}