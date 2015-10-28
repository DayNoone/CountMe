package com.mobile.countme.implementation.models;


import android.graphics.Bitmap;
import android.util.Base64;

import com.mobile.countme.implementation.controllers.MainController;

import java.io.ByteArrayOutputStream;
import java.util.Date;

/**
 * Created by Robin on 05.10.2015.
 */
public class ErrorModel {

    private String description = "";
    private String name;
    private String longitude;
    private String latitude;
    private Bitmap photoTaken;
    private boolean editedWhenReported;
    private long timeStamp;
    private boolean createdInIdle;

    private MainController mainController;

    public ErrorModel(MainController mainController){
        this.mainController = mainController;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Bitmap getPhotoTaken() {
        return photoTaken;
    }

    public String getPhotoTakenInBase64() {
        if(photoTaken == null) return null;
        //TODO: Set size of picture - compress?
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        photoTaken.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return encoded;
    }

    public void setPhotoTaken(Bitmap photoTaken) {
        this.photoTaken = photoTaken;
    }

    public void setThisError(){
        mainController.setErrorModel(this);
    }
    public boolean isEditedWhenReported() {
        return editedWhenReported;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setEditedWhenReported(boolean editedWhenReported) {
        this.editedWhenReported = editedWhenReported;
    }

    public void setCreatedInIdle(boolean createdInIdle) {
        this.createdInIdle = createdInIdle;
    }

    public String getDescription() {
        return description;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getName() {
        return name;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public boolean isCreatedInIdle() {
        return createdInIdle;
    }

    @Override
    public String toString() {
        return name;
    }
}
