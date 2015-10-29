package com.mobile.countme.implementation.models;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import com.mobile.countme.framework.AppMenu;
import com.mobile.countme.implementation.controllers.HTTPSender;
import com.mobile.countme.implementation.controllers.MainController;

import java.util.ArrayList;

/**
 * Created by Sondre on 02.10.2015.
 */
public class GPSTracker extends Service implements LocationListener {

    private final AppMenu mContext;

    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    private Location location; // location
    private double latitude; // latitude
    private double longitude; // longitude
    private double distance; //distance

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 5 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 1; // 1 second

    // Declaring a Location Manager
    protected LocationManager locationManager;

    private ArrayList<Location> trip;
    private ArrayList<Integer> connectionTypes;
    private ConnectivityManager cm;
    private NetworkInfo activeNetwork;

    public GPSTracker(AppMenu context) {
        this.mContext = context;
        trip = new ArrayList<Location>();
        Location temp = getLocation();
        if (temp != null) {
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            connectionTypes = new ArrayList<Integer>();
            if (cm != null) {
                activeNetwork = cm.getActiveNetworkInfo();
                if (activeNetwork != null) {
                    connectionTypes.add(activeNetwork.getType());
                } else {
                    connectionTypes.add(-1);
                }
            } else {
                connectionTypes.add(-1);
            }
            trip.add(temp);
        }

    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnabled && !isGPSEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("GPS Enabled", "GPS Enabled");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
            }
        }
        catch (IllegalStateException e){
            e.printStackTrace();
        }


        return location;
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     */
    public void stopUsingGPS(UserModel userModel) {
        Log.d("Stopping GPS", "Stopping GPS");
        if (locationManager != null) {
            Log.d("Stopping GPS", "Locationmanager!=null");
            locationManager.removeUpdates(this);
            HTTPSender.sendTrip(trip, connectionTypes, userModel, mContext);
            AppMenu.getMainController().getTripModel().setTrips(trip);
        }
    }

    /**
     * Function to get latitude
     */
    public Double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     */
    public Double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    /**
     * Function to check GPS/wifi enabled
     *
     * @return boolean
     */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     */
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            trip.add(location);
            cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                activeNetwork = cm.getActiveNetworkInfo();
                if (activeNetwork != null) {
                    connectionTypes.add(activeNetwork.getType());
                } else {
                    connectionTypes.add(-1);
                }
            } else {
                connectionTypes.add(-1);
            }
            if (trip.size() > 1) {

                Log.e("GPSTracker", "It works");
                float distanceTo = location.distanceTo(trip.get(trip.size() - 2));
                //Checks if the distance between two points that are added with one second difference are more than X meters.
                if (distanceTo < 20.0f) {
                    distance += location.distanceTo(trip.get(trip.size() - 2));
                }

            }
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    public ArrayList<Location> getTrip() {
        return trip;
    }

    public float getCurrentSpeed() {
        if (trip.size() > 0) {
            return trip.get(trip.size() - 1).getSpeed();
        }
        return 0.0f;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public boolean isCanGetLocation() {
        return canGetLocation;
    }
}
