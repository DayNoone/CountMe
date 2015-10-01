package com.mobile.countme.implementation.menus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.countme.R;
import com.mobile.countme.custom_views.CustomTextView;

/**
 * Created by Kristian on 11/09/2015.
 */
public class EnvironmentMenu extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.environment_activity,container,false);
        return v;
    }

    public void setEnvironmentGain(int co2_savedToday, int co2_carDistance){
        CustomTextView co2_display = (CustomTextView) getView().findViewById(R.id.co2_display);
        CustomTextView carDistance = (CustomTextView) getView().findViewById(R.id.car_km);
        co2_display.setText(Integer.toString(co2_savedToday) + " g");
        carDistance.setText(Integer.toString(co2_carDistance) + " km med bil");
    }

}
