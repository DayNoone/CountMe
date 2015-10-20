package com.mobile.countme.implementation.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Spinner;

import com.mobile.countme.R;
import com.mobile.countme.custom_views.CustomTextView;
import com.mobile.countme.framework.AppMenu;
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
        CustomTextView calories_display = (CustomTextView) findViewById(R.id.calories_burned);
        CustomTextView avgSpeed = (CustomTextView) findViewById(R.id.avgSpeed_result);
        CustomTextView time_used = (CustomTextView) findViewById(R.id.time_used);
        co2_saved.setText(getMainController().getTripModel().getCo2_saved() + " g");
        Double transformedDistance = new BigDecimal(getMainController().getTripModel().getDistance()).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        distance.setText(transformedDistance + " km");
        calories_display.setText(getMainController().getTripModel().getKcal() + " kcal");
        Double transformedAvgSpeed = new BigDecimal(getMainController().getTripModel().getAvg_speed()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        avgSpeed.setText(transformedAvgSpeed + " km/t");
        time_used.setText(getMainController().getTimeInFormat(-1));
        getMainController().setTripInitialized(false);
        initSpinner();
    }

    public void goToMainMenu(View view){
        getMainController().resetErrors();
        goTo(MainMenu.class);
    }

    private void initSpinner(){
        final Spinner dropdown = (Spinner)findViewById(R.id.spinnerErrors);
        ArrayList<String> items = new ArrayList<>();
        items.add("Velg feilmelding");
        for(ErrorModel error : getMainController().getTripErrors().values()){
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
                if(getMainController().getTripErrors().containsKey(item)) {
                    getMainController().getTripErrors().get(item).setThisError();
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
