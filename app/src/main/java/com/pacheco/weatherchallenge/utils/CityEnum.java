package com.pacheco.weatherchallenge.utils;

public enum CityEnum {

    LISBON ("2267057"),
    MADRID ("3117735"),
    PARIS ("6455259"),
    BERLIN ("2950158"),
    COPENHAGEN ("2618425"),
    ROME ("3169070"),
    LONDON ("2643743"),
    DUBLIN ("2964574"),
    PRAGUE ("3067696"),
    VIENNA ("2761369");

    private String id;

    CityEnum(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
