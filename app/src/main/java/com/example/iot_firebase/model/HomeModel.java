package com.example.iot_firebase.model;

public class HomeModel {
    private String homeId;
    private String homeName;
    public HomeModel(){}
    public HomeModel(String homeId, String homeName){this.homeId = homeId; this.homeName = homeName; }
    public String getHomeName() {
        return homeName;
    }
    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }
    public String getHomeId() {
        return homeId;
    }
    public void setHomeId(String homeId) {
        this.homeId = homeId;
    }
}
