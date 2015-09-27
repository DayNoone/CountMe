package com.mobile.countme.implementation.menus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobile.countme.R;

/**
 * Created by Kristian on 11/09/2015.
 */
public class EnvironmentMenu extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.environment_activity,container,false);
        return v;
    }

    /**
     * Displays the environmental gain.
     * @param co2_savedToday
     */
    public void setEnvironmentGain(int co2_savedToday){
        TextView environmentView = (TextView) getView().findViewById(R.id.co2_display);
        environmentView.setText(Integer.toString(co2_savedToday));
    }
}
