package com.pacheco.weatherchallenge.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.pacheco.weatherchallenge.response.Response;
import com.pacheco.weatherchallenge.retrofit.Repository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    public final LiveData<List<Response>> responseList;

    public MainViewModel(@NonNull Application application) {
        super(application);
        responseList = new Repository().getResponseLiveList();
    }

    public LiveData<List<Response>> getResponseList() {
        return responseList;
    }
}
