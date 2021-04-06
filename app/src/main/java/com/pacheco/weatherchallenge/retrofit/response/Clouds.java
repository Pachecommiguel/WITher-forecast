package com.pacheco.weatherchallenge.retrofit.response;

import androidx.room.Ignore;

public class Clouds {

    private int all;

    @Ignore
    public Clouds(int all) {
        this.all = all;
    }

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }
}
