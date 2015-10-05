package com.mobile.countme.implementation.menus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mobile.countme.R;
import com.mobile.countme.framework.AppMenu;
import com.mobile.countme.implementation.controllers.MainPages;

/**
 * Created by Kristian on 16/09/2015.
 */
public class ErrorMenu extends AppMenu {

    ImageView photoTaken;

    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.error_activity);

        photoTaken = (ImageView)findViewById(R.id.pictureTaken);
    }

    public void sendReport(View view){
        if(getUser().isTripInitialized()){
            goTo(BikingActive.class);
        }else {
            goTo(ResultMenu.class);
        }
        goTo(MainPages.class);
    }

    public void createDescription(){
        //TODO: Pop up to add description.
//        getUser().addDescription();
    }

    public void startCamera(View view){
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bp = (Bitmap) data.getExtras().get("data");
        getUser().addPhoto(bp);
        photoTaken.setImageBitmap(bp);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

}
