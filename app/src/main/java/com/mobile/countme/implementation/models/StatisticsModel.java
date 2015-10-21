package com.mobile.countme.implementation.models;

/**
 * Created by Robin on 01.10.2015.
 * This class contains all the information(from today) the StatisticsTab will display.
 */
public class StatisticsModel {

    private int co2_saved;
    private double distance;
    private double avg_speed;
    private int kcal;

    /**
     * Adds the co2 saved this trip to the co2 saved so far(this week or month).
     * @param co2_saved
     */
    public void addCo2_saved(int co2_saved) {
        this.co2_saved += co2_saved;
    }

    /**
     * Adds the distance travelled on this trip to the distance travelled so far(this week or month) in kilometers.
     * @param distance
     */
    public void addDistance(double distance){
        this.distance += distance/1000;
    }

    /**
     * Calculates the new average speed based on the current average speed and the average speed from the last trip.
     * @param avg_speed
     */
    public void calc_new_avgSpeed(double avg_speed){
        if(this.avg_speed != 0) {
            this.avg_speed = (this.avg_speed + avg_speed) / 2;
        }else {
            this.avg_speed = avg_speed;
        }
    }

    public double getAvg_speed() {
        return avg_speed;
    }

    public int getCo2_saved() {
        return co2_saved;
    }

    public double getDistance() {
        return distance;
    }

    public int getKcal() {
        return kcal;
    }

    /**
     * Resets all the statistics.
     */
    public void resetStatistics(){
        co2_saved = 0;
        distance = 0;
        avg_speed = 0;
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

    public void addKcal(int kcal) {
        this.kcal += kcal;
    }
}
