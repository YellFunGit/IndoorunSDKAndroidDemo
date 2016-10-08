package com.yellfun.demo;

import android.app.Application;

import com.indoorun.mapapi.control.Idr;


/**
 * Application
 * Created by sharyuke on 16-9-12.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Idr.initSDK(this);
        Thread.setDefaultUncaughtExceptionHandler((thread, ex) -> {
            ex.printStackTrace();
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.exit(0);
        });
    }
}
