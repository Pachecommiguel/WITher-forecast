package com.pacheco.weatherchallenge.ui.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.pacheco.weatherchallenge.R;
import com.pacheco.weatherchallenge.databinding.ActivityAddBinding;
import com.pacheco.weatherchallenge.viewmodels.AddViewModel;

public class AddActivity extends AppCompatActivity {

    private AddViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityAddBinding binding = DataBindingUtil.setContentView(this,
                R.layout.activity_add);
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(AddViewModel.class);

        binding.setViewModel(viewModel);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(R.string.search);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.confirm_item, menu);
        return true;
    }

    public void onConfirmItemClick(MenuItem item) {
        viewModel.onConfirmItemClick();
        finish();
    }
}