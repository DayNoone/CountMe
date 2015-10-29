package com.mobile.countme.framework;

import android.location.Location;

import java.util.ArrayList;

/**
 * Created by Kristian on 11/09/2015.
 * Renamed by Torgeir on 19/10/2015.
 */
public class GPSFilter {

    public static void filterTrip(ArrayList<Location> trip, ArrayList<Integer> modes) {
        if(trip.size() < 2){
            trip.clear();
            modes.clear();
            return;
        }

        Location end = trip.get(trip.size() - 1);
        Location almostEnd;
        float accumulator = 0.0f;
        float lengthSegment;
        for (int i = trip.size() - 2; i >= 0; i--) {
            almostEnd = trip.get(i);
            lengthSegment = almostEnd.distanceTo(end);
            if (lengthSegment < 20.0f) {
                accumulator += lengthSegment;
            }
            trip.remove(i + 1);
            modes.remove(i + 1);

            if (accumulator >= 100.0f) {
                break;
            }
            end = almostEnd;


        }
        if(trip.size() < 2){
            trip.clear();
            modes.clear();
            return;
        }
        Location start = trip.get(0);
        Location next;
        int lastIndex = 0;
        accumulator = 0.0f;
        for (int i = 1; i < trip.size(); i++) {
            next = trip.get(i);
            lengthSegment = start.distanceTo(next);
            if (lengthSegment < 20.0f) {
                accumulator += lengthSegment;
            }
            if( accumulator > 100.0f){
                lastIndex = i - 1;
                break;
            }
            start = next;


        }
        for (int i = lastIndex; i >= 0; i--) {
            trip.remove(i);
            modes.remove(i);
        }

    }

}
