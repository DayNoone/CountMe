package com.mobile.countme.implementation.menus;

import android.os.Bundle;
import android.view.View;

import com.mobile.countme.R;
import com.mobile.countme.framework.AppMenu;
import com.mobile.countme.implementation.controllers.MainPages;

/**
 * Created by Kristian on 16/09/2015.
 */
public class ErrorMenu extends AppMenu {

    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.error_activity);
    }

    public void sendReport(View view){
        if(getUser().isTripInitialized()){
            goTo(BikingActive.class);
        }else {
            goTo(ResultMenu.class);
        }
        goTo(MainPages.class);
    }

    public void startCamera(View view){
        goTo(CameraMenu.class);
    }

    public void createDescription(){
        //TODO: Pop up to add description.
//        getUser().addDescription();
    }

}
