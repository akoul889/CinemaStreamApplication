package com.akshay.cinemastream;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by AKSHAY on 02-10-2015.
 */
public class CinemaApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
