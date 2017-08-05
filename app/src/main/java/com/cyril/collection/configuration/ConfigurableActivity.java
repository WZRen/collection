package com.cyril.collection.configuration;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.cyril.collection.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by cyril on 2017/2/22.
 */
public class ConfigurableActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurable);
        initView();
    }

    private void initView() {
        textView = (TextView) findViewById(R.id.textview);
        readJSONFile();
    }

    private void readJSONFile() {
        try {
            InputStreamReader isr = new InputStreamReader(getAssets().open("app.json"), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line = "";

            StringBuilder builder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            br.close();
            isr.close();

            JSONObject object = new JSONObject(builder.toString());
            String heightStr = object.getString("home_banner_height");
            textView.setText(heightStr);
            textView.setTextSize(object.getInt("text_size"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
