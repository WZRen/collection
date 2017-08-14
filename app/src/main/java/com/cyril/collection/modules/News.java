package com.cyril.collection.modules;

import java.util.List;

/**
 * Created by cyril on 2017/8/11.
 */

public class News {
    private String date;
    private List<StoriesBean> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }
}
