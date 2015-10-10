package com.mobile.countme.framework;

import android.content.Context;
import android.util.Log;
import android.view.MenuItem;
import android.widget.PopupMenu;

import com.mobile.countme.implementation.views.ErrorMenu;

/**
 * Created by Robin on 05.10.2015.
 */
public class PopUpMenuEventHandle extends AppMenu implements PopupMenu.OnMenuItemClickListener {
    Context context;

    public PopUpMenuEventHandle(Context context){
        this.context =context;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if(getUser().getTripErrors().containsKey(item.getTitle())) {
            getUser().getTripErrors().get(item.getTitle()).setThisError();
            Log.e("Popup", "elementClicked");
            getUser().setErrorClicked(true);
            return true;
        }
        return false;
    }
}