package com.mobile.countme.implementation.models;

/**
 * Created by Robin on 01.10.2015.
 * This class contains all the information(from today) the StatisticsMenu will display.
 */
public class SingleTripModel {

    private int co2_saved;
    private double distance;
    private double avg_speed;

    //TODO: Create ArrayList with errors reported this trip. May need to create an ErrorModel that contains both a description and a picture.

    public double getAvg_speed() {
        return avg_speed;
    }

    public int getCo2_saved() {
        return co2_saved;
    }

    public double getDistance() {
        return distance;
    }

    public void setAvg_speed(double avg_speed) {
        this.avg_speed = avg_speed;
    }

    public void setCo2_saved(int co2_saved) {
        this.co2_saved = co2_saved;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
