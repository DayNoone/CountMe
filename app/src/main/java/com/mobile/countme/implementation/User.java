package com.mobile.countme.implementation;

import com.mobile.countme.framework.AppMenu;
import com.mobile.countme.implementation.controllers.MainPages;

/**
 * Created by Robin on 27.09.2015.
 */
public class User {

    private AndroidFileIO fileIO;
    private AppMenu context;
    private MainPages mainPages;

    public User (AndroidFileIO io, AppMenu context) {
        this.fileIO = io;
        this.context = context;
    }

    public void setMainPages(MainPages mainPages) {
        this.mainPages = mainPages;
    }

    public MainPages getMainPages() {
        return mainPages;
    }
}
