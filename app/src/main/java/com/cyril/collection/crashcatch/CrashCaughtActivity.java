package com.cyril.collection.crashcatch;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cyril.collection.R;

/**
 * Created by cyril on 2017/5/9.
 */
public class CrashCaughtActivity extends Activity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash_caught);
//        createCrash();
        textView = (TextView)findViewById(R.id.text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCrash();
            }
        });
    }

    private void createCrash() {
//        textView.setText("触发异常");
        int a = 1;
        int b = 0;
        int c = a / b;
    }
}
