package com.pacheco.weatherchallenge.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.pacheco.weatherchallenge.Repository;
import com.pacheco.weatherchallenge.retrofit.response.City;

public class AddViewModel extends AndroidViewModel {

    private final Repository repository;
    public final MutableLiveData<City> city = new MutableLiveData<>();
    private final MutableLiveData<Integer> statusCode;

    public AddViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance();
        statusCode = repository.getStatusCode();
        city.setValue(new City());
    }

    public MutableLiveData<Integer> getStatusCode() {
        return statusCode;
    }

    public void onConfirmItemClick() {
        String name = city.getValue().getName();

        if (name != null) {
            repository.addCityByName(name.trim());
        }
    }
}
