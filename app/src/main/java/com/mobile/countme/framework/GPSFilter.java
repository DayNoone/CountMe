package com.mobile.countme.framework;

import android.location.Location;

import java.util.ArrayList;

/**
 * Created by Kristian on 11/09/2015.
 * Renamed by Torgeir on 19/10/2015.
 */
public class GPSFilter {

    public static void filterTrip(ArrayList<Location> trip, ArrayList<Integer> modes){
        Location end = trip.get(trip.size() - 1);
        Location almostEnd;
        double accumulator = 0.0;
        double lengthSegment = 0.0;
        for(int i = trip.size() - 2; i >= 0; i --){
            almostEnd = trip.get(i);
            lengthSegment = almostEnd.distanceTo(end);
            if(lengthSegment < 20.0){
                accumulator += lengthSegment;
            }
            if( accumulator < 100.0){
                trip.remove(i);
                trip.remove(i);
            }
            else{
                trip.remove(i);
                trip.remove(i);
                break;
            }
            end = almostEnd;


        }
        Location start = trip.get(0);
        Location next;
        int lastIndex = 0;
        accumulator = 0.0;
        lengthSegment = 0.0;
        for(int i = 1; i < trip.size(); i ++){
            next = trip.get(i);
            lengthSegment = start.distanceTo(next);
            if(lengthSegment < 20.0){
                accumulator += lengthSegment;
            }
            if( accumulator < 100.0){

            }
            else{
                lastIndex = i;
                break;
            }
            start = next;


        }
        for(int i = lastIndex; i >= 0; i--){
            trip.remove(i);
            modes.remove(i);
        }

    }

}
