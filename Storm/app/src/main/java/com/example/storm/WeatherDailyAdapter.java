package com.example.storm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class WeatherDailyAdapter extends RecyclerView.Adapter<WeatherDailyAdapter.WeatherDailyHolder>{
    private final List<Weather> sevenday;

    public WeatherDailyAdapter(List<Weather> sevendayList) {
        sevenday = sevendayList;
    }

    @NonNull
    @Override
    public WeatherDailyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day_recycleview,parent,false);
        return new WeatherDailyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherDailyHolder holder, int position) {
        Weather day = sevenday.get(position);
        if (position == 0) {
            holder.description.setText(day.getDescription());
            String humidityString = day.getStringHumidity();
            holder.humidity.setText(humidityString);
            String temp = Math.round(day.getMintemperature())+"°C/"+Math.round(day.getMaxtemperature())+"°C";
            holder.tempMaxMin.setText(temp);
            holder.DayOfWeek.setText("Today");
        }
        else if(position == 1){
            holder.description.setText(day.getDescription());
            String humidityString = day.getStringHumidity();
            holder.humidity.setText(humidityString);
            String temp = Math.round(day.getMintemperature())+"°C/"+Math.round(day.getMaxtemperature())+"°C";
            holder.tempMaxMin.setText(temp);
            holder.DayOfWeek.setText("Tomorrow");
        }
        else{
            holder.description.setText(day.getDescription());
            String humidityString = day.getStringHumidity();
            holder.humidity.setText(humidityString);
            String temp = Math.round(day.getMintemperature())+"°C/"+Math.round(day.getMaxtemperature())+"°C";
            holder.tempMaxMin.setText(temp);
            holder.DayOfWeek.setText(day.getDataFormattedFullTime());
        }
    }

    @Override
    public int getItemCount() {
        return sevenday.size();
    }

    public static class WeatherDailyHolder extends RecyclerView.ViewHolder {
        ImageView imageWeatherItem;
        TextView humidity;
        TextView description;
        TextView tempMaxMin;
        TextView DayOfWeek;
        public WeatherDailyHolder(@NonNull View itemView) {
            super(itemView);
            imageWeatherItem = itemView.findViewById(R.id.imageViewWeather_item);
            humidity = itemView.findViewById(R.id.textViewHumidityItem);
            description = itemView.findViewById(R.id.textViewDescriptionItem);
            tempMaxMin = itemView.findViewById(R.id.textViewMaxMinTemp);
            DayOfWeek = itemView.findViewById(R.id.textViewDay_item);
        }
    }
}

