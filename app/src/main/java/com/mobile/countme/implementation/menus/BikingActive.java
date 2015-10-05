package com.mobile.countme.implementation.menus;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.mobile.countme.R;
import com.mobile.countme.framework.AppMenu;
import com.mobile.countme.framework.MapsActivity;
import com.mobile.countme.implementation.controllers.MainPages;
import com.mobile.countme.implementation.models.ErrorModel;

import java.util.ArrayList;

/**
 * Created by Kristian on 16/09/2015.
 */
public class BikingActive extends AppMenu {

    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.biking_active);
        getUser().setTripInitialized(true);
    }

    public void stopBiking(View view) {
        new AlertDialog.Builder(this)
                .setMessage(R.string.stop_biking)
                .setNegativeButton(R.string.no, null)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                         // TODO: Testing code - Remove.
                        getUser().addTripCo2(1232);
                        getUser().addTripDistance(32.412312);
                        getUser().addTripAvgSpeed(2.231231);
                        goTo(ResultMenu.class);
                    }
                }).create().show();
    }

    public void goToMaps(View view) {
        goTo(MapsActivity.class);
    }

    public void sendError(View view){
        new AlertDialog.Builder(this)
                .setMessage(R.string.report_error)
                .setNegativeButton(R.string.later, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Her må det sendes inn koordinater eller noe slikt, sånn at brukeren kan identifisere problemet etter turen, hvis han/hun vil legge til beskrivelse i ettertid.
//                        getUser().addErrorToTrip();
                    }
                })
                .setPositiveButton(R.string.now, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        //Add description and/or take picture.
                        goTo(ErrorMenu.class);

                    }
                }).create().show();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.stop_biking)
                .setNegativeButton(R.string.no, null)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO: Testing code - Remove.
                        getUser().addTripCo2(1232);
                        getUser().addTripDistance(32.412312);
                        getUser().addTripAvgSpeed(2.231231);
                        goTo(ResultMenu.class);
                    }
                }).create().show();
    }

}
