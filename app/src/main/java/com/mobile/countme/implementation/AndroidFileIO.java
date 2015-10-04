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



//    public void writeEnvironmentSaveFile(String string){
//        try{
//            if(!string.equals(old_environmentalStats)) {
//                old_environmentalStats = string;
//                FileOutputStream outputStream = context.openFileOutput(context.getString(R.string.environmentStatisticsData), Context.MODE_PRIVATE);
//                outputStream.write(string.getBytes());
//                outputStream.close();
//                Log.e("FileIO", "EnvironmentalFileSaved: " + string);
//            }
//        } catch (Exception e){
//            e.printStackTrace();
//            Log.e("FileIO", "EnvironmentalFileNotFound!");
//        }
//    }

//    public String readEnvironmentSaveFile(){
//        String temp="";
//        try{
//            FileInputStream inputStream = context.openFileInput(context.getString(R.string.environmentStatisticsData));
//            int c;
//            while ( (c= inputStream.read()) != -1){
//                temp = temp + Character.toString((char) c);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            Log.e("FileIO", "UnableToReadEnvironmentalFile!");
//        }
//        Log.e("FileIO", "ReadEnvironmentalFile:" + temp);
//        return temp;
//    }

//    //Only used once the first time the game starts up
//    //If a file is already present it gives you the present file.
//    public File getEnvironmentSaveFile(){
//        File file = new File(context.getFilesDir(), context.getString(R.string.environmentStatisticsData));
//        if(!file.exists()){
//            savedData = file;
//            Log.e("FileIO", "EnvironmentalDataCreated");
//        }else{
//            Log.e("FileIO", "EnvironmentalDataExists");
//        }
//        return savedData;
//    }

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
