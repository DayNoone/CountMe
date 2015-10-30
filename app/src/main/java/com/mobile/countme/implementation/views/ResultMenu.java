package com.mobile.countme.implementation.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.mobile.countme.R;
import com.mobile.countme.custom_classes.CustomTextView;
import com.mobile.countme.framework.AppMenu;
import com.mobile.countme.framework.MapsActivity;
import com.mobile.countme.implementation.controllers.HTTPSender;
import com.mobile.countme.implementation.controllers.MainMenu;
import com.mobile.countme.implementation.models.ErrorModel;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Kristian on 16/09/2015.
 */
public class ResultMenu extends AppMenu {

    private JSONObject survey;
    private int surveyCounter = 0;
    private Timer surveyTimer;
    private TimerTask surveyTimerTask;

    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.result_activity);
        CustomTextView co2_saved = (CustomTextView) findViewById(R.id.co2_saved_result);
        CustomTextView distance = (CustomTextView) findViewById(R.id.distance_result);
        CustomTextView calories_display = (CustomTextView) findViewById(R.id.calories_burned);
        CustomTextView avgSpeed = (CustomTextView) findViewById(R.id.avgSpeed_result);
        CustomTextView time_used = (CustomTextView) findViewById(R.id.time_used);
        co2_saved.setText(getMainController().getTripModel().getCo2_saved() + " g");
        Double transformedDistance = new BigDecimal(getMainController().getTripModel().getDistance()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        distance.setText(transformedDistance + " km");
        calories_display.setText(getMainController().getTripModel().getKcal() + " kcal");
        Double transformedAvgSpeed = new BigDecimal(getMainController().getTripModel().getAvg_speed()).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        avgSpeed.setText(transformedAvgSpeed + " km/t");
        if(getMainController().isTripInitialized()) {
            time_used.setText(getMainController().getTimeInFormat(-1));
        }else{
            time_used.setText(getMainController().getLastTime());
        }
        getMainController().setTripInitialized(false);
        initSpinner();
        initSurveyTimer();
    }

    public void goToMainMenu(View view){
        HTTPSender.sendErrors(getMainController().getTripErrors());
        getMainController().resetErrors();
        goTo(MainMenu.class);
    }

    public void goToMaps(View view) {
        goTo(MapsActivity.class);
    }

    private void initSpinner(){
        final Spinner dropdown = (Spinner)findViewById(R.id.spinnerErrors);
        ArrayList<String> items = new ArrayList<>();
        items.add(getString(R.string.errorsList));
        for(ErrorModel error : getMainController().getTripErrors().values()){
            items.add(error.getName());
        }
        dropdown.setPrompt("Feilmeldinger");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.error_dropdownlist, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                String item = dropdown.getSelectedItem().toString();
                if (getMainController().getTripErrors().containsKey(item)) {
                    getMainController().getTripErrors().get(item).setThisError();
                    goTo(ErrorMenu.class);
                }
                Log.e("Selected item : ", item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    /**
     * Survey timer will check if you receive a survey within 10 seconds. If not, nothing will happen.
     */
    private void initSurveyTimer(){
        surveyTimer = new Timer();
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.survey, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
//                                result.setText(userInput.getText());
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
        surveyTimerTask = new TimerTask() {
            @Override
            public void run() {
                surveyCounter++;
                if(surveyCounter > 10){
                    Log.e("ResultMenu", "timer timed out");
                    if (surveyTimer != null) {
                        surveyTimer.cancel();
                        surveyTimer = null;
                    }
                    surveyCounter = 0;
                }
                if(HTTPSender.getSurvey() != null){
                    survey = HTTPSender.getSurvey();
//                    LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE );
//                    new AlertDialog.Builder(getApplicationContext())
//                            .setMessage(R.string.survey)
//                            .setView(inflater.inflate(R.layout.survey, null))
//                            .setPositiveButton(R.string.finished_editing, new DialogInterface.OnClickListener() {
//
//                                public void onClick(DialogInterface arg0, int arg1) {
//
//                                }
//                            }).create().show();
                    if (surveyTimer != null) {
                        surveyTimer.cancel();
                        surveyTimer = null;
                    }
                    surveyCounter = 0;
                }
            }
        };
    }

    @Override
    public void onBackPressed() {

    }


}
