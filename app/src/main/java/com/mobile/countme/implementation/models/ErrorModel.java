package com.mobile.countme.implementation.models;


import android.graphics.Bitmap;

/**
 * Created by Robin on 05.10.2015.
 */
public class ErrorModel {

    private String descprition;
    private String coordinates;
    private Bitmap photoTaken;

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

}
