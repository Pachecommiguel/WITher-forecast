package com.pacheco.weatherchallenge.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.pacheco.weatherchallenge.network.models.City;

import java.util.List;

@Dao
public interface CityDao {

    @Insert
    void insert(City city);

    @Delete
    void delete(City city);

    @Query("SELECT * FROM city_table ORDER BY name ASC")
    LiveData<List<City>> getAll();

    @Update
    void update(City city);
}
