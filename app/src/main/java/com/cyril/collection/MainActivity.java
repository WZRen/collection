package com.cyril.collection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.cyril.collection.comconfig.AppConfig;
import com.cyril.collection.downSource.Download;
import com.cyril.collection.downSource.DownloadService;
import com.cyril.collection.modules.SourceMod;
import com.cyril.collection.post.DataPosts;
import com.cyril.collection.post.HttpManager;
import com.cyril.collection.post.HttpOnNextListener;
import com.cyril.collection.post.ProgressSubscriber;
import com.cyril.collection.post.RequestCode;
import com.cyril.collection.utils.FileUtil;
import com.cyril.collection.utils.MethodsUtil;
import com.cyril.collection.utils.MyWebViewClient;

import java.io.File;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    public static final String MESSAGE_PROGRESS = "message_progress";
    private WebView webView;
    private Button refresh;

    private final Set<String> offlineResources = new HashSet<>();
    private String cacheFilePath = "", sourcePath = "";

    private TelephonyManager mTm;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(MESSAGE_PROGRESS)) {
                Download download = intent.getParcelableExtra("download");
//                Log.e("progress", download.getCurrentSize() + "");
            }
        }
    };

    private void registerReceiver() {
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(MESSAGE_PROGRESS);
        manager.registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mTm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        getBuild();

        cacheFilePath = FileUtil.getSourceCache(this);
        sourcePath = cacheFilePath + AppConfig.CACHE_FOLDER_NAME + File.separator;
        registerReceiver();

        webView = (WebView) findViewById(R.id.webview);
        refresh = (Button) findViewById(R.id.button);

//        getSourceInfo("");
//        fetchResourcesList();
//        fetchOfflineResources();

//        deepFiles(this, "offline_res");
//        for (Iterator<String> iterator = offlineResources.iterator(); iterator.hasNext(); ) {
//            Log.e("wzr_offResource", iterator.next());
//        }

        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.getSettings().setDomStorageEnabled(false);
        webView.clearCache(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

//        重写 WebViewClient.shouldInterceptRequest() 方法
//        webView.setWebViewClient(new WebViewClient() {
//        /*    @Override
//            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
//
//                int lastSlash = url.lastIndexOf("/");
//                if (lastSlash != -1) {
//                    String suffix = url.substring(lastSlash + 1);
//                    if (offlineResources.contains(sourcePath + suffix)) {
////                        try {
////                            Log.e("wzr_read", suffix + ": " + MethodsUtil.md5sum(cacheFilePath + AppConfig.CACHE_FOLDER_NAME+File.separator  + suffix));
////                        } catch (Exception e) {
////                            e.printStackTrace();
////                        }
//                        String mimeType;
//                        if (suffix.endsWith(".js")) {
//                            mimeType = "application/javascript";
//                        } else if (suffix.endsWith(".css")) {
//                            mimeType = "text/css";
//                        } else if (suffix.endsWith(".gif")) {
//                            mimeType = "image/gif";
//                        } else if (suffix.endsWith(".png")) {
//                            mimeType = "image/png";
//                        } else if (suffix.endsWith(".jpeg")) {
//                            mimeType = "image/jpg";
//                        } else {
//                            mimeType = "text/html";
//                        }
//                        try {
//                            FileInputStream fis = new FileInputStream(sourcePath + suffix);
//                            InputStream is = new BufferedInputStream(fis);
//                            return new WebResourceResponse(mimeType, "UTF-8", is);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        if (suffix.endsWith(".html") || suffix.endsWith(".ico")) {
//                        } else {
////                            Intent intent = new Intent(MainActivity.this, DownloadService2.class);
////                            intent.putExtra("url", url);
////                            intent.putExtra("path", cacheFilePath);
////                            startService(intent);
//                        }
////                        if (offlineResources.size() > 0) {
////                            Log.e("wzr", url+"; "+cacheFilePath);
////                            if (suffix.endsWith(".html") || suffix.endsWith(".ico")) {
////                            } else {
//////                                getSourceInfo(suffix);
////                                Intent intent = new Intent(MainActivity.this, DownloadService2.class);
////                                intent.putExtra("url", url);
////                                intent.putExtra("path", cacheFilePath);
////                                startService(intent);
////                            }
////                        }
//                    }
//                }
//                return super.shouldInterceptRequest(view, url);
//            }*/
//
//            @Override
//            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
//                if (url.endsWith(".js") || url.endsWith(".css")) {
//                    String suffix = StringUtil.getFinalResource(url);
//                    if (offlineResources.contains(suffix)) {
//                        String mimeType;
//                        if (suffix.endsWith(".js")) {
//                            mimeType = "application/javascript";
//                        } else if (suffix.endsWith(".css")) {
//                            mimeType = "text/css";
//                        } else if (suffix.endsWith(".gif")) {
//                            mimeType = "image/gif";
//                        } else if (suffix.endsWith(".png")) {
//                            mimeType = "image/png";
//                        } else if (suffix.endsWith(".jpeg")) {
//                            mimeType = "image/jpg";
//                        } else {
//                            mimeType = "text/html";
//                        }
//                        try {
//                            InputStream is = getAssets().open("offline_res/" + suffix);
//                            return new WebResourceResponse(mimeType, "UTF-8", is);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//                return super.shouldInterceptRequest(view, url);
//            }
//
//            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//            @Override
//            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
//                String url = request.getUrl().toString();
//                if (url.endsWith(".js") || url.endsWith(".css")) {
//                    String suffix = StringUtil.getFinalResource(url);
//                    if (offlineResources.contains(suffix)) {
//                        String mimeType;
//                        if (suffix.endsWith(".js")) {
//                            mimeType = "application/javascript";
//                        } else if (suffix.endsWith(".css")) {
//                            mimeType = "text/css";
//                        } else if (suffix.endsWith(".gif")) {
//                            mimeType = "image/gif";
//                        } else if (suffix.endsWith(".png")) {
//                            mimeType = "image/png";
//                        } else if (suffix.endsWith(".jpeg")) {
//                            mimeType = "image/jpg";
//                        } else {
//                            mimeType = "text/html";
//                        }
//                        try {
//                            InputStream is = getAssets().open("offline_res/" + suffix);
//                            return new WebResourceResponse(mimeType, "UTF-8", is);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//
//                return super.shouldInterceptRequest(view, request);
//            }
//        });


        MyWebViewClient myWebViewClient = new MyWebViewClient(this) {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.e("wzr_page", "start");
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        };
        webView.setWebViewClient(myWebViewClient);


        webView.loadUrl("http://192.168.111.20/html/treeView.html");

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.reload();
            }
        });
    }

    private void getBuild() {
        Log.e("wzr", "BOARD: " + Build.BOARD + "\nBOOTLOADER: " + Build.BOOTLOADER + "\nBRAND: " + Build.BRAND
                + "\nCPU_ABI: " + Build.CPU_ABI + "\nCPU_ABI2: " + Build.CPU_ABI2 + "\nDEVICE: " + Build.DEVICE
                + "\nDISPLAY: " + Build.DISPLAY + "\nFINGERPRINT: " + Build.FINGERPRINT + "\nHARDWARE: " + Build.HARDWARE
                + "\nHOST: " + Build.HOST + "\nID: " + Build.ID + "\nMANUFACTURER: " + Build.MANUFACTURER + "\nMODEL: " + Build.MODEL
                + "\nPRODUCT: " + Build.PRODUCT + "\nRADIO: " + Build.RADIO + "\nSERIAL: " + Build.SERIAL + "\nTAGS: " + Build.TAGS
                + "\nTIME: " + Build.TIME + "\nTYPE: " + Build.TYPE + "\nUSER: " + Build.USER + "\nSDK: " + Build.VERSION.SDK
                + "\nRELEASE: " + Build.VERSION.RELEASE + "\nBASE_OS: " + Build.VERSION.BASE_OS + "\nCODENAME: " + Build.VERSION.CODENAME
                + "\nINCREMENTAL: " + Build.VERSION.INCREMENTAL + "\nSECURITY_PATCH: " + Build.VERSION.SECURITY_PATCH
                + "\nSubscriberId: " + mTm.getSubscriberId());

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
            broadcastReceiver = null;
        }
    }

    private void getSourceInfo(String source_name) {
        Map<String, String> param = new HashMap<>();
        param.put("version", "1.0");
        param.put("source_name", source_name);
        DataPosts posts = new DataPosts(new ProgressSubscriber(new HttpOnNextListener<SourceMod>() {
            @Override
            public void onNext(SourceMod response) {
                if ("200".equals(response.getCode())) {
//                    try {
//                        Log.e("wzr_read", response.getData().getPackage_name() + ": " + MethodsUtil.md5sum(cacheFilePath + response.getData().getPackage_name()));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                    if ("0".equals(response.getData().getStrategy())) {
                        if (needUpdate(cacheFilePath + response.getData().getPackage_name(), response.getData().getMd5())) {
                    /*
                    0=全量更新
                    1=增量更新
                     */
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("sourceInfo", (Serializable) response.getData());
                            Intent intent = new Intent(MainActivity.this, DownloadService.class);
                            intent.putExtras(bundle);
                            intent.putExtra("path", cacheFilePath);
                            startService(intent);
                        }
                    } else {
                        if (needUpdate(sourcePath + response.getData().getPackage_name(), response.getData().getMd5())) {
                    /*
                    0=全量更新
                    1=增量更新
                     */
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("sourceInfo", (Serializable) response.getData());
                            Intent intent = new Intent(MainActivity.this, DownloadService.class);
                            intent.putExtras(bundle);
                            intent.putExtra("path", cacheFilePath);
                            startService(intent);
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, response.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        }, MainActivity.this), RequestCode.SOURCE_UPDATE_CODE, param);
        HttpManager manager = HttpManager.getInstance();
        manager.doPost(posts);
    }

    private boolean needUpdate(String filePath, String md5) {
        try {
            if (!MethodsUtil.isWifi(MainActivity.this)) {
                return false;
            }

            if (md5.equals(MethodsUtil.md5sum(filePath))) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
        return true;
    }

    private void fetchOfflineResources() {
        File mFile = new File(sourcePath);
        File[] files = mFile.listFiles();

        try {
            String[] res = new String[files.length];
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                res[i] = file.getPath();
            }

            if (res != null) {
                Collections.addAll(offlineResources, res);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
