package com.mobile.countme.implementation.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mobile.countme.R;
import com.mobile.countme.custom_views.CustomTextView;
import com.mobile.countme.framework.AppMenu;
import com.mobile.countme.framework.PopUpMenuEventHandle;
import com.mobile.countme.implementation.controllers.MainMenu;
import com.mobile.countme.implementation.models.ErrorModel;

import java.math.BigDecimal;

/**
 * Created by Kristian on 16/09/2015.
 */
public class ResultMenu extends AppMenu {

    private PopupMenu popupMenu;
    private PopupWindow mDropdown = null;
    LayoutInflater mInflater;
    Button pop;

    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.result_activity);
        CustomTextView co2_saved = (CustomTextView) findViewById(R.id.co2_saved_result);
        CustomTextView distance = (CustomTextView) findViewById(R.id.distance_result);
        CustomTextView avgSpeed = (CustomTextView) findViewById(R.id.avgSpeed_result);
        CustomTextView time_used = (CustomTextView) findViewById(R.id.time_used);
        co2_saved.setText(getUser().getTripModel().getCo2_saved() + " g");
        Double transformedDistance = new BigDecimal(getUser().getTripModel().getDistance()).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        distance.setText(transformedDistance + " km");
        Double transformedAvgSpeed = new BigDecimal(getUser().getTripModel().getAvg_speed()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        avgSpeed.setText(transformedAvgSpeed + " m/s");
        time_used.setText(getUser().getTimeInFormat(-1));
        getUser().setTripInitialized(false);
        pop = (Button) findViewById(R.id.popupError);
    }

    public void goToMainMenu(View view){
        getUser().resetErrors();
        goTo(MainMenu.class);
    }

    public void showErrorPopupList(View view){
        try {

            mInflater = (LayoutInflater) getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = mInflater.inflate(R.layout.error_popupwindow, null);

            //If you want to add any listeners to your textviews, these are two //textviews.
            final TextView itema = (TextView) layout.findViewById(R.id.noErrorsReported);


            layout.measure(View.MeasureSpec.UNSPECIFIED,
                    View.MeasureSpec.UNSPECIFIED);
            mDropdown = new PopupWindow(layout, FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT,true);
            Drawable background = getResources().getDrawable(android.R.drawable.editbox_dropdown_dark_frame);
            mDropdown.setBackgroundDrawable(background);
            mDropdown.showAsDropDown(pop, 5, 5);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showErrorList(View view){
        popupMenu=new PopupMenu(this,view);
        MenuInflater menuInflater=popupMenu.getMenuInflater();
        PopUpMenuEventHandle popUpMenuEventHandle=new PopUpMenuEventHandle(getApplicationContext());
        popupMenu.setOnMenuItemClickListener(popUpMenuEventHandle);
        menuInflater.inflate(R.menu.error_popuplist, popupMenu.getMenu());
        popupMenu.show();
        for(ErrorModel error : getUser().getTripErrors().values()){
            popupMenu.getMenu().add(error.toString());
        }
        if(popupMenu.getMenu().size() > 1){
            popupMenu.getMenu().getItem(0).setVisible(false);
        }
        for (int i = 0; i < popupMenu.getMenu().size(); i++){
            Log.e("ResultMenu", "title: " + popupMenu.getMenu().getItem(i).getTitle());
        }
        new Thread() {
            public void run() {
                try

                {
                    Log.e("ResultMenu","erroclicked: " + getUser().isErrorClicked());
                    while(true) {
                        if (getUser().isErrorClicked()) {
                            Log.e("ResultMenu","erroclickedyes: " + getUser().isErrorClicked());
                            getUser().setErrorClicked(false);
                            goTo(ErrorMenu.class);
                            break;
                        }
                    }
                } catch (
                        Exception ex
                        )

                {
                    Log.e("Client", "Something went wrong - couldn't connect");
                    ex.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void onBackPressed() {

    }
}
