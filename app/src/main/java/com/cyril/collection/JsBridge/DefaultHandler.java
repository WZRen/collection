package com.cyril.collection.JsBridge;

/**
 * Created by cyril on 2017/5/15.
 */

public class DefaultHandler implements BridgeHandler {
    String TAG = "DefaultHandler";

    @Override
    public void handler(String data, CallBackFunction function) {
        if (function != null) {
            function.onCallBack("DefaultHandler response data");
        }
    }
}
