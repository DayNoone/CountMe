/*
 * Copyright (c) 2015 Anders Lunde, Robin Sjøvoll, Kristian Huse. All rights reserved.
 */

package com.mobile.countme.implementation;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.mobile.countme.R;
import com.mobile.countme.framework.AppMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Anders Lunde on 22.01.2015.
 * Saves the user data as a string, or as shared pref data
 * FileIO is not used widely because server saves user data.
 */
public class AndroidFileIO {


//    private static File savedData;
    private AppMenu context;

    public AndroidFileIO(AppMenu context){
        this.context = context;
    }

    /**
     * Update the trip statistics in the internal storage.
     * @param todaysTrips
     */
    public void writeStatisticSaveFile(JSONObject todaysTrips){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.statisticsData), Context.MODE_PRIVATE);
        String tripsString = sharedPreferences.getString(context.getString(R.string.statisticsData), null);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        try {
            JSONArray trips = new JSONArray(tripsString);
            JSONObject lastDayTrips = (JSONObject)trips.get(trips.length() - 1);
            if(lastDayTrips.getString("TimeStamp").equals(todaysTrips.getString("TimeStamp"))){
                lastDayTrips.put("co2Saved", todaysTrips.get("co2Saved"));
                lastDayTrips.put("distance", todaysTrips.get("distance"));
                lastDayTrips.put("avgSpeed", todaysTrips.get("avgSpeed"));
                lastDayTrips.put("calories", todaysTrips.get("calories"));
            }else {
                trips.put(todaysTrips);
            }
            prefEditor.putString(context.getString(R.string.statisticsData), trips.toString());
            prefEditor.commit();
            Log.e("AndroidFileIO", "co2Saved: " + sharedPreferences.getString(context.getString(R.string.statisticsData), null));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update the user information in the internal storage.
     * @param userInfo
     */
    public void writeUserInformationSaveFile(JSONObject userInfo){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.userInfo), Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putString(context.getString(R.string.userInfo), userInfo.toString());
        prefEditor.commit();
        Log.e("AndroidFileIO", "userinfo: " + sharedPreferences.getString(context.getString(R.string.userInfo), null));
    }

    /**
     * Writes the initial trips statistics on first start of application.
     * @param jsonArray
     */
    public void writeInitialStatisticsSaveFile(JSONArray jsonArray){
        String string = jsonArray.toString();
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.statisticsData), Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putString( context.getString(R.string.statisticsData), string );
        prefEditor.commit();
        Log.e("AndroidFileIO", "tripsStatistics: " + sharedPreferences.getString(context.getString(R.string.statisticsData), null));
    }

    /**
     * Writes the initial user information on first start of application.
     * @param userInformation
     */
    public void writeInitialUserInformation(JSONObject userInformation){
        String string = userInformation.toString();
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.userInfo), Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putString( context.getString(R.string.userInfo), string );
        prefEditor.commit();
        Log.e("AndroidFileIO", "userInformation: " + sharedPreferences.getString(context.getString(R.string.userInfo), null));
    }

    public JSONArray readStatisticsSaveFile(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.statisticsData), Context.MODE_PRIVATE);
        String tripsString = sharedPreferences.getString(context.getString(R.string.statisticsData), null);
        JSONArray trips = null;
        try {
            trips = new JSONArray(tripsString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return trips;
    }

    public JSONObject readUserInformation(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.userInfo), Context.MODE_PRIVATE);
        String userInformationString = sharedPreferences.getString(context.getString(R.string.userInfo), null);
        JSONObject userInformation = null;
        try {
            userInformation = new JSONObject(userInformationString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return userInformation;
    }
//    //Only used once the first time the game starts up
//    //If a file is already present it gives you the present file.
//    public File getTripsSaveFile(){
//        File file = new File(context.getFilesDir(), context.getString(R.string.tripsStatisticsData));
//        if(!file.exists()){
//            savedData = file;
//            Log.e("FileIO", "TripStatisticsDataCreated");
//        }else{
//            Log.e("FileIO", "TripsStatisticsDataExists");
//        }
//        return savedData;
//    }

    public void removeSaveFile(){

    }

}
