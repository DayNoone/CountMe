package com.mobile.countme.implementation.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;

import com.mobile.countme.R;
import com.mobile.countme.framework.AppMenu;
import com.mobile.countme.implementation.AndroidFileIO;
import com.mobile.countme.implementation.models.EnvironmentModel;
import com.mobile.countme.implementation.models.ErrorModel;
import com.mobile.countme.implementation.models.TripModel;
import com.mobile.countme.implementation.models.StatisticsModel;
import com.mobile.countme.implementation.models.UserModel;
import com.mobile.countme.implementation.views.BikingActive;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.google.android.gms.internal.zzhu.runOnUiThread;

/**
 * Created by Robin on 27.09.2015.
 * This is the main controller in the application.
 */
public class User {

    private AndroidFileIO fileIO;
    private AppMenu context;
    private MainMenu mainMenu;

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M-yyyy");
    private Calendar calendar = new GregorianCalendar();

    private boolean start_using_tracker;
    private boolean tripInitialized;
    //Used in the result menu
    private boolean errorClicked;
    //Current time
    private long time;

    //Errors reported during trip
    private Map<String, ErrorModel> tripErrors;

    //Timer during trip with a Timertask that will update the bikingactive view
    private Timer timer;
    private TimerTask timerTask;

    private BikingActive bikingActive;
    private int errorCount = 1;

    /**
     * The models of the MVC structure.
     */
    private EnvironmentModel environmentModel;
    private StatisticsModel statisticsModel;
    private TripModel tripModel;
    private ErrorModel errorModel;
    private UserModel userModel;

    public User (AndroidFileIO io, AppMenu context) {
        this.fileIO = io;
        this.context = context;

        //Instantiate the models and load the internal statistics
        environmentModel = new EnvironmentModel();
        statisticsModel = new StatisticsModel();
        tripModel = new TripModel();
        userModel = new UserModel();

        tripErrors = new HashMap<>();


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
        dummyObjectLastWeek.put("distance", 2.255512321);
        dummyObjectLastWeek.put("avgSpeed",1.32);
        dummyObjectLastMonth.put("TimeStamp", "08-09-2015");
        dummyObjectLastMonth.put("co2Saved", 1);
        dummyObjectLastMonth.put("distance",1.2314123123123);
        dummyObjectLastMonth.put("avgSpeed", 1.54);
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
            int numOfTrips = 0;
            for (int i = allDaysTrips.length() - 1; i >= 0; i--) {

                JSONObject dayTrips = allDaysTrips.getJSONObject(i);

                Date date = simpleDateFormat.parse((dayTrips.getString("TimeStamp")));
                if (date.after(calendar.getTime())) {
                    numOfTrips++;
                    lastPeriodTrips.put("co2Saved", Integer.toString(Integer.parseInt(lastPeriodTrips.getString("co2Saved")) + Integer.parseInt(dayTrips.getString("co2Saved"))));
                    lastPeriodTrips.put("distance", Double.toString(Double.parseDouble(lastPeriodTrips.getString("distance")) + Double.parseDouble(dayTrips.getString("distance"))));
                    lastPeriodTrips.put("avgSpeed", Double.toString(Double.parseDouble(lastPeriodTrips.getString("avgSpeed")) + Double.parseDouble(dayTrips.getString("avgSpeed"))));
                }else {
                    break;
                }
            }
            if(numOfTrips > 0) {
                Log.e("User", "numoftrips: " + numOfTrips + "avgSpeed: " + lastPeriodTrips.getString("avgSpeed"));
                lastPeriodTrips.put("avgSpeed", Double.toString(Double.parseDouble(lastPeriodTrips.getString("avgSpeed")) / numOfTrips));
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
                statisticsModel.setDistance(Double.parseDouble(todaysTrips.getString("distance")));
                statisticsModel.setAvg_speed(Double.parseDouble(todaysTrips.getString("avgSpeed")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Loads the user information from phones internal storage
     */
    public void loadUserInformation(){
        JSONObject userInformation = fileIO.readUserInformation();
        Log.w("User", "loadTripsStatistics: " + userInformation);
        try {
            userModel.setGender(userInformation.getString("Gender"));
            userModel.setBirthYear(Integer.parseInt(userInformation.getString("BirthDate")));
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

    //Saves the user information to internal storage
    public void saveUserInformationToStorage(){
        JSONObject userInfo = new JSONObject();
        try {
            userInfo.put("BirthDate",userModel.getBirthYear());
            userInfo.put("Gender", userModel.getGender());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        fileIO.writeUserInformationSaveFile(userInfo);
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

    public void createUSerInformation(){
        JSONObject userInformation = new JSONObject();
        try {
            userInformation.put("BirthDate", 0);
            userInformation.put("Gender", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        fileIO.writeUserInformationSaveFile(userInformation);
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

    public ErrorModel getErrorModel() {
        return errorModel;
    }

    public TripModel getTripModel() {
        return tripModel;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public Map<String,ErrorModel> getTripErrors() {
        return tripErrors;
    }

    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public void setBikingActive(BikingActive bikingActive) {
        this.bikingActive = bikingActive;
    }

    public AppMenu getContext() {
        return context;
    }

    public void addStatistics(double distance){
        statisticsModel.addDistance(distance);
        tripModel.setDistance(distance);
        double avgSpeed = distance/getTimeUsedInSeconds();
        statisticsModel.calc_new_avgSpeed(avgSpeed);
        tripModel.setAvg_speed(avgSpeed);
        int co2 = environmentModel.addCo2_savedTrip(distance);
        statisticsModel.addCo2_saved(co2);
        tripModel.setCo2_saved(co2);
    }


    public boolean isTripInitialized() {
        return tripInitialized;
    }

    public void setTripInitialized(boolean tripInitialized) {
        this.tripInitialized = tripInitialized;
    }

    public void addDescription(String description){
        errorModel.setDescprition(description);
    }

    public void addPhoto(Bitmap photo){
        errorModel.setPhotoTaken(photo);
    }

    public void addError(ErrorModel errorModel){
        tripErrors.put(errorModel.toString(), errorModel);
    }

    public void setErrorModel(ErrorModel error){
        errorModel = error;
    }

    public void resetErrors(){
        tripErrors = new HashMap<>();
        errorCount = 1;
    }

    public void setErrorClicked(boolean errorClicked) {
        this.errorClicked = errorClicked;
    }

    public boolean isErrorClicked() {
        return errorClicked;
    }

    public void setTime() {
        this.time = System.currentTimeMillis();
    }

    public String getTimeInFormat(Integer time_used) {
        String seconds = "";
        String minutes = "";
        String hours = "";
        long difference = System.currentTimeMillis() - time;
        Integer numSeconds = (int) (difference/1000);
        if(numSeconds > 5){
            start_using_tracker = true;
        }
        if(time_used > 0){
            numSeconds = time_used;
        }
        Integer numMinutes = numSeconds/60;
        Integer numHours = numMinutes/60;
        numSeconds = numSeconds - numMinutes*60;
        numMinutes = numMinutes - numHours*60;
        seconds = numSeconds.toString();
        minutes = numMinutes.toString();
        hours = numHours.toString();
        if(numSeconds < 10){
            seconds = "0" + seconds;
        }
        if(numMinutes < 10){
            minutes = "0" + minutes;
        }
        if(numHours < 10){
            hours = "0" + hours;
        }
        return "" + hours + ":"  + minutes + ":"+ seconds;
    }

    public double getTimeUsedInSeconds(){
        long difference = System.currentTimeMillis() - time;
        return (double)(difference/1000);
    }

    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 1000, 1000); //
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(bikingActive != null) {
                            bikingActive.updateView(getTimeInFormat(-1), start_using_tracker);
                        }
                    }
                });

            }
        };
    }

    public void setStart_using_tracker(boolean start_using_tracker) {
        this.start_using_tracker = start_using_tracker;
    }

    public int getErrorCount() {
        return errorCount++;
    }
}
