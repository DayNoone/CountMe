package com.mobile.countme.implementation.models;


import android.graphics.Bitmap;

import com.mobile.countme.implementation.controllers.User;

/**
 * Created by Robin on 05.10.2015.
 */
public class ErrorModel {

    private String descprition = "";
    private String coordinates;
    private Bitmap photoTaken;
    private boolean editedWhenReported;

    private User user;

    public ErrorModel(User user){
        this.user = user;
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
        user.setErrorModel(this);
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
