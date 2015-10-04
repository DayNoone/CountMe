package com.mobile.countme.implementation.menus;

import android.os.Bundle;
import android.view.View;

import com.mobile.countme.R;
import com.mobile.countme.framework.AppMenu;
import com.mobile.countme.implementation.controllers.MainPages;

/**
 * Created by Kristian on 16/09/2015.
 */
public class ResultMenu extends AppMenu {

    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.resultscreen_activity);
    }

    public void goToMainScreen(View view){
        goTo(MainPages.class);
    }

}
