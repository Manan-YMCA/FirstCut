package com.manan.dev.shineymca.Models;

import java.util.HashMap;

public class User {
    private String userName;
    private String userID;
    private String[] userClubsInterested;
    private HashMap<String,Integer> userSettingsMap;
    //TODO Add more properties here
    public User(String userName, String userID) {
        this.userName = userName;
        this.userID = userID;
        //this.userSettingsMap = ???? ; define default settings here
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String[] getUserClubsInterested() {
        return userClubsInterested;
    }

    public void setUserClubsInterested(String[] userClubsInterested) {
        this.userClubsInterested = userClubsInterested;
    }

    public HashMap<String, Integer> getUserSettingsMap() {
        return userSettingsMap;
    }

    public void setUserSettingsMap(HashMap<String, Integer> userSettingsMap) {
        this.userSettingsMap = userSettingsMap;
    }
}
