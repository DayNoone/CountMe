package com.mobile.countme.implementation.models;

import com.mobile.countme.framework.AppMenu;

/**
 * Created by Robin on 14.10.2015.
 */
public class UserModel extends AppMenu{

    private float weight;
    private int birthYear;
    private String gender;


    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getAge() {
    //TODO: Implement this.
        return 1;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
