package com.cyril.collection.downSource;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.cyril.collection.MainActivity;
import com.cyril.collection.comconfig.AppConfig;
import com.cyril.collection.utils.StringUtil;

import java.io.File;

import rx.Subscriber;

/**
 * Created by cyril on 2017/3/13.
 */
public class DownloadService2 extends IntentService {

    private static final String TAG = "DownloadApi2";

    private String url = "", sourceName = "", path = "";


    public DownloadService2() {
        super(TAG);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        url = intent.getStringExtra("url");
        sourceName = StringUtil.fetchSourceName(url);

        path = intent.getStringExtra("path");

        Log.e("wzr_download", url + "; " + sourceName);
        download();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
//        download();
    }

    private void download() {
        DownloadProgressListener listener = new DownloadProgressListener() {
            @Override
            public void update(long bytesRead, long contentLength, boolean done) {
                Download download = new Download();
                download.setTotalSize(contentLength);
                download.setCurrentSize(bytesRead);
                int progress = (int) ((bytesRead * 100) / contentLength);
                download.setProgress(progress);
                sendNotification(download);
            }
        };
        File file = null;
        file = new File(path + AppConfig.CACHE_FOLDER_NAME, sourceName);

        new DownloadHttp(StringUtil.getHostName(url), listener).downloadSource(url, file, new Subscriber() {
            @Override
            public void onCompleted() {
                downloadCompleted();
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                downloadCompleted();
            }

            @Override
            public void onNext(Object o) {

            }
        });
    }

    private void downloadCompleted() {
        Download download = new Download();
        download.setProgress(100);
        sendIntent(download);
    }

    private void sendNotification(Download download) {
        sendIntent(download);
    }

    private void sendIntent(Download download) {
        Intent intent = new Intent(MainActivity.MESSAGE_PROGRESS);
        intent.putExtra("download", download);
        LocalBroadcastManager.getInstance(DownloadService2.this).sendBroadcast(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {

    }
}
