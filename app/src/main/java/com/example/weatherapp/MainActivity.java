package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.adapter.WeatherForecastAdapter;
import com.example.weatherapp.model.WeatherForecast;
import com.google.android.gms.location.FusedLocationProviderClient;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    final String APP_ID = "bffca17bcb552b8c8e4f3b82f64cccd2";
    final long MIN_TIME = 5000;
    final float MIN_DISTANCE = 1000;
    final int REQUEST_CODE = 100;
    FusedLocationProviderClient fusedLocationProviderClient;
    EditText editText;
    Button button;
    ImageView imageView, imgMan;
    TextView temptv, time, longitude, latitude, humidity, sunrise, sunset, pressure, wind, country, city_nam, max_temp, min_temp, feels;
    RecyclerView rvWeatherForecast;
    private ArrayList<WeatherForecast> weatherForecastArrayList;
    private WeatherForecastAdapter weatherForecastAdapter;
    private String cityName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextTextPersonName);
        button = findViewById(R.id.button);
        imageView = findViewById(R.id.imageView);
        temptv = findViewById(R.id.textView3);
        time = findViewById(R.id.textView2);
        imgMan = findViewById(R.id.imgMan);

        longitude = findViewById(R.id.longitude);
        latitude = findViewById(R.id.latitude);
        humidity = findViewById(R.id.humidity);
        sunrise = findViewById(R.id.sunrise);
        sunset = findViewById(R.id.sunset);
        pressure = findViewById(R.id.pressure);
        wind = findViewById(R.id.wind);
        country = findViewById(R.id.country);
        city_nam = findViewById(R.id.city_nam);
        max_temp = findViewById(R.id.temp_max);
        min_temp = findViewById(R.id.min_temp);
        feels = findViewById(R.id.feels);
        rvWeatherForecast = findViewById(R.id.rvWeatherForecast);
        weatherForecastArrayList = new ArrayList<>();
        weatherForecastAdapter = new WeatherForecastAdapter(this, weatherForecastArrayList);
        rvWeatherForecast.setAdapter(weatherForecastAdapter);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        getWeatherForCurrentLocation();

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getWeather(editText.getText().toString());
                //startActivity(new Intent(MainActivity.this, WeatherLocation.class));
            }
        });

        imgMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(MainActivity.this, FavoriteCitiesActivity.class));
            }
        });


    }

    public void getWeather(String city)
        {
            String url ="http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+APP_ID+"&units=metric";
            StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                    new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        //find temperature
                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject object = jsonObject.getJSONObject("main");
                        int temp = object.getInt("temp");
                        temptv.setText("Temperature\n"+temp+"°C");

                        //find country
                        JSONObject object8 = jsonObject.getJSONObject("sys");
                        String count = object8.getString("country");
                        country.setText(count+"  :");

                        //find city
                        String city = jsonObject.getString("name");
                        city_nam.setText(city);

                        //find icon
                        JSONArray jsonArray = jsonObject.getJSONArray("weather");
                        JSONObject obj = jsonArray.getJSONObject(0);
                        String icon = obj.getString("icon");
                        Picasso.get().load("http://openweathermap.org/img/wn/"+icon+"@2x.png").into(imageView);

                        //find date & time
                        //Calendar calendar = Calendar.getInstance();
                        //SimpleDateFormat std = new SimpleDateFormat("HH:mm a \nE, MMM dd yyyy");
                        //String date = std.format(calendar.getTime());
                        //time.setText(date);

                        //find latitude
                        JSONObject object2 = jsonObject.getJSONObject("coord");
                        double lat_find = object2.getDouble("lat");
                        latitude.setText(lat_find+"°  N");

                        //find longitude
                        JSONObject object3 = jsonObject.getJSONObject("coord");
                        double long_find = object3.getDouble("lon");
                        longitude.setText(long_find+"°  E");

                        //find humidity
                        JSONObject object4 = jsonObject.getJSONObject("main");
                        int humidity_find = object4.getInt("humidity");
                        humidity.setText(humidity_find+"  %");

                        //find sunrise
                        JSONObject object5 = jsonObject.getJSONObject("sys");
                        String sunrise_find = object5.getString("sunrise");
                        sunrise.setText(sunrise_find);

                        //find sunrise
                        JSONObject object6 = jsonObject.getJSONObject("sys");
                        String sunset_find = object6.getString("sunset");
                        sunset.setText(sunset_find);

                        //find pressure
                        JSONObject object7 = jsonObject.getJSONObject("main");
                        String pressure_find = object7.getString("pressure");
                        pressure.setText(pressure_find+"  hPa");

                        //find wind speed
                        JSONObject object9 = jsonObject.getJSONObject("wind");
                        String wind_find = object9.getString("speed");
                        wind.setText(wind_find+"  km/h");

                        //find min temperature
                        JSONObject object10 = jsonObject.getJSONObject("main");
                        int mintemp = object10.getInt("temp_min") ;
                        min_temp.setText("Min Temp\n"+mintemp+" °C");

                        //find max temperature
                        JSONObject object12 = jsonObject.getJSONObject("main");
                        int maxtemp = object12.getInt("temp_max");
                        max_temp.setText("Max Temp\n"+maxtemp+" °C");

                        //find feels
                        JSONObject object13 = jsonObject.getJSONObject("main");
                        double feels_find = object13.getDouble("feels_like");
                        feels.setText(feels_find+" °C");



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this,error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            });

            getWeatherForecast(city);

            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(stringRequest);
        }
    private void getWeatherForCurrentLocation() {
        getLastLocation();
    }

    private void getLastLocation() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if(location != null) {
                                cityName = getCityName(location.getLongitude(), location.getLatitude());
                                getWeather(cityName);

                            }
                        }
                    });
        }
        else {
            askPermission();
        }
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }



    private void getWeatherForecast(String city) {
        String url ="http://api.openweathermap.org/data/2.5/forecast?q="+city+"&appid="+APP_ID+"&units=metric";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        weatherForecastArrayList.clear();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray forecastArray = jsonObject.getJSONArray("list");
                            for(int i = 0; i <forecastArray.length(); i++) {
                                JSONObject forecastObj = forecastArray.getJSONObject(i);
                                String time = forecastObj.getString("dt_txt");
                                String temp =Integer.toString(forecastObj.getJSONObject("main").getInt("temp"));
                                String icon = forecastObj.getJSONArray("weather").getJSONObject(0).getString("icon");
                                weatherForecastArrayList.add(new WeatherForecast(time, temp, icon));

                            }
                            weatherForecastAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);

    }

    private String getCityName(double longtitude, double latitude) {
        String city = "Not Found";
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        try {
            List<Address> addresses = gcd.getFromLocation(latitude, longtitude, 10);
            for(Address addr : addresses) {
                if (addr != null) {
                    String cityy = addr.getLocality();
                    if (cityy != null && !cityy.equals("")) {
                        city = cityy;
                    } else {
                        Log.d("TAG", "CITY NOT FOUND");
                        Toast.makeText(this, "City not found!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return city;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getWeatherForCurrentLocation();
            }
            else {
                Toast.makeText(this, "Required Permission",Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (weatherForecastAdapter != null ) {
            weatherForecastAdapter.release();
        }
    }
}