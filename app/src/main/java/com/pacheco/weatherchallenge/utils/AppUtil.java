package com.pacheco.weatherchallenge.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public abstract class AppUtil {

    public static boolean isFirstTimeLaunch(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("com.pacheco.weatherchallenge", MODE_PRIVATE);
        boolean isFirstRun = preferences.getBoolean(Constants.FIRST_RUN, true);

        if (isFirstRun) {
            preferences.edit().putBoolean(Constants.FIRST_RUN, false).apply();
        }

        return isFirstRun;
    }
}
