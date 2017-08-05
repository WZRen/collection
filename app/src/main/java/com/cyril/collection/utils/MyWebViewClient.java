package com.cyril.collection.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.InputStream;
import java.util.HashSet;

/**
 * Created by cyril on 2017/3/16.
 */
public class MyWebViewClient extends WebViewClient {
    private Context context;
    private HashSet<String> offlineResources = new HashSet<>();
    private final String folderName = "offline_res";

    public MyWebViewClient(Context context) {
        this.context = context;
        FileUtil.deepFiles(context, folderName, offlineResources);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        if (url.endsWith(".js") || url.endsWith(".css")) {
            String suffix = StringUtil.getFinalResource(url);
            if (offlineResources.contains(suffix)) {
                String mimeType;
                if (suffix.endsWith(".js")) {
                    mimeType = "application/javascript";
                } else if (suffix.endsWith(".css")) {
                    mimeType = "text/css";
                } else if (suffix.endsWith(".gif")) {
                    mimeType = "image/gif";
                } else if (suffix.endsWith(".png")) {
                    mimeType = "image/png";
                } else if (suffix.endsWith(".jpeg")) {
                    mimeType = "image/jpg";
                } else {
                    mimeType = "text/html";
                }
                try {
                    InputStream is = context.getAssets().open(folderName + "/" + suffix);
                    return new WebResourceResponse(mimeType, "UTF-8", is);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.shouldInterceptRequest(view, url);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        String url = request.getUrl().toString();
        if (url.endsWith(".js") || url.endsWith(".css")) {
            String suffix = StringUtil.getFinalResource(url);
            if (offlineResources.contains(suffix)) {
                String mimeType;
                if (suffix.endsWith(".js")) {
                    mimeType = "application/javascript";
                } else if (suffix.endsWith(".css")) {
                    mimeType = "text/css";
                } else if (suffix.endsWith(".gif")) {
                    mimeType = "image/gif";
                } else if (suffix.endsWith(".png")) {
                    mimeType = "image/png";
                } else if (suffix.endsWith(".jpeg")) {
                    mimeType = "image/jpg";
                } else {
                    mimeType = "text/html";
                }
                try {
                    InputStream is = context.getAssets().open(folderName + "/" + suffix);
                    return new WebResourceResponse(mimeType, "UTF-8", is);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.shouldInterceptRequest(view, request);
    }

}
