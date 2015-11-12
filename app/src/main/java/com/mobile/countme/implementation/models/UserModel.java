package com.mobile.countme.implementation.models;

import com.mobile.countme.framework.AppMenu;

import java.util.Calendar;

/**
 * Created by Robin on 14.10.2015.
 */
public class UserModel extends AppMenu{

    private Float weight;
    private Integer birthYear;
    private String gender;
    private Calendar calendar = Calendar.getInstance();
    private String username;
    private String password;
    private boolean receiveSurveys;


    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    /**
     * Returns the age of the user based on their birth year
     * @return
     */
    public int getAge() {
        return calendar.get(Calendar.YEAR) - birthYear;
    }

    public boolean isReceiveSurveys() {
        return receiveSurveys;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setReceiveSurveys(boolean receiveSurveys) {
        this.receiveSurveys = receiveSurveys;
    }
}
