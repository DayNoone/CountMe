package com.mobile.countme.implementation.menus;

import android.os.Bundle;
import android.view.View;

import com.mobile.countme.R;
import com.mobile.countme.framework.AppMenu;
import com.mobile.countme.implementation.controllers.MainPages;

/**
 * Created by Kristian on 16/09/2015.
 */
public class CameraMenu extends AppMenu {

    //TODO: I think we should use - onCreateView so that we can access the fragments.
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.camera_activity);
    }


}
