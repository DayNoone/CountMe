package com.mobile.countme.implementation.controllers;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mobile.countme.R;
import com.mobile.countme.framework.AppMenu;
import com.mobile.countme.framework.MainViewPagerAdapter;
import com.mobile.countme.framework.SlidingTabLayout;
import com.mobile.countme.implementation.AndroidFileIO;
import com.mobile.countme.implementation.menus.BikingActive;
import com.mobile.countme.implementation.models.EnvironmentModel;

import java.util.Locale;

/**
 * Created by Kristian on 16/09/2015.
 */
public class MainPages extends AppMenu {

    Toolbar toolbar;
    ViewPager pager;
    MainViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[];
    int Numboftabs =4;

    AndroidFileIO fileIO; // File IO to save and get internally saved statistics.
    private int[] places = new int[]{1 , 10, 100, 1000, 10000, 100000, 1000000};

    /**
     * The models in the MVC architecture.
     */
    EnvironmentModel environmentModel;

    public void onCreate(Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.main_activity);

        Titles= new CharSequence[]{
            getResources().getString(R.string.biking_idle),
                    getResources().getString(R.string.environment_page),
                    getResources().getString(R.string.statistics_page),
                    getResources().getString(R.string.info_page)};

        toolbar = (Toolbar) findViewById(R.id.main_tool_bar);
        setSupportActionBar(toolbar);

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new MainViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.main_pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.main_tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.TabScroller);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

        //Not functional ( view is not made yet)
        //((TextView) findViewById(R.id.start_tur)).setTypeface(Assets.getTypeface(this, Assets.baskerville_old_face_regular));

        //Instantiate the models and load the internal statistics
        environmentModel = new EnvironmentModel(this);
        loadStatistics();

        getUser().setMainPages(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Log.w("onOptionsItemSelected", "Start of method");
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.lang_norwegian) {
            setLocale("no");
            return true;
        }
        else if (id == R.id.lang_english) {
            Log.w("MainPages, itemSelected", "Setting language to english");
            setLocale("en");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToIntroduction(View view){
        goTo(IntroductionMenu.class);
    }

    public void startBiking(View view) {
        goTo(BikingActive.class);
    }

    public void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainPages.class);
        startActivity(refresh);
        finish();
    }

    /**
     * Loads the environmental statistics of the user from phones internal storage
     */
    public void loadStatistics(){
        String environmentStatistics = fileIO.readEnvironmentSaveFile();
        Log.w("User","loadStatistics: " + environmentStatistics);
        Character charAt;
        int positionInList = 0;
        int tempValue = 0;
        String tempString = "";
        //For every char in statistics
        for (int i = 0; i < environmentStatistics.length(); i++){

            charAt = environmentStatistics.charAt(i);
            if(charAt == '@'){
                break;
            }
            //If the charAt pos i is the separation char '#' then
            // add current temp value to the statList and move on to the next stat.
            if(charAt == '#'){
                for (int l=0; l < tempString.length(); l++) {
                    tempValue += Character.getNumericValue(tempString.charAt(l)) * places[(tempString.length() - 1) - l];
                }

                environmentModel.setStat(positionInList, tempValue);
                positionInList += 1;
                tempValue = 0;
                tempString = "";
                continue;
            }else{
                tempString += charAt;
            }
        }

    }

    //Saves all game statistics to internal storage
    //Current solution puts all statistics as a long string with the @ as an end char.
    public void saveEnvironmentStatistics(){
        String environmentStatistics = ""+ environmentModel.getCo2_savedToday() +'#'+ environmentModel.getCo2_carDistance() + '@';
        fileIO.writeEnvironmentSaveFile(environmentStatistics);
    }

    public EnvironmentModel getEnvironmentModel() {
        return environmentModel;
    }

    public void setEnvironmentGain(){
        adapter.getEnvironmentMenu().setEnvironmentGain(environmentModel.getCo2_savedToday());
    }

    //TODO: MOVE THIS CODE TO USER.JAVA
}
