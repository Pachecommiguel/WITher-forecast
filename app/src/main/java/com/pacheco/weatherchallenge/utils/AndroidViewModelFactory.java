package com.pacheco.weatherchallenge.utils;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.pacheco.weatherchallenge.viewmodels.DetailsViewModel;

public class AndroidViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {

    private final Application application;
    private final Integer id;

    public AndroidViewModelFactory(@NonNull Application application, Integer id) {
        super(application);
        this.application = application;
        this.id = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailsViewModel(application, id);
    }
}
