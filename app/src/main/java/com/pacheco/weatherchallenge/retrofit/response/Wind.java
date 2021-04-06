package com.pacheco.weatherchallenge.retrofit.response;

import androidx.room.Ignore;

public class Wind {

    private double speed;
    private int deg;

    @Ignore
    public Wind(double speed, int deg) {
        this.speed = speed;
        this.deg = deg;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }
}
