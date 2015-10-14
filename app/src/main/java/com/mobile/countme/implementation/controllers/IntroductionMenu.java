package com.mobile.countme.implementation.controllers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.mobile.countme.framework.AppMenu;
import com.mobile.countme.R;
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
        goTo(MainMenu.class);
    }


    public void insertDate(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Legg til beskrivelse");

// Set an EditText view to get user input
        final EditText input = new EditText(this);
        final Integer birthYear = getUser().getUserModel().getBirthYear();
        Log.e("IntroductionMenu", birthYear + "");
        input.setFilters(new InputFilter[] {new InputFilter.LengthFilter(4)});
        if(birthYear != 0) {
            input.setText(birthYear + "");
        }
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Do something with value!
                Editable editable = input.getText();
                if (editable.toString().length() > 0) {
                    int birthYear = Integer.parseInt(editable.toString());
                    getUser().getUserModel().setBirthYear(birthYear);
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }

    public void initEditText(){
        final EditText editText = (EditText) findViewById(R.id.editText);
        if(editText == null) return;
        final Integer birthYear = getUser().getUserModel().getBirthYear();
        if(birthYear != 0) {
            editText.setText(birthYear + "");
        }
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Editable editable = editText.getText();
                    if (editable.toString().length() > 0) {
                        int newBirthYear = Integer.parseInt(editable.toString());
                        getUser().getUserModel().setBirthYear(newBirthYear);
                    }
                    handled = true;
                }
                return handled;
            }
        });
    }
}

