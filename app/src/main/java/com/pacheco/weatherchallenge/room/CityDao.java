package com.pacheco.weatherchallenge.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.pacheco.weatherchallenge.retrofit.response.City;

import java.util.List;

@Dao
public interface CityDao {

    @Insert
    void insert(City city);

    @Query("SELECT * FROM city_table ORDER BY name ASC")
    LiveData<List<City>> getAll();
}
