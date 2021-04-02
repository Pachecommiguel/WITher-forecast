package com.pacheco.weatherchallenge.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.pacheco.weatherchallenge.response.Response;
import com.pacheco.weatherchallenge.retrofit.Repository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    public final LiveData<List<Response>> response;

    public MainViewModel(@NonNull Application application) {
        super(application);
        response = new Repository().getResponse();
    }

    public LiveData<List<Response>> getResponse() {
        return response;
    }
}
