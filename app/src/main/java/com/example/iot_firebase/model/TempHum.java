package com.example.iot_firebase.model;

public class TempHum {
    private String homeId;
    private int humidity;
    private String tempHumId;
    private int temperature;

    public TempHum() {
    }

    public TempHum(String homeId, int humidity, String tempHumId, int temperature) {
        this.homeId = homeId;
        this.humidity = humidity;
        this.tempHumId = tempHumId;
        this.temperature = temperature;
    }

    public String getTempHumId() {
        return tempHumId;
    }

    public void setTempHumId(String tempHumId) {
        this.tempHumId = tempHumId;
    }

    public String getHomeId() {
        return homeId;
    }

    public void setHomeId(String homeId) {
        this.homeId = homeId;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}
