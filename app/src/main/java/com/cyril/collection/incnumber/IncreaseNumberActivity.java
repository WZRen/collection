package com.cyril.collection.incnumber;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.cyril.collection.R;
import com.cyril.collection.widget.IncreaseNumberView;

/**
 * Created by cyril on 2017/8/4.
 */

public class IncreaseNumberActivity extends Activity {
    private IncreaseNumberView number1, number2, number3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_increase_number);
        initView();
    }

    private void initView() {
        number1 = (IncreaseNumberView) findViewById(R.id.number1);
        number2 = (IncreaseNumberView) findViewById(R.id.number2);
        number3 = (IncreaseNumberView) findViewById(R.id.number3);
    }

    public void start(View view) {
        number1.setPrefixString("￥");
        number1.setNumberString("0.00");

        number2.setEnableAnim(false);
        number2.setNumberString("999112");

        number3.setPostfixString("%");
        number3.setNumberString("99.75");

    }
}
