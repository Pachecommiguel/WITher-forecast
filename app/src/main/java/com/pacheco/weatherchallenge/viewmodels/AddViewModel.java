package com.pacheco.weatherchallenge.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.pacheco.weatherchallenge.Repository;
import com.pacheco.weatherchallenge.retrofit.response.City;
import com.pacheco.weatherchallenge.retrofit.response.Coord;

public class AddViewModel extends AndroidViewModel {

    private final Repository repository;
    public final MutableLiveData<City> city = new MutableLiveData<>();

    public AddViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance();
        city.setValue(new City());
    }

    public void onConfirmItemClick() {
        String name = city.getValue().getName();

        if ((name != null) && (!name.trim().equals(""))) {
            repository.addCityByName(name.trim());
        } else {
            Coord coord = city.getValue().getCoord();
            repository.addCityByCoordinates(coord.getLat(), coord.getLon());
        }
    }
}
