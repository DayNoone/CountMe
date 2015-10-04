package com.mobile.countme.implementation.menus;

import android.os.Bundle;
import android.view.View;

import com.mobile.countme.R;
import com.mobile.countme.custom_views.CustomTextView;
import com.mobile.countme.framework.AppMenu;
import com.mobile.countme.implementation.controllers.MainPages;

import java.math.BigDecimal;

/**
 * Created by Kristian on 16/09/2015.
 */
public class ResultMenu extends AppMenu {

    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.result_activity);
        CustomTextView co2_saved = (CustomTextView) findViewById(R.id.co2_saved_result);
        CustomTextView distance = (CustomTextView) findViewById(R.id.distance_result);
        CustomTextView avgSpeed = (CustomTextView) findViewById(R.id.avgSpeed_result);
        co2_saved.setText(getUser().getResultModel().getCo2_saved() + " g");
        Double transformedDistance = new BigDecimal(getUser().getResultModel().getDistance()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        distance.setText(transformedDistance + " km");
        Double transformedAvgSpeed = new BigDecimal(getUser().getResultModel().getAvg_speed()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        avgSpeed.setText(transformedAvgSpeed + " m/s");
    }

    public void goToMainScreen(View view){
        goTo(MainPages.class);
    }

}
