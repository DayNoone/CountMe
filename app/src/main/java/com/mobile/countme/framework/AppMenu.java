package com.mobile.countme.framework;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Kristian on 11/09/2015.
 */
public abstract class AppMenu extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);
    }


    public void goToNoAnimation(Class javaClass) {
        Intent intent = new Intent(this, javaClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }

    public void goTo(Class javaClass) {
        Intent intent = new Intent(this, javaClass);
        startActivity(intent);
        finish();
    }

}
