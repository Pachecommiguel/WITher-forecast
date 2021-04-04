package com.pacheco.weatherchallenge.retrofit.response;

public class Main {

    private double temp;
    private int pressure;
    private int humidity;

    public Main(double temp, int pressure, int humidity) {
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    public double getTemp() {
        return temp;
    }

    public int getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }
}
