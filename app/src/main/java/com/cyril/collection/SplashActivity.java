package com.cyril.collection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.cyril.collection.widget.WowSplashView;
import com.cyril.collection.widget.WowView;

/**
 * Created by cyril on 2017/8/4.
 */

public class SplashActivity extends Activity {

    private WowSplashView wowSplashView;
    private WowView wowView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
    }

    private void initView() {
        wowSplashView = (WowSplashView) findViewById(R.id.wow_splash);
        wowView = (WowView) findViewById(R.id.wow_view);

        wowSplashView.startAnimate();

        wowSplashView.setOnEndListener(new WowSplashView.OnEndListener() {
            @Override
            public void onEnd(WowSplashView wowSplashView) {
                wowSplashView.setVisibility(View.GONE);
                wowView.setVisibility(View.VISIBLE);
                wowView.startAnimate(wowSplashView.getDrawingCache());
                try {

                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(SplashActivity.this, StartActivity.class));
                SplashActivity.this.finish();
            }
        });
    }
}
