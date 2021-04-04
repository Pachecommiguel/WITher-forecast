package com.pacheco.weatherchallenge.retrofit.response;

import androidx.annotation.Nullable;

public class City {

    private int id;
    private Main main;
    private Wind wind;
    private Clouds clouds;
    private String name;

    public City(int id, String name, double mainTemp, int mainPressure, int mainHumidity,
                double windSpeed, int windDeg, int cloudsAll) {
        this.name = name;
        this.id = id;
        main = new Main(mainTemp, mainPressure, mainHumidity);
        wind = new Wind(windSpeed, windDeg);
        clouds = new Clouds(cloudsAll);
    }

    public City(int id, String name) {
        this.id = id;
        this.name = name;
        main = new Main();
    }

    public int getId() {
        return id;
    }

    public Main getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof City) {
            return id == ((City) obj).getId();
        }

        return super.equals(obj);
    }
}
