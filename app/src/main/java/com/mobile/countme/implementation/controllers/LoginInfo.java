package com.mobile.countme.implementation.controllers;

/**
 * Created by Torgeir on 16.10.2015.
 */
public class LoginInfo {

    private String userID;
    private String token;
    private boolean isSet;


    public LoginInfo(){
        isSet = false;
    }

    public void setUserID(String userID) {
        this.userID = userID;
        if(token != null && userID != null){
            isSet = true;
        }
    }


    public void setToken(String token) {
        this.token = token;

        if(token != null && userID != null){
            isSet = true;
        }
    }

    public String getUserID() {

        return userID;
    }

    public String getToken() {
        return token;
    }

    public boolean isSet() {
        return isSet;
    }
}
