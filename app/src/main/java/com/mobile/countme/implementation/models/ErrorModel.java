package com.mobile.countme.implementation.models;


import android.graphics.Bitmap;

import com.mobile.countme.implementation.controllers.MainController;

/**
 * Created by Robin on 05.10.2015.
 */
public class ErrorModel {

    private String descprition = "";
    private String coordinates;
    private Bitmap photoTaken;
    private boolean editedWhenReported;

    private MainController mainController;

    public ErrorModel(MainController mainController){
        this.mainController = mainController;
    }

    public String getDescprition() {
        return descprition;
    }

    public void setDescprition(String descprition) {
        this.descprition = descprition;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public Bitmap getPhotoTaken() {
        return photoTaken;
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

    public void setEditedWhenReported(boolean editedWhenReported) {
        this.editedWhenReported = editedWhenReported;
    }

    @Override
    public String toString() {
        return coordinates;
    }
}
