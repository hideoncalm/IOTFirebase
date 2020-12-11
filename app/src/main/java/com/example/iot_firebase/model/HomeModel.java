package com.example.iot_firebase.model;

public class HomeModel {
    private int homeId;
    private String homeName;

    public String getHomeName() {
        return homeName;
    }
    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }
    public int getHomeId() {
        return homeId;
    }
    public void setHomeId(int homeId) {
        this.homeId = homeId;
    }
}
