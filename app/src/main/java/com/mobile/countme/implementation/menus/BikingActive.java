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

/**
 * Created by Kristian on 16/09/2015.
 */
public class BikingActive extends AppMenu {

    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.biking_active);
    }

    public void stopBiking(View view) {
        new AlertDialog.Builder(this)
                .setMessage(R.string.stop_biking)
                .setNegativeButton(R.string.no, null)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        getUser().getEnvironmentModel().addCo2_savedTrip(123123);
                        goTo(MainPages.class);
                    }
                }).create().show();
    }

    public void goToMaps(View view) {
        goTo(MapsActivity.class);
    }

}
