package com.cyril.collection.modules;

import com.cyril.collection.widget.calendar.BaseCalendarItemModel;

/**
 * Created by cyril on 2017/8/11.
 */

public class CustomCalendarItemModel extends BaseCalendarItemModel {
    private int newsCount;
    private boolean isFav;

    public int getNewsCount() {
        return newsCount;
    }

    public void setNewsCount(int newsCount) {
        this.newsCount = newsCount;
    }

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }
}
