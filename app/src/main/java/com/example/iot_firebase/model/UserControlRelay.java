package com.example.iot_firebase.model;

public class UserControlRelay {
    private String userId;
    private String relayId;

    public UserControlRelay() {
    }

    public UserControlRelay(String userId, String relayId) {
        this.userId = userId;
        this.relayId = relayId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRelayId() {
        return relayId;
    }

    public void setRelayId(String relayId) {
        this.relayId = relayId;
    }
}
