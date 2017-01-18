package com.brz.http.bean;

/**
 * Created by dahuahua on 2017/1/16.
 */

public class WeatherInfo {
    private String city;
    private String weather;
    private String advise;
    private String temperature;
    private String temperatureRange;

    public void setCity(String city){
        this.city = city;
    }

    public String getCity() {
        return this.city;
    }

    public void setWeather(String weather){
        this.weather = weather;
    }

    public String getWeather() {
        return this.weather;
    }

    public void setAdvise(String advise){
        this.advise = advise;
    }

    public String getAdvise() {
        return this.advise;
    }

    public void setTemperature(String temperature){
        this.temperature = temperature;
    }

    public String getTemperature() {
        return this.temperature;
    }

    public void setTemperatureRange(String temperatureRange){
        this.temperatureRange = temperatureRange;
    }

    public String getTemperatureRange() {
        return this.temperatureRange;
    }

}
