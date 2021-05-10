package com.example.storm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Weather {
    private long id;
    private String main;

    public double getMintemperature() {
        return Mintemperature;
    }

    public void setMintemperature(double mintemperature) {
        Mintemperature = mintemperature;
    }

    public double getMaxtemperature() {
        return Maxtemperature;
    }

    public void setMaxtemperature(double maxtemperature) {
        Maxtemperature = maxtemperature;
    }

    private String description;
    private String icon;
    private int humidity;
    private double temperature;
    private long time;
    private String timezone;
    private double uiv;
    private double Mintemperature, Maxtemperature;

    public double getUiv() {
        return uiv;
    }

    public void setUiv(double uiv) {
        this.uiv = uiv;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getFormattedTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone(timezone));
        Date date = new Date(time);
        return formatter.format(date);
    }

    public String getDataFormattedFullTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy");
        formatter.setTimeZone(TimeZone.getTimeZone(timezone));
        Date date = new Date(time);
        return formatter.format(date);
    }

    public String getStringHumidity(){
        String textHumidity=String.valueOf(humidity);
        return textHumidity+"%";
    }

    public String getStringUvi(){
        String textUvi=String.valueOf(uiv);
        if(uiv<3){
            return textUvi+"/Low";
        }
        else if(uiv<6){
            return textUvi+"/Moderate";
        }
        else if(uiv<8){
            return textUvi+"/High";
        }
        else if(uiv<11){
            return textUvi+"/Very high";
        }
        else {
            return textUvi+"/Extreme";
        }

    }

}
