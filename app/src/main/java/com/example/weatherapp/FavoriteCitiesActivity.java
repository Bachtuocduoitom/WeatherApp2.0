package com.example.weatherapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.weatherapp.adapter.CityAdapter;
import com.example.weatherapp.model.City;

import java.util.ArrayList;

public class FavoriteCitiesActivity extends AppCompatActivity {

    RecyclerView rvCitiesList;
    ImageView imageBack;
    private CityAdapter cityAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_cities);

        imageBack.findViewById(R.id.imageBack);
        rvCitiesList.findViewById(R.id.rvCitiesList);

        cityAdapter = new CityAdapter(getListCity());
        
    }

    private ArrayList<City> getListCity() {
        ArrayList<City> list = new ArrayList<>();
        list.add(new City("Hanoi"));
        list.add(new City("London"));
        list.add(new City("Dalat"));
        list.add(new City("Tokyo"));


        return list;
    }
}