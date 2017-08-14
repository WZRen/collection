package com.cyril.collection;

import android.app.Application;

import com.letv.sarrsdesktop.blockcanaryex.jrt.BlockCanaryEx;
import com.letv.sarrsdesktop.blockcanaryex.jrt.Config;

import java.util.Random;

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
        boolean isInSamplePro = BlockCanaryEx.isInSamplerProcess(this);
        if (!isInSamplePro) {
            BlockCanaryEx.install(new Config(this));
        }

        super.onCreate();
        application = this;
        //异常收集处理
//        CrashHandler1.getInstance().init(this);

        if (!isInSamplePro) {
            doHeavyWork();
        }

    }

    private void doHeavyWork() {
        long startTime = System.currentTimeMillis();
        Random random = new Random();
        while (System.currentTimeMillis() - startTime < 300L) {
            random.nextInt(Integer.MAX_VALUE);
        }
    }
}

