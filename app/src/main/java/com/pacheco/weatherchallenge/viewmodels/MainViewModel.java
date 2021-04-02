package com.pacheco.weatherchallenge.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.pacheco.weatherchallenge.response.Response;
import com.pacheco.weatherchallenge.retrofit.Repository;

public class MainViewModel extends AndroidViewModel {

    public final LiveData<Response> response;

    public MainViewModel(@NonNull Application application) {
        super(application);
        response = new Repository().getResponse();
    }

    public LiveData<Response> getResponse() {
        return response;
    }
}
