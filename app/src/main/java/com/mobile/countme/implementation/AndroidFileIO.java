/*
 * Copyright (c) 2015 Anders Lunde, Robin Sjøvoll, Kristian Huse. All rights reserved.
 */

package com.mobile.countme.implementation;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.mobile.countme.R;
import com.mobile.countme.framework.AppMenu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Anders Lunde on 22.01.2015.
 * Saves the user data as a string, or as shared pref data
 * FileIO is not used widely because server saves user data.
 */
public class AndroidFileIO {


    private static File savedData;
    private AppMenu context;

    private String old_environmentalStats;
    private String old_tripsStats;

    public AndroidFileIO(AppMenu context){
        this.context = context;
    }



    public void writeEnvironmentSaveFile(String string){
        try{
            if(!string.equals(old_environmentalStats)) {
                old_environmentalStats = string;
                FileOutputStream outputStream = context.openFileOutput(context.getString(R.string.environmentStatisticsData), Context.MODE_PRIVATE);
                outputStream.write(string.getBytes());
                outputStream.close();
                Log.e("FileIO", "EnvironmentalFileSaved: " + string);
            }
        } catch (Exception e){
            e.printStackTrace();
            Log.e("FileIO", "EnvironmentalFileNotFound!");
        }
    }

    public String readEnvironmentSaveFile(){
        String temp="";
        try{
            FileInputStream inputStream = context.openFileInput(context.getString(R.string.environmentStatisticsData));
            int c;
            while ( (c= inputStream.read()) != -1){
                temp = temp + Character.toString((char) c);
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("FileIO", "UnableToReadEnvironmentalFile!");
        }
        Log.e("FileIO", "ReadEnvironmentalFile:" + temp);
        return temp;
    }

    //Only used once the first time the game starts up
    //If a file is already present it gives you the present file.
    public File getEnvironmentSaveFile(){
        File file = new File(context.getFilesDir(), context.getString(R.string.environmentStatisticsData));
        if(!file.exists()){
            savedData = file;
            Log.e("FileIO", "EnvironmentalDataCreated");
        }else{
            Log.e("FileIO", "EnvironmentalDataExists");
        }
        return savedData;
    }

    public void writeTripsSaveFile(String string){
        try{
            if(!string.equals(old_tripsStats)) {
                old_tripsStats = string;
                FileOutputStream outputStream = context.openFileOutput(context.getString(R.string.tripsStatisticsData), Context.MODE_PRIVATE);
                outputStream.write(string.getBytes());
                outputStream.close();
                Log.e("FileIO", "TripFileSaved: " + string);
            }
        } catch (Exception e){
            e.printStackTrace();
            Log.e("FileIO", "TripFileNotFound!");
        }
    }

    public String readTripsSaveFile(){
        String temp="";
        try{
            FileInputStream inputStream = context.openFileInput(context.getString(R.string.tripsStatisticsData));
            int c;
            while ( (c= inputStream.read()) != -1){
                temp = temp + Character.toString((char) c);
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("FileIO", "UnableToReadTripsStatisticsFile!");
        }
        Log.e("FileIO", "ReadTripsStatisticsFile:" + temp);
        return temp;
    }

    //Only used once the first time the game starts up
    //If a file is already present it gives you the present file.
    public File getTripsSaveFile(){
        File file = new File(context.getFilesDir(), context.getString(R.string.tripsStatisticsData));
        if(!file.exists()){
            savedData = file;
            Log.e("FileIO", "TripStatisticsDataCreated");
        }else{
            Log.e("FileIO", "TripsStatisticsDataExists");
        }
        return savedData;
    }

    public void removeSaveFile(){

    }

    public SharedPreferences getSharedPref(){
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.profile_preferences), Context.MODE_PRIVATE);
        return sharedPref;
    }


}
