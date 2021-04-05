package com.pacheco.weatherchallenge.utils;

import androidx.databinding.InverseMethod;

public class Converter {

    @InverseMethod("stringToDouble")
    public static String doubleToString(double value) {
        return (value == 0) ? "" : String.valueOf(value);
    }

    public static double stringToDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
