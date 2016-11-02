package com.ssthouse.retrofitlearn;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by ssthouse on 02/11/2016.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
