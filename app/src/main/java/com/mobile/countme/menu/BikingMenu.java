package com.mobile.countme.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.countme.R;
import com.mobile.countme.framework.AppMenu;

/**
 * Created by Kristian on 11/09/2015.
 */
public class BikingMenu extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.biking_idle,container,false);
        return v;
    }

}
