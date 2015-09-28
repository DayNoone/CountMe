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

    public AndroidFileIO(AppMenu context){
        this.context = context;
    }



    public void writeEnvironmentSaveFile(String string){
        try{
           FileOutputStream outputStream = context.openFileOutput(context.getString(R.string.environmentStatisticsData), Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
            Log.e("FileIO", "FileSaved: " + string);
        } catch (Exception e){
            e.printStackTrace();
            Log.e("FileIO", "FileNotFound!");
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
            Log.e("FileIO", "UnableToReadSaveFile!");
        }
        Log.e("FileIO", "ReadSaveFile:" + temp);
        return temp;
    }

    //Only used once the first time the game starts up
    //If a file is already present it gives you the present file.
    public File getEnvironmentSaveFile(){
        File file = new File(context.getFilesDir(), context.getString(R.string.environmentStatisticsData));
        if(!file.exists()){
            savedData = file;
            Log.e("FileIO", "savedDataCreated");
        }else{
            Log.e("FileIO", "savedDataExists");
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
