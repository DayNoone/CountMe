package com.mobile.countme.implementation.views;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.widget.PopupMenu;

import com.mobile.countme.R;
import com.mobile.countme.custom_views.CustomTextView;
import com.mobile.countme.framework.AppMenu;
import com.mobile.countme.framework.PopUpMenuEventHandle;
import com.mobile.countme.implementation.controllers.MainMenu;
import com.mobile.countme.implementation.models.ErrorModel;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Kristian on 16/09/2015.
 */
public class ResultMenu extends AppMenu {

    private PopupMenu popupMenu;

    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.result_activity);
        CustomTextView co2_saved = (CustomTextView) findViewById(R.id.co2_saved_result);
        CustomTextView distance = (CustomTextView) findViewById(R.id.distance_result);
        CustomTextView avgSpeed = (CustomTextView) findViewById(R.id.avgSpeed_result);
        CustomTextView time_used = (CustomTextView) findViewById(R.id.time_used);
        co2_saved.setText(getUser().getTripModel().getCo2_saved() + " g");
        Double transformedDistance = new BigDecimal(getUser().getTripModel().getDistance()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        distance.setText(transformedDistance + " km");
        Double transformedAvgSpeed = new BigDecimal(getUser().getTripModel().getAvg_speed()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        avgSpeed.setText(transformedAvgSpeed + " m/s");
        time_used.setText(getUser().getTimeDifference());
        getUser().setTripInitialized(false);

    }

    public void goToMainMenu(View view){
        goTo(MainMenu.class);
    }

    public void showErrorList(View view){
        popupMenu=new PopupMenu(this,view);
        MenuInflater menuInflater=popupMenu.getMenuInflater();
        PopUpMenuEventHandle popUpMenuEventHandle=new PopUpMenuEventHandle(getApplicationContext());
        popupMenu.setOnMenuItemClickListener(popUpMenuEventHandle);
        menuInflater.inflate(R.menu.error_popuplist, popupMenu.getMenu());
        popupMenu.show();
        for(ErrorModel error : getUser().getTripErrors().values()){
            popupMenu.getMenu().add(error.toString());
        }
        new Thread() {
            public void run() {
                try

                {
                    Log.e("ResultMenu","erroclicked: " + getUser().isErrorClicked());
                    while(true) {
                        if (getUser().isErrorClicked()) {
                            Log.e("ResultMenu","erroclickedyes: " + getUser().isErrorClicked());
                            getUser().setErrorClicked(false);
                            goTo(ErrorMenu.class);
                            break;
                        }
                    }
                } catch (
                        Exception ex
                        )

                {
                    Log.e("Client", "Something went wrong - couldn't connect");
                    ex.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void onBackPressed() {

    }
}
