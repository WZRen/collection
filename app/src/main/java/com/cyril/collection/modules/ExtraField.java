package com.cyril.collection.modules;

import java.io.Serializable;

/**
 * Created by cyril on 2017/8/11.
 */

public class ExtraField implements Serializable {
    private boolean isHeader;
    private String date;

    public ExtraField(boolean isHeader, String date){
        this.isHeader = isHeader;
        this.date = date;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
