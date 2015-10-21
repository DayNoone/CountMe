package com.mobile.countme.implementation.controllers;

import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.util.Log;

import com.mobile.countme.framework.GPSFilter;
import com.mobile.countme.implementation.models.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Torgeir on 14.10.2015.
 */
public class HTTPSender {

    private static final String SERVER_URL = "https://tf2.sintef.no:8084/smioTest/api/";
    //private static final String USERID = "560946d9b2af57c413ac8427";
    //private static final String TOKEN = "$2a$10$w1BPdOBqiuaYiKJ6a2qYdewOKOdk7fQ.LE3yjf6fvF5/YLtBi2Q8S";
    //private static final String USERNAME = "sondre";
    //private static final String PASSWORD = "dabchick402";

    static private LoginInfo info;


    public HTTPSender() {

    }


    //sendTrip method creates a json from an arraylist of locations, an arraylist of ints and a context
    //Then it uses delegation to send the json to the server via a specified url
    public static void sendTrip(ArrayList<Location> trip, ArrayList<Integer> connectionTypes, Context context) {
        logIn();
        synchronized (info) {
            try {
                while (!info.isLoggedIn()) {
                    info.wait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.d("SendTrip", "SendTrip started");

        GPSFilter.filterTrip(trip, connectionTypes);
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
            jsonObject.put("_userId", info.getUserID());
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
            Location location;
            for (int i = 0; i < trip.size(); i++) {
                location = trip.get(i);
                dataPoint = new JSONObject();
                /*
                                "lat":                  //item.get('Latitude'),
                                "lon":                 //item.get('Longitude'),
                                "time":                             //item.get('Timestamp'),
                                "mode":                           //item.get('TravelMode'),
                                "connection": //item.get('Connection'),
                                */


                dataPoint.put("lat", location.getLatitude());
                dataPoint.put("lon", location.getLongitude());
                dataPoint.put("time", sdf.format(new Date(location.getTime())) + "-0100");
                dataPoint.put("mode", "mobile");
                int connectionType = connectionTypes.get(i);
                switch (connectionType) {
                    case (ConnectivityManager.TYPE_BLUETOOTH):
                        dataPoint.put("mode", "bluethooth");
                        break;
                    case (ConnectivityManager.TYPE_DUMMY):
                        dataPoint.put("mode", "dummy");
                        break;
                    case (ConnectivityManager.TYPE_ETHERNET):
                        dataPoint.put("mode", "ethernet");
                        break;
                    case (ConnectivityManager.TYPE_MOBILE):
                        dataPoint.put("mode", "mobile");
                        break;
                    case (ConnectivityManager.TYPE_MOBILE_DUN):
                        dataPoint.put("mode", "mobile_dun");
                        break;
                    case (ConnectivityManager.TYPE_VPN):
                        dataPoint.put("mode", "vpn");
                        break;
                    case (ConnectivityManager.TYPE_WIFI):
                        dataPoint.put("mode", "wifi");
                        break;
                    case (ConnectivityManager.TYPE_WIMAX):
                        dataPoint.put("mode", "wimax");
                        break;
                    default:
                        dataPoint.put("mode", "");
                        break;


                }
                /*
                                "altitude":                       //item.get('Altitude'),
                                "accuracy":      //item.get('Accuracy'),
                                "altitudeAccuracy":       //item.get('AltitudeAccuracy'),
                                "heading":                       //item.get('Heading'),
                                "speed":                           //item.get('Speed')

                 */
                dataPoint.put("altitude", trip.get(i).getAltitude());
                dataPoint.put("accuracy", trip.get(i).getAccuracy());
                dataPoint.put("altitudeAccuracy", "");
                dataPoint.put("heading", "");
                dataPoint.put("speed", trip.get(i).getSpeed());
                tripData.put(dataPoint);

            }
            jsonObject.put("tripData", tripData);
            jsonObject.put("purpose", "");
            String versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            jsonObject.put("OS", versionName);
            Log.d("SendTrip", "JSON created successfully");
        }
        catch (Exception e) {
            //dirty fix to checked exceptions
            e.printStackTrace();
        }


        if (jsonObject != null) {
            String sendURL = SERVER_URL + "user/" + info.getUserID() + "/trips/?token=" + info.getToken();
            HttpSenderThread thread = new HttpSenderThread(jsonObject, sendURL, info, HttpPostKind.TRIP);
            thread.start();
        }
    }

    public static void logIn(){
        info = new LoginInfo();
        synchronized (info) {
            try {
                while (!info.hasInfo()) {
                    info.wait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try{

            JSONObject obj = new JSONObject();
            obj.put("username", info.getUsername());
            obj.put("password", info.getPassword());
            HttpSenderThread thread = new HttpSenderThread(obj, SERVER_URL, info, HttpPostKind.LOGIN);
            thread.start();
        }
        catch( Exception e){
            e.printStackTrace();
        }

    }

    public static void createUser(UserModel model) {
        info = new LoginInfo();
        JSONObject obj = null;
        try {
            obj = new JSONObject();
/*              username:                         userStore.getAt(0).get('username'),
               gender:                              userStore.getAt(0).get('gender'),
               maritalstatus:    userStore.getAt(0).get('maritalstatus'),
               occupation:                       userStore.getAt(0).get('occupation'),
               birthyear:                           userStore.getAt(0).get('birthyear'),
               subscription:      userStore.getAt(0).get('subscription'),
               residence:                          userStore.getAt(0).get('residence'),
               area:                                   userStore.getAt(0).get('area'),
               numchildren:      userStore.getAt(0).get('numchildren')
*/
            obj.put("username", model.getUsername());
            obj.put("birthyear", model.getBirthYear());
            obj.put("gender", model.getGender());
            obj.put("password", model.getPassword());
            //Potentially more things
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        if (obj != null) {
            String sendURL = SERVER_URL + "user/";
            HttpSenderThread thread = new HttpSenderThread(obj, sendURL, info, HttpPostKind.CREATEUSER);
            thread.start();
        }

    }



}





