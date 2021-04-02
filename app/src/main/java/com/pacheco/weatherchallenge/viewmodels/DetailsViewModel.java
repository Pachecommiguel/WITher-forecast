package com.pacheco.weatherchallenge.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.pacheco.weatherchallenge.response.Response;
import com.pacheco.weatherchallenge.retrofit.Repository;

public class DetailsViewModel extends AndroidViewModel {

    public final LiveData<Response> response;

    public DetailsViewModel(@NonNull Application application, Integer id) {
        super(application);
        response = Repository.getInstance().getResponseById(id);
    }
}
