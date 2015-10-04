package com.mobile.countme.implementation.menus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.countme.R;
import com.mobile.countme.custom_views.CustomTextView;

import java.math.BigDecimal;

/**
 * Created by Kristian on 11/09/2015.
 */
public class StatisticsMenu extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.statistics_activity,container,false);
        return v;
    }

    /**
     * Updates this view based on the model values sent by the controller.
     * @param co2_saved
     * @param distance
     * @param avg_speed
     */
    public void setTripsStatistics(int co2_saved, double distance, double avg_speed){
        if(getView() == null) return;
        CustomTextView co2_display = (CustomTextView) getView().findViewById(R.id.co2_saved_statistics);
        CustomTextView carDistance = (CustomTextView) getView().findViewById(R.id.distance_statistics);
        CustomTextView avgSpeed = (CustomTextView) getView().findViewById(R.id.avgSpeed_statistics);
        co2_display.setText(Integer.toString(co2_saved) + " g");
        Double transformedDistance = new BigDecimal(distance).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        carDistance.setText(Double.toString(transformedDistance) + " km");
        Double transformedAvgSpeed = new BigDecimal(avg_speed).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        avgSpeed.setText(Double.toString(transformedAvgSpeed) + " m/s");
    }
}
