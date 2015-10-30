package com.mobile.countme.implementation.controllers;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.countme.R;
import com.mobile.countme.framework.AppMenu;
import com.mobile.countme.framework.DecimalDigitsInputFilter;
import com.mobile.countme.framework.MainViewPagerAdapter;
import com.mobile.countme.framework.SlidingTabLayout;
import com.mobile.countme.implementation.models.ErrorModel;
import com.mobile.countme.implementation.views.BikingActive;
import com.mobile.countme.implementation.views.ErrorMenu;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;


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
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        actionId == EditorInfo.IME_ACTION_NEXT){
                    Toast.makeText(getApplicationContext(), getString(R.string.birthdate_saved), Toast.LENGTH_SHORT).show();

                    return false;

                }
                return false;
            }

        });
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
                    int oldBirthYear = getMainController().getUserModel().getBirthYear();
                    if(editable.toString().isEmpty()){
                        getMainController().getUserModel().setBirthYear(0);
                        if(oldBirthYear != 0){
                            getMainController().saveUserInformationToStorage();
                        }
                    } else {
                        int newBirthYear = Integer.parseInt(editable.toString());
                        getMainController().getUserModel().setBirthYear(newBirthYear);
                        if(oldBirthYear != newBirthYear){
                            getMainController().saveUserInformationToStorage();
                        }
                    }
                }
            }
        });

        final EditText editTextWeight = (EditText) findViewById(R.id.editTextWeight);
        if(editTextWeight == null) return;
        DecimalDigitsInputFilter decimalDigitsInputFilter = new DecimalDigitsInputFilter();
        decimalDigitsInputFilter.setDigits(1);
        editTextWeight.setFilters(new InputFilter[] {decimalDigitsInputFilter});
        final Float weight = getMainController().getUserModel().getWeight();
        if(weight != null && weight != 0) {
            editTextWeight.setText(weight + "");
        }
        editTextWeight.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        actionId == EditorInfo.IME_ACTION_NEXT) {
                    Toast.makeText(getApplicationContext(), getString(R.string.weight_saved), Toast.LENGTH_SHORT).show();


                    return false;

                }
                return false;
            }

        });
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
                float oldWeight = getMainController().getUserModel().getWeight();
                if(editable.toString().isEmpty()){
                    getMainController().getUserModel().setWeight((float)0.0);
                    if(oldWeight != 0){
                        getMainController().saveUserInformationToStorage();
                    }
                }else {
                    float newWeight = Float.parseFloat(editable.toString());
                    getMainController().getUserModel().setWeight(newWeight);
                    if(oldWeight != newWeight){
                        getMainController().saveUserInformationToStorage();
                    }
                }
            }
        });

        final RadioGroup rg = (RadioGroup) findViewById(R.id.genderGrp);
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
                        if(getMainController().getUserModel().getGender().equals("male")){
                            getMainController().getUserModel().setGender("");
                        }else {
                            getMainController().getUserModel().setGender("male");
                        }
                        getMainController().saveUserInformationToStorage();
                        break;
                    case R.id.female:
                        if(getMainController().getUserModel().getGender().equals("female")){
                            getMainController().getUserModel().setGender("");
                        }else {
                            getMainController().getUserModel().setGender("female");
                        }
                        getMainController().saveUserInformationToStorage();
                        break;
                }
            }
        });
    }

    /**
     * Error reporting
     *
     * @param view
     */
    public void sendErrorIdle(View view) {
        final ErrorModel newErrorModel = new ErrorModel(getMainController());
        newErrorModel.setName("Feilmelding " + getMainController().getErrorCount());
        newErrorModel.setLatitude(getMainController().getTracker().getLatitude().toString());
        newErrorModel.setLongitude(getMainController().getTracker().getLongitude().toString());
        newErrorModel.setTimeStamp(getMainController().getTracker().getLocation() != null ? System.currentTimeMillis() : -1);
        newErrorModel.setCreatedInIdle(true);
        new AlertDialog.Builder(this)
                .setMessage(R.string.report_errorIdle)
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Map<String,ErrorModel> errorSend = new TreeMap<String, ErrorModel>();
                        errorSend.put("Feilmelding1",newErrorModel);
                        HTTPSender.sendErrors(errorSend);
                        Toast.makeText(getApplicationContext(), getString(R.string.error_saved), Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        //Add description and/or take picture.
                        newErrorModel.setEditedWhenReported(true);
                        getMainController().setErrorModel(newErrorModel);
                        goTo(ErrorMenu.class);

                    }
                }).create().show();
    }



}
