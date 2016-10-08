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
        Idr.initSDK(this)// 初始化SDK
                .needCrash();// 拦截崩溃，写入到崩溃日志文件
    }
}
