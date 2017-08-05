package com.cyril.collection.tagclouddemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cyril.collection.JsBridge.JsBridgeActivity;
import com.cyril.collection.MainActivity;
import com.cyril.collection.addtabrow.AddTableRowActivity;
import com.cyril.collection.codelogin.BCLoginActivity;
import com.cyril.collection.configuration.ConfigurableActivity;
import com.cyril.collection.crashcatch.CrashCaughtActivity;
import com.cyril.collection.fetchpiccolor.FetchColorActivity;
import com.cyril.collection.incnumber.IncreaseNumberActivity;
import com.cyril.collection.picupload.ImageDealActivity;
import com.cyril.collection.sendmail.SendMailActivity;
import com.cyril.collection.uncertaintyjson.DiffTypeOfJsonActivity;
import com.cyril.collection.uploadfiles.UploadActivity;
import com.cyril.collection.widget.tagcloud.TagsAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by cyril on 2017/8/4.
 */

public class TextTagsAdapter extends TagsAdapter {
    private List<String> dataSet = new ArrayList<>();

    public TextTagsAdapter(@NonNull String... data) {
        dataSet.clear();
        Collections.addAll(dataSet, data);
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public View getView(final Context context, final int position, ViewGroup parent) {
        TextView tv = new TextView(context);
        tv.setText(dataSet.get(position));
        tv.setGravity(Gravity.CENTER);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (position) {
                    case 0:
                        context.startActivity(new Intent(context, MainActivity.class));
                        break;
                    case 1:
                        context.startActivity(new Intent(context, AddTableRowActivity.class));
                        break;
                    case 2:
                        context.startActivity(new Intent(context, ConfigurableActivity.class));
                        break;
                    case 3:
                        context.startActivity(new Intent(context, FetchColorActivity.class));
                        break;
                    case 4:
                        context.startActivity(new Intent(context, ImageDealActivity.class));
                        break;
                    case 5:
                        context.startActivity(new Intent(context, CrashCaughtActivity.class));
                        break;
                    case 6:
                        context.startActivity(new Intent(context, SendMailActivity.class));
                        break;
                    case 7:
                        context.startActivity(new Intent(context, BCLoginActivity.class));
                        break;
                    case 8:
                        context.startActivity(new Intent(context, JsBridgeActivity.class));
                        break;
                    case 9:
                        context.startActivity(new Intent(context, UploadActivity.class));
                        break;
                    case 10:
                        context.startActivity(new Intent(context, DiffTypeOfJsonActivity.class));
                        break;
                    case 11:
                        context.startActivity(new Intent(context, IncreaseNumberActivity.class));
                        break;
                }
            }
        });
        tv.setTextColor(Color.WHITE);
        return tv;
    }

    @Override
    public Object getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public int getPopularity(int position) {
        return position % 7;
    }

    @Override
    public void onThemeColorChanged(View view, int themeColor) {
        view.setBackgroundColor(themeColor);
    }
}
