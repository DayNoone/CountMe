package com.mobile.countme.implementation.models;

import com.mobile.countme.implementation.controllers.MainPages;

/**
 * Created by Robin on 27.09.2015.
 * This class contains all the information the EnvironmentMenu will display.
 */
public class EnvironmentModel {

    private int co2_savedToday;
    private int co2_carDistance;
    private final int avgCar_co2_per_kilometer = 271;

    public EnvironmentModel(){
    }

    /**
     * Adds the CO2 saved during the trip to the CO2 saved today so far.
     * @param co2_trip
     */
    public void addCo2_savedTrip(int co2_trip){
        co2_savedToday += co2_trip;
    }

    public int getCo2_savedToday() {
        return co2_savedToday;
    }

    /**
     * This value is derived from the co2 saved today value and is given in kilometers.
     * It uses the calculator from here: http://www.ecf.com/resources/co2-calculator/
     * @return
     */
    public int getCo2_carDistance() {
        co2_carDistance = co2_savedToday/avgCar_co2_per_kilometer;
        return co2_carDistance;
    }

    public void setStat(int index, int stat){
        if(index == 0){
            co2_savedToday = stat;
        }
        else if(index == 1){
            co2_carDistance = stat;
        }
    }

    public void resetStatistics(){
        co2_carDistance = 0;
        co2_savedToday = 0;
    }
}
