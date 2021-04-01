package com.pacheco.weatherchallenge.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.pacheco.weatherchallenge.R;
import com.pacheco.weatherchallenge.retrofit.Repository;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO testing
        Repository repository = new Repository();
        repository.getResponse().observe(this, response ->
                Log.e("TAG", "onCreate: " + response.getName() ));
    }
}