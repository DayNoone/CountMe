package com.mobile.countme.implementation.menus;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.mobile.countme.R;
import com.mobile.countme.custom_views.CustomTextView;
import com.mobile.countme.framework.AppMenu;
import com.mobile.countme.framework.PopUpMenuEventHandle;
import com.mobile.countme.implementation.controllers.MainPages;
import com.mobile.countme.implementation.models.ErrorModel;

import java.math.BigDecimal;

/**
 * Created by Kristian on 16/09/2015.
 */
public class ResultMenu extends AppMenu {

    private int item_selection=0;
    private PopupMenu popupMenu;

    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.result_activity);
        CustomTextView co2_saved = (CustomTextView) findViewById(R.id.co2_saved_result);
        CustomTextView distance = (CustomTextView) findViewById(R.id.distance_result);
        CustomTextView avgSpeed = (CustomTextView) findViewById(R.id.avgSpeed_result);
        co2_saved.setText(getUser().getSingleTripModel().getCo2_saved() + " g");
        Double transformedDistance = new BigDecimal(getUser().getSingleTripModel().getDistance()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        distance.setText(transformedDistance + " km");
        Double transformedAvgSpeed = new BigDecimal(getUser().getSingleTripModel().getAvg_speed()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        avgSpeed.setText(transformedAvgSpeed + " m/s");
        getUser().setTripInitialized(false);
    }

    public void goToMainScreen(View view){
        goTo(MainPages.class);
    }

    public void showErrorList(View view){
        popupMenu=new PopupMenu(this,view);
        MenuInflater menuInflater=popupMenu.getMenuInflater();
        PopUpMenuEventHandle popUpMenuEventHandle=new PopUpMenuEventHandle(getApplicationContext());
        popupMenu.setOnMenuItemClickListener(popUpMenuEventHandle);
        menuInflater.inflate(R.menu.error_popuplist, popupMenu.getMenu());
        popupMenu.show();
        popupMenu.getMenu().add("yoooo");
    }


    public void addErrorToPopUp(ErrorModel errorModel){
        //TODO: Add error.
//        popupMenu.getMenu().add(errorModel);
    }
}
