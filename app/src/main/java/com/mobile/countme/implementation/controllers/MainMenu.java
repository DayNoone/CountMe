package com.mobile.countme.implementation.controllers;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mobile.countme.R;
import com.mobile.countme.custom_views.CustomTextView;
import com.mobile.countme.framework.AppMenu;
import com.mobile.countme.framework.DecimalDigitsInputFilter;
import com.mobile.countme.framework.MainViewPagerAdapter;
import com.mobile.countme.framework.SlidingTabLayout;
import com.mobile.countme.implementation.views.BikingActive;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;


/**
 * Created by Kristian on 16/09/2015.
 */
public class MainMenu extends AppMenu {

    Toolbar toolbar;
    ViewPager pager;
    MainViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[];
    int numboftabs =4;

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
        adapter =  new MainViewPagerAdapter(getSupportFragmentManager(),Titles, numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.main_pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.main_tabs);
        tabs.setMainMenu(this);
        tabs.setMainPagesInitialized(true);
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

        getMainController().setMainMenu(this);
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
            Log.w("MainMenu, itemSelected", "Setting language to english");
            setLocale("en");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToIntroduction(View view){
        goTo(IntroductionMenu.class);
    }

    public void startBiking(View view) {
        getMainController().resetTracker();
        getMainController().setTime();
        getMainController().startTimer();
        getMainController().setStart_using_tracker(false);
        goTo(BikingActive.class);
    }

    /**
     * Updates the statistics view to show this weeks statistics.
     * @param view
     */
    public void viewOneDayStats(View view) {
        Button button_day = (Button)findViewById(R.id.button3);
        Button button_week = (Button)findViewById(R.id.button4);
        Button button_month = (Button)findViewById(R.id.button5);
        button_day.setBackgroundResource(R.drawable.btn_stroke_statistics);
        button_week.setBackgroundResource(R.drawable.btn_stroke);
        button_month.setBackgroundResource(R.drawable.btn_stroke);
        adapter.getStatisticsTab().setTripsStatistics(getMainController().getStatisticsModel().getCo2_saved(), getMainController().getStatisticsModel().getDistance(), getMainController().getStatisticsModel().getAvg_speed(), getMainController().getStatisticsModel().getKcal());
    }

    /**
     * Updates the statistics view to show this weeks statistics.
     * @param view
     */
    public void viewOneWeekStats(View view) {
        JSONObject lastWeekTrips = getMainController().getLastPeriodTrips(7);
        Button button_day = (Button)findViewById(R.id.button3);
        Button button_week = (Button)findViewById(R.id.button4);
        Button button_month = (Button)findViewById(R.id.button5);
        button_day.setBackgroundResource(R.drawable.btn_stroke);
        button_week.setBackgroundResource(R.drawable.btn_stroke_statistics);
        button_month.setBackgroundResource(R.drawable.btn_stroke);
        try {
            adapter.getStatisticsTab().setTripsStatistics(Integer.parseInt(lastWeekTrips.getString("co2Saved")), Double.parseDouble(lastWeekTrips.getString("distance")), Double.parseDouble(lastWeekTrips.getString("avgSpeed")), Integer.parseInt(lastWeekTrips.getString("calories")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Updates the statistics view to show this month statistics.
     * @param view
     */
    public void viewOneMonthStatistics(View view){
        JSONObject lastMonthTrips = getMainController().getLastPeriodTrips(30);
        Button button_day = (Button)findViewById(R.id.button3);
        Button button_week = (Button)findViewById(R.id.button4);
        Button button_month = (Button)findViewById(R.id.button5);
        button_day.setBackgroundResource(R.drawable.btn_stroke);
        button_week.setBackgroundResource(R.drawable.btn_stroke);
        button_month.setBackgroundResource(R.drawable.btn_stroke_statistics);
        try {
            adapter.getStatisticsTab().setTripsStatistics(Integer.parseInt(lastMonthTrips.getString("co2Saved")), Double.parseDouble(lastMonthTrips.getString("distance")), Double.parseDouble(lastMonthTrips.getString("avgSpeed")), Integer.parseInt(lastMonthTrips.getString("calories")));
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainMenu.class);
        startActivity(refresh);
        finish();
    }


    /**
     * Sets the statistics both in the models and the internal storage based on the newest trip.
     */
    public void setStatistics(){
        adapter.getEnvironmentTab().setEnvironmentGain(getMainController().getEnvironmentModel().getCo2_savedToday(), getMainController().getEnvironmentModel().getCo2_carDistance(), getMainController().getEnvironmentModel().getCo2_busDistance(), getMainController().getEnvironmentModel().getCo2_trainDistance(), getMainController().getEnvironmentModel().getCo2_plainDistance());
        adapter.getStatisticsTab().setTripsStatistics(getMainController().getStatisticsModel().getCo2_saved(), getMainController().getStatisticsModel().getDistance(), getMainController().getStatisticsModel().getAvg_speed(), getMainController().getStatisticsModel().getKcal());
        getMainController().saveTripsStatistics();
    }

    /**
     * Initializes the options tab
     */
    public void initOptionsTab(){
        final EditText editText = (EditText) findViewById(R.id.editText);
        if(editText == null) return;
        final Integer birthYear = getMainController().getUserModel().getBirthYear();
        if(birthYear != null && birthYear != 0) {
            editText.setText(birthYear + "");
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Editable editable = editText.getText();
                if (!editable.toString().contains("Y")) {
                    if(editable.toString().isEmpty()){
                        getMainController().getUserModel().setBirthYear(0);
                    }else {
                        int newBirthYear = Integer.parseInt(editable.toString());
                        getMainController().getUserModel().setBirthYear(newBirthYear);
                    }
                }
            }
        });

        final EditText editTextWeight = (EditText) findViewById(R.id.editTextWeight);
        if(editTextWeight == null) return;
        editTextWeight.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(4,1)});
        final Float weight = getMainController().getUserModel().getWeight();
        if(weight != null && weight != 0) {
            editTextWeight.setText(weight + "");
        }
        editTextWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Editable editable = editTextWeight.getText();
                if(editable.toString().isEmpty()){
                    getMainController().getUserModel().setWeight((float)0.0);
                }else {
                    float newWeight = Float.parseFloat(editable.toString());
                    getMainController().getUserModel().setWeight(newWeight);
                }
            }
        });

        RadioGroup rg = (RadioGroup) findViewById(R.id.genderGrp);
        if(rg == null) return;
        if(getMainController().getUserModel().getGender() != null && getMainController().getUserModel().getGender().equals("male")){
            RadioButton male = (RadioButton) findViewById(R.id.male);
            male.setChecked(true);
        }else if(getMainController().getUserModel().getGender() != null && getMainController().getUserModel().getGender().equals("female")){
            RadioButton female = (RadioButton) findViewById(R.id.female);
            female.setChecked(true);
        }
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.male:
                        getMainController().getUserModel().setGender("male");
                        break;
                    case R.id.female:
                        getMainController().getUserModel().setGender("female");
                        break;
                }
            }
        });
    }



}
