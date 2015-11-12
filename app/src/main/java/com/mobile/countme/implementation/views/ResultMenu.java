package com.mobile.countme.implementation.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.mobile.countme.R;
import com.mobile.countme.custom_classes.CustomTextView;
import com.mobile.countme.framework.AppMenu;
import com.mobile.countme.framework.MapsActivity;
import com.mobile.countme.implementation.controllers.HTTPSender;
import com.mobile.countme.implementation.controllers.MainMenu;
import com.mobile.countme.implementation.models.ErrorModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.google.android.gms.internal.zzhu.runOnUiThread;

/**
 * Created by Kristian on 16/09/2015.
 */
public class ResultMenu extends AppMenu {

    private JSONObject survey;
    private int surveyCounter = 0;
    private Timer surveyTimer;
    private TimerTask surveyTimerTask;
    private AppCompatActivity context = this;
    private String[] items = {};

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
        avgSpeed.setText(transformedAvgSpeed + " " + getString(R.string.kmph));
        if(getMainController().isTripInitialized()) {
            time_used.setText(getMainController().getTimeInFormat(-1));
        }else{
            time_used.setText(getMainController().getLastTime());
        }
        getMainController().setTripInitialized(false);
        initSpinner();
        if(getMainController().getUserModel().isReceiveSurveys()){
            initSurveyTimer();
        }
    }

    public void goToMainMenu(View view){
        HTTPSender.sendErrors(getMainController().getTripErrors());
        getMainController().resetErrors();
        goTo(MainMenu.class);
    }

    public void goToMaps(View view) {
        goTo(MapsActivity.class);
    }

    /**
     * Initializes the spinner containing all the reported road faults.
     */
    private void initSpinner(){
        final Spinner dropdown = (Spinner)findViewById(R.id.spinnerErrors);
        ArrayList<String> items = new ArrayList<>();
        items.add(getString(R.string.errorsList));
        for(ErrorModel error : getMainController().getTripErrors().values()){
            items.add(error.getName());
        }
        dropdown.setPrompt(getString(R.string.errorDialog));
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
                //If a survey is sent.
                if(HTTPSender.getSurvey() != null){
                    survey = HTTPSender.getSurvey();
                    createDialog();
                    if (surveyTimer != null) {
                        surveyTimer.cancel();
                        surveyTimer = null;
                    }
                    surveyCounter = 0;
                    HTTPSender.setSurvey(null);
                }
            }
        };

        surveyTimer.schedule(surveyTimerTask, 1000, 1000);
    }

    /**
     * Creates a dialog for the user based on what type of survey he/she receives.
     */
    private void createDialog(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.survey, null);

                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);
                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);
                try {
                    alertDialogBuilder.setTitle(survey.getString("title"));
                    final ArrayList<Integer> selectedItems = new ArrayList();

                    switch (survey.getString("type")){
                        case "text":
                            alertDialogBuilder.setMessage(survey.getString("question"));
                            break;
                        case "link":
                            userInput.setVisibility(View.GONE);
                            final SpannableString s =
                                    new SpannableString(survey.getString("question"));
                            Linkify.addLinks(s, Linkify.ALL);
                            alertDialogBuilder.setMessage(s);
                            break;
                        case "radio":
                            alertDialogBuilder.setTitle(survey.getString("question"));
                            userInput.setVisibility(View.GONE);
                            JSONArray jsonArray = (JSONArray)survey.get("alternatives");
                            items = new String[jsonArray.length()];
                            for(int i = 0; i < jsonArray.length(); i++){
                                items[i] = jsonArray.get(i).toString();
                            }
                            alertDialogBuilder.setSingleChoiceItems( items, -1, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int item) {
                                    selectedItems.clear();
                                    selectedItems.add(item);
                                }
                            });
                            break;
                        case "check":
                            alertDialogBuilder.setTitle(survey.getString("question"));
                            userInput.setVisibility(View.GONE);
                            JSONArray jsonArrayCheck = (JSONArray)survey.get("alternatives");
                            items = new String[jsonArrayCheck.length()];
                            for(int i = 0; i < jsonArrayCheck.length(); i++){
                                items[i] = jsonArrayCheck.get(i).toString();
                            }
                            alertDialogBuilder.setMultiChoiceItems(items, null,
                                    new DialogInterface.OnMultiChoiceClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int indexSelected,
                                                            boolean isChecked) {
                                            if (isChecked) {
                                                // If the user checked the item, add it to the selected items
                                                selectedItems.add(indexSelected);
                                            } else if (selectedItems.contains(indexSelected)) {
                                                // Else, if the item is already in the array, remove it
                                                selectedItems.remove(Integer.valueOf(indexSelected));
                                            }
                                        }
                                    });
                            break;
                    }

                    // set dialog message
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton(getString(R.string.finished_editing),
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // get user input and set it to result
                                            // edit text
                                                try {
                                                    switch(survey.getString("type")){
                                                        case "text":
                                                            HTTPSender.sendAnswer(userInput.getText().toString(), survey.getString("_id"));
                                                            break;
                                                        case "link":
                                                            break;
                                                        case "radio":
                                                            for(Integer item : selectedItems){
                                                                HTTPSender.sendAnswer(items[item], survey.getString("_id"));
                                                            }
                                                            break;
                                                        case "check":
                                                            for(Integer item : selectedItems){
                                                                HTTPSender.sendAnswer(items[item], survey.getString("_id"));
                                                            }
                                                            break;
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                        }
                                    });

                    if(!survey.getString("type").equals("link")){
                                            alertDialogBuilder.setNegativeButton(getString(R.string.cancel),
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.cancel();
                                                        }
                                });
                    }

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();

                    if(survey.getString("type").equals("link")){
                        //Sets the link clickable.

                        ((TextView)alertDialog.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

    }


}
