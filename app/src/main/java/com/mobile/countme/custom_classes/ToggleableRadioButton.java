package com.mobile.countme.custom_classes;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mobile.countme.R;
import com.mobile.countme.framework.AppMenu;
import com.mobile.countme.implementation.controllers.MainController;

/**
 * Created by Robin on 24.10.2015.
 */
public class ToggleableRadioButton extends RadioButton {
    public ToggleableRadioButton(Context context) {
        super(context);
    }

    public ToggleableRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ToggleableRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ToggleableRadioButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    // Implement necessary constructors

    @Override
    public void toggle() {
        if(isChecked()) {
//            AppMenu.getMainController().getUserModel().setGender("");
            Log.e("Toggle", "setGenderZero");
            if(getParent() instanceof RadioGroup) {
                ((RadioGroup)getParent()).clearCheck();
            }
        } else {
            setChecked(true);
        }
    }
}
