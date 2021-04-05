package com.pacheco.weatherchallenge.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.pacheco.weatherchallenge.Repository;
import com.pacheco.weatherchallenge.retrofit.response.City;
import com.pacheco.weatherchallenge.utils.Constants;

public class AddViewModel extends AndroidViewModel {

    private final Repository repository;
    public final MutableLiveData<City> city = new MutableLiveData<>();
    private final MutableLiveData<Boolean> status = new MutableLiveData<>();

    public AddViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance();
        city.setValue(new City());
    }

    public MutableLiveData<Boolean> getStatus() {
        return status;
    }

    public void onConfirmItemClick() {
        String name = city.getValue().getName();
        status.setValue(!((name == null) || name.trim().equals(Constants.EMPTY_FIELD)));

        if (status.getValue()) {
            repository.addCityByName(name.trim());
        }
    }
}
