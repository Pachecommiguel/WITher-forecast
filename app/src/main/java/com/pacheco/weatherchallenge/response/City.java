package com.pacheco.weatherchallenge.response;

import androidx.annotation.Nullable;

import java.util.List;

public class City {

    private Coord coord;
    private List<Weather> weatherList;
    private String base;
    private Main main;
    private Integer visibility;
    private Wind wind;
    private Clouds clouds;
    private Integer dt;
    private Sys sys;
    private Integer timezone;
    private Integer id;
    private String name;
    private Integer cod;

    public Coord getCoord() {
        return coord;
    }

    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public String getBase() {
        return base;
    }

    public Main getMain() {
        return main;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public Integer getDt() {
        return dt;
    }

    public Sys getSys() {
        return sys;
    }

    public Integer getTimezone() {
        return timezone;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCod() {
        return cod;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof City) {
            return id.equals(((City) obj).id);
        }

        return super.equals(obj);
    }
}
