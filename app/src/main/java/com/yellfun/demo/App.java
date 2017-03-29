package com.yellfun.demo;

import android.app.Application;

import com.indoorun.mapapi.control.Idr;


/**
 * Application
 * Created by sharyuke on 16-9-12.
 */
public class App extends Application {

        public static final String REGION_ID = "14428254382730015";
//    public static final String REGION_ID = "14900020402039555";
        public static final String FLOOR_ID = "14557583851000004";
//    public static final String FLOOR_ID = "14900020402219556";

    @Override
    public void onCreate() {
        super.onCreate();
        Idr.initSDK(this)// 初始化SDK
                .setLocateUrl("test.locating.indoorun.com")
                .needCrash();// 拦截崩溃，写入到崩溃日志文件
    }
}
