package com.cyril.collection.modules;

/**
 * Created by cyril on 2017/1/13.
 */
public class Response<T> {
    private Data<T> data;
    private String info;
    private String code;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

