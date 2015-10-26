package com.mobile.countme.implementation.controllers;

/**
 * Created by Torgeir on 16.10.2015.
 */
public class LoginInfo {

    private String userID;
    private String token;

    private String username;

    private String password;
    private boolean hasInfo;
    private boolean isLoggedIn;

    public LoginInfo(){
        isLoggedIn = false;
        hasInfo = false;
    }
    public void resetInfo(){
        username = null;
        password = null;
        hasInfo = false;
    }
    public void resetLogin(){
        token = null;
        userID = null;
        isLoggedIn = false;
    }

    public void setPassword(String password) {
        this.password = password;
        if(this.password != null && username != null){
            hasInfo = true;
        }
    }

    public void setUsername(String username) {
        this.username = username;
        if(this.username != null && password != null){
            hasInfo = true;
        }
    }


    public void setUserID(String userID) {
        this.userID = userID;
        if(token != null && userID != null){
            isLoggedIn = true;
        }
    }

    public void setToken(String token) {
        this.token = token;
        if(token != null && userID != null){
            isLoggedIn = true;
        }
    }


    public String getUsername() {
        return username;
    }

    public String getUserID() {

        return userID;
    }

    public String getToken() {
        return token;
    }
    public String getPassword(){
        return password;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }
    public boolean hasInfo(){
        return hasInfo;
    }
}
