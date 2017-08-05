package com.cyril.collection;

import android.app.Application;

/**
 * Created by cyril on 2017/1/17.
 */
public class MyApplication extends Application {
    private static MyApplication application;

    public static MyApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
//        CrashHandler1.getInstance().init(this);
    }
}

