package com.cyril.collection.JsBridge;

/**
 * Created by cyril on 2017/5/15.
 */

public interface WebViewJavascriptBridge {
    public void send(String data);

    public void send(String data, CallBackFunction responseCallBack);
}
