package com.pacheco.weatherchallenge.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.pacheco.weatherchallenge.Repository;

public class AddViewModel extends AndroidViewModel {

    private final Repository repository;
    public final MutableLiveData<String> cityName = new MutableLiveData<>();

    public AddViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance();
    }

    public void onAddButtonClick() {
        if (cityName.getValue() != null) {
            repository.addCityByName(cityName.getValue().trim());
        }
    }
}
