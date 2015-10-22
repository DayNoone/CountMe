package com.mobile.countme.implementation.models;

import android.location.Location;

import java.util.ArrayList;

/**
 * Created by Robin on 01.10.2015.
 * This class contains all the information(from today) the StatisticsTab will display.
 */
public class TripModel {

    private int co2_saved;
    private double distance;
    private double avg_speed;
    private int kcal;

    private ArrayList<Location> trips;

    public double getAvg_speed() {
        return avg_speed;
    }

    public int getCo2_saved() {
        return co2_saved;
    }

    public double getDistance() {
        return distance;
    }

    public ArrayList<Location> getTrips() {
        return trips;
    }

    public int getKcal() {
        return kcal;
    }

    public void setAvg_speed(double avg_speed) {
        this.avg_speed = avg_speed;
    }

    public void setCo2_saved(int co2_saved) {
        this.co2_saved = co2_saved;
    }

    public void setDistance(double distance) {
        this.distance = distance/1000;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public void setTrips(ArrayList<Location> trips) {
        this.trips = trips;
    }
}
