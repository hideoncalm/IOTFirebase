package com.example.iot_firebase.model;

public class RelayModel {
    private int homeId;
    private String relayId;
    private String relayName;
    private int status;
    public RelayModel(){ }
    public RelayModel(int homeId, String relayId, String relayName, int status) {
        this.homeId = homeId;
        this.relayId = relayId;
        this.relayName = relayName;
        this.status = status;
    }

    public String getRelayId() {
        return relayId;
    }

    public void setRelayId(String relayId) {
        this.relayId = relayId;
    }

    public int getHomeId() {
        return homeId;
    }

    public void setHomeId(int homeId) {
        this.homeId = homeId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
       this.status = status;
    }

    public String getRelayName() {
        return relayName;
    }

    public void setRelayName(String relayName) {
        this.relayName = relayName;
    }
}
