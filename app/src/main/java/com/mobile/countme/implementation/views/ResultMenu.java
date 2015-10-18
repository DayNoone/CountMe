package com.mobile.countme.implementation.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.mobile.countme.R;
import com.mobile.countme.custom_views.CustomTextView;
import com.mobile.countme.framework.AppMenu;
import com.mobile.countme.framework.PopUpMenuEventHandle;
import com.mobile.countme.implementation.controllers.MainMenu;
import com.mobile.countme.implementation.models.ErrorModel;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Kristian on 16/09/2015.
 */
public class ResultMenu extends AppMenu {

    private PopupMenu popupMenu;
    private PopupWindow mDropdown = null;
    LayoutInflater mInflater;
    Button pop;

    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.result_activity);
        CustomTextView co2_saved = (CustomTextView) findViewById(R.id.co2_saved_result);
        CustomTextView distance = (CustomTextView) findViewById(R.id.distance_result);
        CustomTextView avgSpeed = (CustomTextView) findViewById(R.id.avgSpeed_result);
        CustomTextView time_used = (CustomTextView) findViewById(R.id.time_used);
        co2_saved.setText(getUser().getTripModel().getCo2_saved() + " g");
        Double transformedDistance = new BigDecimal(getUser().getTripModel().getDistance()).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        distance.setText(transformedDistance + " km");
        Double transformedAvgSpeed = new BigDecimal(getUser().getTripModel().getAvg_speed()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        avgSpeed.setText(transformedAvgSpeed + " m/s");
        time_used.setText(getUser().getTimeInFormat(-1));
        getUser().setTripInitialized(false);
        initSpinner();
    }

    public void goToMainMenu(View view){
        getUser().resetErrors();
        goTo(MainMenu.class);
    }

    private void initSpinner(){
        final Spinner dropdown = (Spinner)findViewById(R.id.spinnerErrors);
        ArrayList<String> items = new ArrayList<>();
        items.add("Velg feilmelding");
        for(ErrorModel error : getUser().getTripErrors().values()){
            items.add(error.getCoordinates());
        }
        dropdown.setPrompt("Feilmeldinger");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.error_dropdownlist, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                String item = dropdown.getSelectedItem().toString();
                if(getUser().getTripErrors().containsKey(item)) {
                    getUser().getTripErrors().get(item).setThisError();
                    goTo(ErrorMenu.class);
                }
                Log.e("Selected item : ", item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
