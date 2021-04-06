package com.pacheco.weatherchallenge.models;

import androidx.annotation.Nullable;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "city_table")
public class City {

    @PrimaryKey
    private int id;

    @Embedded
    private Main main;

    @Embedded
    private Wind wind;

    @Embedded
    private Clouds clouds;

    private String name;

    public City(int id, Main main, Wind wind, Clouds clouds, String name) {
        this.id = id;
        this.main = main;
        this.wind = wind;
        this.clouds = clouds;
        this.name = name;
    }

    @Ignore
    public City() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof City) {
            return id == ((City) obj).getId();
        }

        return super.equals(obj);
    }
}
