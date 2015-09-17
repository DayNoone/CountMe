package com.mobile.countme.menu;

import android.os.Bundle;
import android.view.View;

import com.mobile.countme.R;
import com.mobile.countme.framework.AppMenu;

/**
 * Created by Kristian on 16/09/2015.
 */
public class BikingActive extends AppMenu {

    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.biking_active);
    }

    public void stopBiking(View view) {
        goTo(MainPages.class);
    }

}
