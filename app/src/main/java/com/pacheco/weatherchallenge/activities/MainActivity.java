package com.pacheco.weatherchallenge.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.pacheco.weatherchallenge.databinding.ActivityMainBinding;
import com.pacheco.weatherchallenge.recycler.DiffCallback;
import com.pacheco.weatherchallenge.recycler.RecyclerListAdapter;
import com.pacheco.weatherchallenge.viewmodels.MainViewModel;

import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        RecyclerListAdapter adapter = new RecyclerListAdapter(new DiffCallback(), response -> {});

        setContentView(binding.getRoot());

        binding.recyclerview.setAdapter(adapter);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));

        MainViewModel viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(MainViewModel.class);
        viewModel.getResponse().observe(this, adapter::submitList);
    }
}