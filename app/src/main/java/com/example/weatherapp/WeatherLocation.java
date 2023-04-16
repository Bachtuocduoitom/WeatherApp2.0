package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WeatherLocation extends AppCompatActivity {
    TextView textWeather;
    Button buttonBack;
    ListView locationList;
    SearchView searchLocationText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_location);

        textWeather = findViewById(R.id.textWeather);
        buttonBack = findViewById(R.id.buttonBack);
        searchLocationText = findViewById(R.id.searchLocationText);
        locationList = findViewById(R.id.locationList);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WeatherLocation.this, MainActivity.class));
            }
        });
    }
}
