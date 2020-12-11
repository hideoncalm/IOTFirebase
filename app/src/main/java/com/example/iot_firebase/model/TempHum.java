package com.example.iot_firebase.model;

public class TempHum {
    private int homeId;
    private int humidity;
    private int tempHumId;
    private int temperature;

    public TempHum() {
    }

    public TempHum(int homeId, int humidity, int tempHumId, int temperature) {
        this.homeId = homeId;
        this.humidity = humidity;
        this.tempHumId = tempHumId;
        this.temperature = temperature;
    }

    public int getTempHumId() {
        return tempHumId;
    }

    public void setTempHumId(int tempHumId) {
        this.tempHumId = tempHumId;
    }

    public int getHomeId() {
        return homeId;
    }

    public void setHomeId(int homeId) {
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
