package com.mobile.countme.implementation.controllers;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.countme.framework.AppMenu;
import com.mobile.countme.R;
import com.mobile.countme.framework.DecimalDigitsInputFilter;
import com.mobile.countme.framework.SlidingTabLayout;
import com.mobile.countme.framework.IntroductionViewPagerAdapter;

/**
 * Created by Kristian on 11/09/2015.
 */
public class IntroductionMenu extends AppMenu {

    Toolbar toolbar;
    ViewPager pager;
    IntroductionViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"1","2","3","4"};
    int Numboftabs =4;


    @Override
    public void onCreate(Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.introduction_activity);


        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new IntroductionViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setIntroductionMenu(this);
        tabs.setIntroductionMenuInitialized(true);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToMainApp(View view) {
        //TODO add a call to updateUser here when it works
        getMainController().saveUserInformationToStorage();
        Log.e("Test", getMainController().getUserModel().getGender());
        goTo(MainMenu.class);
    }


    /**
     * Initializes the user information tab
     */
    public void initUserInformation(){
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
                    Toast.makeText(getApplicationContext(), getString(R.string.birthdate_saved),Toast.LENGTH_SHORT).show();

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
                        actionId == EditorInfo.IME_ACTION_NEXT){
                    Toast.makeText(getApplicationContext(), getString(R.string.birthdate_saved),Toast.LENGTH_SHORT).show();

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
}

