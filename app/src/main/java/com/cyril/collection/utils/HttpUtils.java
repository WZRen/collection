package com.cyril.collection.utils;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by cyril on 2017/5/11.
 */

public class HttpUtils {
    public static void login(final String url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection;
                try {
                    connection = (HttpURLConnection)new URL(url).openConnection();
                    connection.setRequestMethod("GET");
                    connection.getInputStream();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
