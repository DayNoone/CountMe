package com.mobile.countme.implementation.controllers;

import android.content.Context;
import android.location.Location;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Torgeir on 14.10.2015.
 */
public class HTTPSender {

    public HTTPSender() {

    }

    public static void sendTrip(ArrayList<Location> trip, Context context) {
        JSONObject jsonObject = null;

        /*
        var json = {
            "_userId": [unik brukar-ID],
            "startTime": [timestamp start],                      //"2014-02-13T15:00:00-0100";
            "endTime": [timestamp slutt],     //"2014-02-14T16:25:43-0100";
            "tripData": [dataPoints, feks arrayet de har],
            "purpose": [sikkert irrelevant for dykk, kan berre vere ein tom string],
            "OS": [Ext.os.name + " " + Ext.os.version]                        //OS Information
        };


         */
        try {
            jsonObject = new JSONObject();
            jsonObject.put("_userId", 0); //TODO fill in correct user id
            Date startTime = new Date(trip.get(0).getTime());
            Date endTime = new Date(trip.get(trip.size() - 1).getTime());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy'-'MM'-'dd'T'hh':'mm':'ss");
            sdf.setTimeZone(TimeZone.getTimeZone("CET"));
            sdf.format(startTime);
            sdf.format(endTime);
            jsonObject.put("startTime", startTime + "-0100");
            jsonObject.put("endTime", endTime + "-0100");
            JSONArray tripData = new JSONArray();
            JSONObject dataPoint;
            for (Location location : trip) {
                dataPoint = new JSONObject();
                /*
                                "lat":                  //item.get('Latitude'),
                                "lon":                 //item.get('Longitude'),
                                "time":                             //item.get('Timestamp'),
                                "mode":                           //item.get('TravelMode'),
                                "connection": //item.get('Connection'),
                                "altitude":                       //item.get('Altitude'),
                                "accuracy":      //item.get('Accuracy'),
                                "altitudeAccuracy":       //item.get('AltitudeAccuracy'),
                                "heading":                       //item.get('Heading'),
                                "speed":                           //item.get('Speed')

                 */

                dataPoint.put("lat", location.getLatitude());
                dataPoint.put("lon", location.getLongitude());
                dataPoint.put("time", sdf.format(new Date(location.getTime())) + "-0100");
                dataPoint.put("mode", "");
                //dataPoint.put("connection", )

            }

            jsonObject.put("tripData", trip); //TODO ?
            jsonObject.put("purpose", "");
            String versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            jsonObject.put("OS", versionName);
        } catch (org.json.JSONException e) {

        } catch (Exception e) {

        }

        if (jsonObject != null) {


        }
    }

}
