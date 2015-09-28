package com.mobile.countme.implementation.controllers;

import android.util.Log;

import com.mobile.countme.framework.AppMenu;
import com.mobile.countme.framework.MainViewPagerAdapter;
import com.mobile.countme.implementation.AndroidFileIO;
import com.mobile.countme.implementation.controllers.MainPages;
import com.mobile.countme.implementation.models.EnvironmentModel;

/**
 * Created by Robin on 27.09.2015.
 * This is the main controller in the application.
 */
public class User {

    private AndroidFileIO fileIO;
    private AppMenu context;
    private MainPages mainPages;

    /**
     * The models of the MVC structure.
     */
    private EnvironmentModel environmentModel;

    private int[] places = new int[]{1 , 10, 100, 1000, 10000, 100000, 1000000};

    public User (AndroidFileIO io, AppMenu context) {
        this.fileIO = io;
        this.context = context;

        //Instantiate the models and load the internal statistics
        environmentModel = new EnvironmentModel();
        loadEnvironmentalStatistics();
    }

    /**
     * Loads the environmental statistics of the user from phones internal storage
     */
    public void loadEnvironmentalStatistics(){
        String environmentStatistics = fileIO.readEnvironmentSaveFile();
        Log.w("User", "loadEnvironmentalStatistics: " + environmentStatistics);
        Character charAt;
        int positionInList = 0;
        int tempValue = 0;
        String tempString = "";
        //For every char in statistics
        for (int i = 0; i < environmentStatistics.length(); i++){

            charAt = environmentStatistics.charAt(i);
            if(charAt == '@'){
                break;
            }
            //If the charAt pos i is the separation char '#' then
            // add current temp value to the statList and move on to the next stat.
            if(charAt == '#'){
                for (int l=0; l < tempString.length(); l++) {
                    tempValue += Character.getNumericValue(tempString.charAt(l)) * places[(tempString.length() - 1) - l];
                }

                environmentModel.setStat(positionInList, tempValue);
                positionInList += 1;
                tempValue = 0;
                tempString = "";
                continue;
            }else{
                tempString += charAt;
            }
        }

    }

    //Saves all game statistics to internal storage
    //Current solution puts all statistics as a long string with the @ as an end char.
    public void saveEnvironmentStatistics(){
        String environmentStatistics = ""+ environmentModel.getCo2_savedToday() +'#'+ environmentModel.getCo2_carDistance() + '@';
        fileIO.writeEnvironmentSaveFile(environmentStatistics);
    }

    public EnvironmentModel getEnvironmentModel() {
        return environmentModel;
    }

    public void setMainPages(MainPages mainPages) {
        this.mainPages = mainPages;
    }

    public MainPages getMainPages() {
        return mainPages;
    }

    public AndroidFileIO getFileIO() {
        return fileIO;
    }

    public AppMenu getContext() {
        return context;
    }
}
