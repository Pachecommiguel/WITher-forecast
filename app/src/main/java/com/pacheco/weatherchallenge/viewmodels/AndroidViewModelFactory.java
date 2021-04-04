package com.pacheco.weatherchallenge.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;

public class AndroidViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {

    private final Application application;
    private int id;
    private FusedLocationProviderClient client;

    public AndroidViewModelFactory(@NonNull Application application, int id) {
        super(application);
        this.application = application;
        this.id = id;
    }

    public AndroidViewModelFactory(@NonNull Application application,
                                   FusedLocationProviderClient client) {
        super(application);
        this.application = application;
        this.client = client;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == DetailsViewModel.class) {
            return (T) new DetailsViewModel(application, id);
        }

        return (T) new MainViewModel(application, client);
    }
}
