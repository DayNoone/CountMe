package com.mobile.countme.implementation.menus;

import android.os.Bundle;
import com.mobile.countme.framework.AppMenu;
import com.mobile.countme.implementation.AndroidFileIO;
import com.mobile.countme.implementation.User;
import com.mobile.countme.storage_and_memory.Assets;

/**
 * Created by Kristian on 11/09/2015.
 */
public class LoadingScreen extends AppMenu {

    @Override
    public void onCreate(Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);

        //LOAD SHIT
        //Sets all the static classes for the game
        setAppAssets(new Assets(this));
        setFileIO(new AndroidFileIO(this));
        setUser(new User(getFileIO(), this));


        //AFTER DONE LOADING
        goToNoAnimation(InformationMenu.class);
    }

}
