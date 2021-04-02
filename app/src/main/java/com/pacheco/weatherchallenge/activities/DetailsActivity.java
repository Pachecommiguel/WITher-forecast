package com.pacheco.weatherchallenge.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.pacheco.weatherchallenge.databinding.ActivityDetailsBinding;
import com.pacheco.weatherchallenge.utils.AndroidViewModelFactory;
import com.pacheco.weatherchallenge.utils.Constants;
import com.pacheco.weatherchallenge.viewmodels.DetailsViewModel;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DetailsViewModel viewModel = new AndroidViewModelFactory(getApplication(),
                getIntent().getExtras().getInt(Constants.CITY_ID)).create(DetailsViewModel.class);

        ActivityDetailsBinding binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        setContentView(binding.getRoot());
    }
}