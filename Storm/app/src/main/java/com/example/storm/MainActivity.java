package com.example.storm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    private Weather currentWeather;
    private TextView day,temp, description, hour, humidity, uvi, city;
    private ImageView iconweather;
    private  ArrayList<Weather> sevenday;
    private RecyclerView listDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create URL
        String keyAPI = "b6bb4c2415f827ce1c8b75b35d5b048d";
        double latitude =  10.762622;
        double longitude = 106.660172;
        String URLforecast = "https://api.openweathermap.org/data/2.5/onecall?lat="+latitude+"&lon="+longitude+"&exclude=minutely&units=metric&appid="+keyAPI;
        Log.v(TAG,URLforecast);

        //connect with OkHttp
        if (isNetworkAvailable()){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(URLforecast).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
               try {
                   String JsonWeatherData =Objects.requireNonNull(response.body()).string();
                   Log.v(TAG, JsonWeatherData);
                   if (response.isSuccessful()){

                       currentWeather = getCurrentWeatherDetail(JsonWeatherData);
                       sevenday = getWeatherofSevenday(JsonWeatherData);
                       runOnUiThread(new Runnable() {

                           @Override
                           public void run() {
                               // Stuff that updates the UI
                               displayScreen(currentWeather);
                               displayRecycler(sevenday);
                           }
                       });
                   }
                   else{
                       alertUserAboutError();
                   }
               } catch (IOException e){
                    Log.e(TAG,"IOException: " + e);
               } catch (JSONException e){
                   Log.e(TAG,"JSONException: " + e);
               }
            }
        });
        }
        //displayScreen(currentWeather);
    }

    private void displayRecycler(ArrayList<Weather> sevenday) {
        listDay = findViewById(R.id.recycler_Day);
        WeatherDailyAdapter weatherDailyAdapter = new WeatherDailyAdapter(sevenday);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listDay.setAdapter(weatherDailyAdapter);
        listDay.setLayoutManager(linearLayoutManager);
    }

    private ArrayList<Weather> getWeatherofSevenday(String jsonWeatherData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonWeatherData);
        JSONArray daily = forecast.getJSONArray("daily");
        ArrayList<Weather> weatherArrayList = new ArrayList<>();


        for(int i = 0;i<7;i++){
            JSONObject day = daily.getJSONObject(i);
            Weather weatherday = new Weather();
            JSONObject temp =  day.getJSONObject("temp");
            JSONArray weatherArray = day.getJSONArray("weather");
            JSONObject weather = weatherArray.getJSONObject(0);

            weatherday.setTimezone(forecast.getString("timezone"));
            weatherday.setTime(day.getLong("dt")*1000);
            weatherday.setDescription(weather.getString("description"));
            weatherday.setHumidity(day.getInt("humidity"));
            weatherday.setIcon(weather.getString("icon"));
            weatherday.setMain(weather.getString("main"));
            weatherday.setUiv(day.getDouble("uvi"));
            weatherday.setId(weather.getLong("id"));
            weatherday.setMintemperature(temp.getDouble("min"));
            weatherday.setMaxtemperature(temp.getDouble("max"));
            weatherArrayList.add(weatherday);
        }
        return weatherArrayList;
    }

    private void displayScreen(Weather currentWeather) {
        day = (TextView)findViewById(R.id.textViewDay);
        temp = (TextView)findViewById(R.id.textViewTemp);
        description = (TextView)findViewById(R.id.textViewDiscription);
        hour = (TextView)findViewById(R.id.textViewHour) ;
        iconweather = (ImageView)findViewById(R.id.imageViewIconWeather);
        humidity = (TextView)findViewById(R.id.textViewhumidity);
        uvi = (TextView)findViewById(R.id.textViewUvi);
        city = (TextView)findViewById(R.id.textViewCity);

        day.setText(currentWeather.getDataFormattedFullTime());
        temp.setText(String.valueOf( Math.round(currentWeather.getTemperature())));
        String descrip = currentWeather.getDescription();
        descrip = descrip.substring(0,1).toUpperCase()+ descrip.substring(1).toLowerCase();
        description.setText(descrip);
        hour.setText(currentWeather.getFormattedTime());
        city.setText(currentWeather.getTimezone());

        humidity.setText(currentWeather.getStringHumidity());
        uvi.setText(currentWeather.getStringUvi());

        String iconUrl = "https://openweathermap.org/img/wn/" + currentWeather.getIcon() + "@2x.png";
        Picasso.get().load(iconUrl).into(iconweather);
    }


    private Weather getCurrentWeatherDetail(String jsonWeatherData) throws JSONException{

            JSONObject forecast = new JSONObject(jsonWeatherData);
            String timezone = forecast.getString("timezone");
            JSONObject current = forecast.getJSONObject("current");
            JSONArray currentWeatherArray = current.getJSONArray("weather");
            JSONObject currentWeatherObj = currentWeatherArray.getJSONObject(0);
            currentWeather = new Weather();
            currentWeather.setTimezone(timezone);
            currentWeather.setTime(current.getLong("dt")*1000);
            currentWeather.setDescription(currentWeatherObj.getString("description"));
            currentWeather.setHumidity(current.getInt("humidity"));
            currentWeather.setIcon(currentWeatherObj.getString("icon"));
            currentWeather.setTemperature(current.getDouble("temp"));
            currentWeather.setMain(currentWeatherObj.getString("main"));
            currentWeather.setUiv(current.getDouble("uvi"));
            currentWeather.setId(currentWeatherObj.getLong("id"));
            Log.v(TAG,currentWeather.getDataFormattedFullTime());
            return currentWeather;
    }





    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean Available = false;
        if(networkInfo != null && networkInfo.isAvailable()){
            Available = true;
        }
        else{
            Toast.makeText(this,"The network is unvailable",Toast.LENGTH_LONG).show();
        }
        return Available;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getSupportFragmentManager() ,"error_dialog");
    }
}