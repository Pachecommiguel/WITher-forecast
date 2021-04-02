package com.pacheco.weatherchallenge.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.pacheco.weatherchallenge.response.Response;
import com.pacheco.weatherchallenge.retrofit.Repository;

public class DetailsViewModel extends AndroidViewModel {

    public final LiveData<Response> response;
    private final Repository repository;

    public DetailsViewModel(@NonNull Application application, Integer id) {
        super(application);
        repository = Repository.getInstance();
        response = repository.getResponseById(id);
    }

    public void onRefreshItemClick() {
        repository.refreshResponseById(response.getValue().getId());
    }
}
