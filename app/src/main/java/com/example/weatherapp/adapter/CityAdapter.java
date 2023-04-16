package com.example.weatherapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.model.City;

import java.util.ArrayList;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {
    private Context context;
    private ArrayList<City> cityArrayList;

    public CityAdapter(ArrayList<City> cityArrayList) {
        this.cityArrayList = cityArrayList;
    }

    @NonNull
    @Override
    public CityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityAdapter.ViewHolder holder, int position) {
        City city = cityArrayList.get(position);
        if (city == null) {
            return;
        }
        holder.rvCityName.setText(city.getCityName());
    }

    @Override
    public int getItemCount() {
        return cityArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView rvCityName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rvCityName.findViewById(R.id.rvCityName);
        }
    }
}
