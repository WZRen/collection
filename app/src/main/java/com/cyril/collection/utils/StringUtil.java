package com.cyril.collection.utils;

import android.text.TextUtils;
import android.util.Log;

import java.text.DecimalFormat;

/**
 * Created by cyril on 2017/1/17.
 */
public class StringUtil {
    //获取url的host
    public static String getHostName(String url) {
        String head = "";
        int index = url.indexOf("://");
        if (index != -1) {
            head = url.substring(0, index + 3);
            url = url.substring(index + 3);
        }
        index = url.indexOf("/");
        if (index != -1) {
            url = url.substring(0, index + 1);
        }
        return head + url;
    }

    //从url中截取资源名称
    public static String fetchSourceName(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        } else {
            return url.substring(url.lastIndexOf("/") + 1);
        }
    }

    //获取最终的资源文件
    public static String getFinalResource(String url) {
        Log.e("wzr_final", url);
        try {
            String replaceStr = url.replace(StringUtil.getHostName(url), "");
            String pathStr = replaceStr.substring(0, replaceStr.lastIndexOf("/"));
            String recourseName = replaceStr.substring(replaceStr.lastIndexOf("/") + 1);
            String finalStr = pathStr.replaceAll("/", "");
            Log.e("wzr_final", finalStr + "/" + recourseName);
            return finalStr + "/" + recourseName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    //获取文件大小和单位
    public static String getDateSize(long size) {
        DecimalFormat var = new DecimalFormat("###.00");
        return size < 1024L ? size + "bytes" : (size < 1048576L ? var.format((double) ((float) size / 1024.0F))
                + "KB" : (size < 1073741824L ? var.format((double) ((float) size / 1024.0F / 1024.0F))
                + "MB" : (size < 0L ? var.format((double) ((float) size / 1024.0F / 1024.0F / 1024.0F))
                + "GB" : "error")));
    }
}
