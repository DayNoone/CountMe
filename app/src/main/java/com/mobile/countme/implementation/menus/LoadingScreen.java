package com.mobile.countme.implementation.menus;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mobile.countme.R;
import com.mobile.countme.framework.AppMenu;
import com.mobile.countme.implementation.AndroidFileIO;
import com.mobile.countme.implementation.controllers.User;
import com.mobile.countme.implementation.controllers.IntroductionMenu;
import com.mobile.countme.implementation.controllers.MainPages;
import com.mobile.countme.storage_and_memory.Assets;

/**
 * Created by Kristian on 11/09/2015.
 */
public class LoadingScreen extends AppMenu {

    @Override
    public void onCreate(Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);

        //TODO: ALPHA CODE, REMOVE AT LAUNCH:
//        This clears the sharedPref for userPrefrences (clear username).
//        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.profile_preferences), Context.MODE_PRIVATE);
//        sharedPref.edit().clear().commit();

        //Load things
        //Sets all the static classes for the game
        setAppAssets(new Assets(this));
        setFileIO(new AndroidFileIO(this));
        setUser(new User(getFileIO(), this));
        Log.e("LoadingScreen", "Load done");

//        // TODO: ALPHA CODE, REMOVE AT LAUNCH:
//        // This code resets all the statistics.
//        getUser().resetEnvironmentalStatistics();

        //After done loading
        new LoadViewTask().execute();
    }

    //To use the AsyncTask, it must be subclassed
    //The parameters <Void, Integer, Void> means the task: <Params, Progress, Result>
    private class LoadViewTask extends AsyncTask<Void, Integer, Void> {


        private TextView txtViewProgress;
        private ProgressBar progressBar;

        @Override
        protected void onPreExecute(){

        }

    //The code to be executed in a background thread.
    @Override
    protected Void doInBackground(Void... params) {
             /* This code creates/saves the user data and loads all the application assets
              * The publish progress is set in 2 parts:
              * "Saved Data", "Saved preferences" and
.             */
        try
        {
            //Get the current thread's token
            synchronized (this)
            {

                getFileIO().getEnvironmentSaveFile(); //IMPORTANT, needs to be done first

                //If the user already "logged" inn
                SharedPreferences sharedPref = getSharedPreferences(getString(R.string.profile_preferences), Context.MODE_PRIVATE);
                boolean isLoggedInn = sharedPref.getBoolean(getString(R.string.isLoggedInn), false);
                if(!isLoggedInn) {
                    Log.e("LoadScreen", "UserData Not present, creating new saveStatistics");
                    getUser().saveEnvironmentStatistics(); //ONLY DONE THE FIRST TIME THE GAME IS CREATED
                }

//                getUser().setUserPref(sharedPref); - Should be here if we are to have options like language selection.

                //Loads the statistics from the phone internal storage
                getUser().loadEnvironmentalStatistics();

                //Connecting client -- This should be here if we have to make our own application from scratch
//                if(!getUser().getUsername().equals("DefaultUser")){
//                    androidClient = new AndroidClient(getUser());
//                    Log.e("Client", "Client init");
//                    getUser().setAndroidClient(androidClient);
//                }

                //Loads all the games graphical assets (Img, Bitmap, ...) -- This should be here.
//                getAppAssets().loadNonGameGraphicalAssets();

                //Loads all the games Audio assets (Sound, Music, ...) -- I dont think we are going to have any audio assets.
//                getGameAssets().loadNoneGameAudioAssets();


            }
        }
        catch (Exception e)
        {
            Log.e("LoadScreen", "LOADING GAME DATA FAILED");
            e.printStackTrace();
        }

        return null;
    }

    //after executing the code in the thread
    @Override
    protected void onPostExecute(Void result)
    {

        // Checks if the user is logged inn, if the user is logged inn it skips the LaunchMenu and continues to MainMenuActivity
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.profile_preferences), Context.MODE_PRIVATE);
        boolean isLoggedInn = sharedPref.getBoolean(getString(R.string.isLoggedInn), false);
        Log.e("LoadingScreen", "isloggedin: " + isLoggedInn);
        if(isLoggedInn) {
            goTo(MainPages.class);
        }else{
            //Initialize the next Activity if not logged inn and set the logged inn boolean to true, so that the next time the user starts the application he/she will not go to the introduction menu.
            SharedPreferences.Editor edit = sharedPref.edit();
            edit.putBoolean(getString(R.string.isLoggedInn), Boolean.TRUE);
            edit.commit();
            goTo(IntroductionMenu.class);
        }
    }
}

    @Override
    public void onBackPressed(){

    }

}
