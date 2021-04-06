package com.pacheco.weatherchallenge.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.pacheco.weatherchallenge.retrofit.response.City;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = City.class, version = 1, exportSchema = false)
public abstract class AppRoom extends RoomDatabase {

    private static AppRoom instance;
    public abstract CityDao cityDao();
    public static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    public static AppRoom getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppRoom.class,"city_database")
            .fallbackToDestructiveMigration()
            .build();
        }

        return instance;
    }
}
