package com.mobile.countme.implementation.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.mobile.countme.R;
import com.mobile.countme.framework.AppMenu;
import com.mobile.countme.implementation.AndroidFileIO;
import com.mobile.countme.implementation.models.EnvironmentModel;
import com.mobile.countme.implementation.models.StatisticsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Robin on 27.09.2015.
 * This is the main controller in the application.
 */
public class User {

    private AndroidFileIO fileIO;
    private AppMenu context;
    private MainPages mainPages;

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M-yyyy");
    private Calendar calendar = new GregorianCalendar();

    /**
     * The models of the MVC structure.
     */
    private EnvironmentModel environmentModel;
    private StatisticsModel statisticsModel;

    // This are the places in numbers. It is used to multiply with the single integers stored in memory to get the correct and complete number.
    private int[] places = new int[]{1 , 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000};

    public User (AndroidFileIO io, AppMenu context) {
        this.fileIO = io;
        this.context = context;

        //Instantiate the models and load the internal statistics
        environmentModel = new EnvironmentModel();
        statisticsModel = new StatisticsModel();


    }

    /**
     * Loads the environmental statistics of the user from phones internal storage
     */
    public void loadEnvironmentalStatistics(){
        JSONArray environmentStatistics = fileIO.readStatisticsSaveFile();
        Log.w("User", "loadEnvironmentalStatistics: " + environmentStatistics);
        try {
            JSONObject todaysTrips = environmentStatistics.getJSONObject(environmentStatistics.length()-1);
            if(todaysTrips.getString("TimeStamp").equals(simpleDateFormat.format(calendar.getTime()))){
                environmentModel.setCo2_savedToday(Integer.parseInt(todaysTrips.getString("co2Saved")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //TODO: Testing code - Remove
    public void addRandomShit() throws JSONException {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.statisticsData), Context.MODE_PRIVATE);
        String tripsString = sharedPreferences.getString(context.getString(R.string.statisticsData), null);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        JSONArray trips = new JSONArray(tripsString);
        JSONObject dummyObjectLastWeek = new JSONObject();
        JSONObject dummyObjectLastMonth = new JSONObject();
        dummyObjectLastWeek.put("TimeStamp","01-10-2015");
        dummyObjectLastWeek.put("co2Saved",1);
        dummyObjectLastWeek.put("distance", 1);
        dummyObjectLastWeek.put("avgSpeed",1);
        dummyObjectLastMonth.put("TimeStamp", "08-09-2015");
        dummyObjectLastMonth.put("co2Saved", 1);
        dummyObjectLastMonth.put("distance",1);
        dummyObjectLastMonth.put("avgSpeed", 1);
        trips.put(dummyObjectLastMonth);
        trips.put(dummyObjectLastWeek);
        prefEditor.putString(context.getString(R.string.statisticsData), trips.toString());
        prefEditor.commit();
    }

    /**
     * Returns a JSONObject that contains the summed values of the specified period.
     * @param numberOfDays
     * @return
     */
    public JSONObject getLastPeriodTrips(int numberOfDays) {
        JSONArray allDaysTrips = fileIO.readStatisticsSaveFile();
        calendar.add(Calendar.DAY_OF_MONTH, -numberOfDays);
        JSONObject lastPeriodTrips = new JSONObject();
        try {
            lastPeriodTrips.put("co2Saved", 0);
            lastPeriodTrips.put("distance", 0);
            lastPeriodTrips.put("avgSpeed", 0);
            for (int i = allDaysTrips.length() - 1; i >= 0; i--) {

                JSONObject dayTrips = allDaysTrips.getJSONObject(i);

                Date date = simpleDateFormat.parse((dayTrips.getString("TimeStamp")));
                if (date.after(calendar.getTime())) {

                    lastPeriodTrips.put("co2Saved", Integer.toString(Integer.parseInt(lastPeriodTrips.getString("co2Saved")) + Integer.parseInt(dayTrips.getString("co2Saved"))));
                    lastPeriodTrips.put("distance", Integer.toString(Integer.parseInt(lastPeriodTrips.getString("distance")) + Integer.parseInt(dayTrips.getString("distance"))));
                    lastPeriodTrips.put("avgSpeed", Integer.toString(Integer.parseInt(lastPeriodTrips.getString("avgSpeed")) + Integer.parseInt(dayTrips.getString("avgSpeed"))));
                }else {
                    break;
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        calendar = Calendar.getInstance();
        return lastPeriodTrips;
    }
    /**
     * Loads the trips statistics of the user from phones internal storage
     */
    public void loadTripsStatistics(){
        JSONArray tripsStatistics = fileIO.readStatisticsSaveFile();
        Log.w("User", "loadTripsStatistics: " + tripsStatistics);
        try {
            JSONObject todaysTrips = tripsStatistics.getJSONObject(tripsStatistics.length()-1);
            if(todaysTrips.getString("TimeStamp").equals(simpleDateFormat.format(calendar.getTime()))){
                statisticsModel.setCo2_saved(Integer.parseInt(todaysTrips.getString("co2Saved")));
                statisticsModel.setDistance(Integer.parseInt(todaysTrips.getString("distance")));
                statisticsModel.setAvg_speed(Integer.parseInt(todaysTrips.getString("avgSpeed")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //Saves all trips statistics to internal storage
    //Current solution puts all statistics as a long string with the @ as an end char.
    public void saveTripsStatistics(){
        JSONObject tripsToday = new JSONObject();
        try {
            tripsToday.put("TimeStamp", simpleDateFormat.format(calendar.getTime()));
            tripsToday.put("co2Saved", statisticsModel.getCo2_saved());
            tripsToday.put("distance", statisticsModel.getDistance());
            tripsToday.put("avgSpeed", statisticsModel.getAvg_speed());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        fileIO.writeStatisticSaveFile(tripsToday);
    }

    public void createTripsStatistics(){
        JSONArray trips = new JSONArray();
            JSONObject trip = new JSONObject();
            try {
                trip.put("TimeStamp", simpleDateFormat.format(calendar.getTime()));
                trip.put("co2Saved", 0);
                trip.put("distance", 0);
                trip.put("avgSpeed", 0);
                trips.put(trip);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        fileIO.writeInitialStatisticsSaveFile(trips);
    }

    /**
     * Resets the trips statistics file. This should be done every day.
     */
    public void resetTripsStatistics(){
        JSONObject trip = new JSONObject();
        try {
            trip.put("TimeStamp", simpleDateFormat.format(calendar.getTime()));
            trip.put("co2Saved", 0);
            trip.put("distance", 0);
            trip.put("avgSpeed", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        statisticsModel.resetStatistics();
        fileIO.writeStatisticSaveFile(trip);
    }

    public EnvironmentModel getEnvironmentModel() {
        return environmentModel;
    }

    public StatisticsModel getStatisticsModel() {
        return statisticsModel;
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
