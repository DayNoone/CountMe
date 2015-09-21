package com.mobile.countme.custom_views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.mobile.countme.storage_and_memory.Assets;

/**
 * Created by Kristian on 21/09/2015.
 */
public class CustomTextView extends TextView{

    public CustomTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), Assets.fontPath[Assets.baskerville_old_face_regular]));
    }

}
