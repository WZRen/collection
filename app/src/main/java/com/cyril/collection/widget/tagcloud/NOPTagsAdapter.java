package com.cyril.collection.widget.tagcloud;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by cyril on 2017/8/4.
 */

public class NOPTagsAdapter extends TagsAdapter {
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public View getView(Context context, int position, ViewGroup parent) {
        return null;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public int getPopularity(int position) {
        return 0;
    }

    @Override
    public void onThemeColorChanged(View view, int themeColor) {

    }
}
