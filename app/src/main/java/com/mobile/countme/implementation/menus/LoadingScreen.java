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

        //LOAD SHIT
        //Sets all the static classes for the game
        setAppAssets(new Assets(this));
        setFileIO(new AndroidFileIO(this));
        setUser(new User(getFileIO(), this));
        Log.e("LoadingScreen", "Load done");


        //AFTER DONE LOADING
        new LoadViewTask().execute();
    }

    //To use the AsyncTask, it must be subclassed
    //The parameters <Void, Integer, Void> means the task: <Params, Progress, Result>
    private class LoadViewTask extends AsyncTask<Void, Integer, Void> {


        private TextView txtViewProgress;
        private ProgressBar progressBar;

        @Override
        protected void onPreExecute(){

            //Sets the content view to access its views and progress bar
//            setContentView(R.layout.activity_loadscreen);
//
//            txtViewProgress = (TextView)findViewById(R.id.txtViewProgress);
//            progressBar = (ProgressBar) findViewById(R.id.progressBarLoadScreen);
//
//            //Sets the maximum value of the progress bar to 100
//            progressBar.setMax(100);

        }

    //The code to be executed in a background thread.
    @Override
    protected Void doInBackground(Void... params) {
             /* This code creates/saves the user data and loads all the game assets
              * The publish progress is set in 4 parts:
              * "Saved Data And preferences" , "Load Graphical Assets", "Load Audio Assets", "empty"
.             */
        try
        {
            //Get the current thread's token
            synchronized (this)
            {

                getFileIO().getEnvironmentSaveFile(); //IMPORTANT, needs to be done first

                //If the user already logged inn
                SharedPreferences sharedPref = getSharedPreferences(getString(R.string.profile_preferences), Context.MODE_PRIVATE);
                boolean isLoggedInn = sharedPref.getBoolean(getString(R.string.isLoggedInn), false);
                if(!isLoggedInn) {
                    Log.e("LoadScreen", "UserData Not present, creating new saveStatistics");
                    getUser().saveEnvironmentStatistics(); //ONLY DONE THE FIRST TIME THE GAME IS CREATED
                }

//                getUser().setUserPref(sharedPref);
                getUser().loadEnvironmentalStatistics(); //Loads the statistics from the phone internal storage

//                publishProgress(2*25); // Divide the loading bar into the appropriate amount of pieces

                //Connecting client -- This should be here if we have to make our own application from scratch
//                if(!getUser().getUsername().equals("DefaultUser")){
//                    androidClient = new AndroidClient(getUser());
//                    Log.e("Client", "Client init");
//                    getUser().setAndroidClient(androidClient);
//                }

                //Loads all the games graphical assets (Img, Bitmap, ...) -- This should be here.
//                getAppAssets().loadNonGameGraphicalAssets();
//                publishProgress(2*25);

                //Loads all the games Audio assets (Sound, Music, ...)
//                getGameAssets().loadNoneGameAudioAssets();
//                publishProgress(4*25);


            }
        }
        catch (Exception e)
        {
            Log.e("LoadScreen", "LOADING GAME DATA FAILED");
            e.printStackTrace();
        }

        return null;
    }

    //Update the progress
    @Override
    protected void onProgressUpdate(Integer... values)
    {
        //Update the progress at the UI if progress value is smaller than 100
//        if(values[0] <= 100)
//        {
//            txtViewProgress.setText("Progress: " + Integer.toString(values[0]) + "%");
//            progressBar.setProgress(values[0]);
//        }

    }

    //after executing the code in the thread
    @Override
    protected void onPostExecute(Void result)
    {
        // Checks if the user is logged inn, if the user is logged inn it skips the LaunchMenu and continues to MainMenuActivity
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.profile_preferences), Context.MODE_PRIVATE);
        boolean isLoggedInn = sharedPref.getBoolean(getString(R.string.isLoggedInn), false);
        if(isLoggedInn) {
            Log.w("LoadScreen", "HAS USERNAME");
            goTo(MainPages.class);
        }else{
            //initialize the next Activity if not logged inn
            goTo(IntroductionMenu.class);
        }
    }
}

    @Override
    public void onBackPressed(){

    }

}
