package com.mobile.countme.framework;

import android.content.Context;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.mobile.countme.R;

/**
 * Created by Robin on 05.10.2015.
 */
public class PopUpMenuEventHandle implements PopupMenu.OnMenuItemClickListener {
    Context context;
    public PopUpMenuEventHandle(Context context){
        this.context =context;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId()== R.id.id_admin) {
            return true;
        }
        if (item.getItemId()==R.id.id_user){

        }
        return false;
    }
}