package com.cyril.collection.tagclouddemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyril.collection.R;
import com.cyril.collection.widget.tagcloud.TagsAdapter;

/**
 * Created by cyril on 2017/8/4.
 */

public class ViewTagsAdapter extends TagsAdapter {
    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public View getView(Context context, int position, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.tag_item_view, parent, false);
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public int getPopularity(int position) {
        return position % 5;
    }

    @Override
    public void onThemeColorChanged(View view, int themeColor) {
        view.findViewById(R.id.android_eye).setBackgroundColor(themeColor);
    }
}
