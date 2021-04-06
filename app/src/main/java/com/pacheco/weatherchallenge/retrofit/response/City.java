package com.pacheco.weatherchallenge.retrofit.response;

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

    @Embedded
    private Coord coord;

    @Ignore
    public City(int id, String name, double mainTemp, int mainPressure, int mainHumidity,
                double windSpeed, int windDeg, int cloudsAll) {
        this.name = name;
        this.id = id;
        main = new Main(mainTemp, mainPressure, mainHumidity);
        wind = new Wind(windSpeed, windDeg);
        clouds = new Clouds(cloudsAll);
    }

    @Ignore
    public City() {
        coord = new Coord();
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

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof City) {
            return id == ((City) obj).getId();
        }

        return super.equals(obj);
    }
}
