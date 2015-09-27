package com.mobile.countme.storage_and_memory;

import android.content.Context;
import android.graphics.Typeface;

import com.mobile.countme.framework.AppMenu;

/**
 * Created by Kristian on 11/09/2015.
 */
public class Assets {

    public static final int baskerville_old_face_regular = 0;

    private static boolean fontsLoaded;
    private static Typeface[] fonts;
    private AppMenu menu;

    public Assets (AppMenu menu){
        this.menu = menu;
    }

    // To add more fonts, simply add them to the fonts folder, and add their path here
    public static final String[] fontPath = {
            "fonts/bakserville-old-face-regular.ttf"
    };

    //return the font requested, or load them if not done yet
    public static Typeface getTypeface(Context context, int fontIdentifier) {
        if(!fontsLoaded)
            loadFonts(context);
        return fonts[fontIdentifier];
    }

    //Load the fonts as described in fontPath
    private static void loadFonts(Context context) {
        fonts = new Typeface[fontPath.length];
        int i = 0;
        for(String s : fontPath) {
            fonts[i] = Typeface.createFromAsset(context.getAssets(), s);
            i++;
        }
        fontsLoaded = true;
    }

}
