package com.mobile.countme.implementation.menus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.countme.R;
import com.mobile.countme.custom_views.CustomTextView;

/**
 * Created by Kristian on 11/09/2015.
 */
public class StatisticsMenu extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.statistics_activity,container,false);
        return v;
    }

    public void setTripsStatistics(int co2_saved, int distance, int avg_speed){
        if(getView() == null) return;
        CustomTextView co2_display = (CustomTextView) getView().findViewById(R.id.co2_saved_statistics);
        CustomTextView carDistance = (CustomTextView) getView().findViewById(R.id.distance_statistics);
        CustomTextView avgSpeed = (CustomTextView) getView().findViewById(R.id.avgSpeed_statistics);
        co2_display.setText(Integer.toString(co2_saved) + " g");
        carDistance.setText(Integer.toString(distance) + " km");
        avgSpeed.setText(Integer.toString(avg_speed) + " m/s");
    }
}
