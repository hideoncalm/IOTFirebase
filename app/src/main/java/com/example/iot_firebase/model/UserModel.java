package com.example.iot_firebase.model;

public class UserModel {
    private String userName;
    private String password;
    private String userFullName;
    private String userId;
    private String homeId;

    public UserModel() {
    }

    public UserModel(String userName, String password, String userFullName, String userId, String homeId) {
        this.userName = userName;
        this.password = password;
        this.userFullName = userFullName;
        this.userId = userId;
        this.homeId = homeId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHomeId() {
        return homeId;
    }

    public void setHomeId(String homeId) {
        this.homeId = homeId;
    }
}
