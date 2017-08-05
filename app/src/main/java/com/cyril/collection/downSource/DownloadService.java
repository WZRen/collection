package com.cyril.collection.downSource;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.cyril.collection.MainActivity;
import com.cyril.collection.comconfig.AppConfig;
import com.cyril.collection.modules.SourceInfo;
import com.cyril.collection.utils.FileUtil;
import com.cyril.collection.utils.MethodsUtil;
import com.cyril.collection.utils.StringUtil;

import java.io.File;

import rx.Subscriber;

/**
 * Created by cyril on 2017/1/17.
 */
public class DownloadService extends IntentService {

    private static final String TAG = "DownloadApi";

    private String url = "", sourceName = "", path = "", strategy = "", md5 = "";

    private SourceInfo sourceInfo;

    public DownloadService() {
        super(TAG);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        sourceInfo = (SourceInfo) intent.getSerializableExtra("sourceInfo");
        url = sourceInfo.getSrc_url();
        sourceName = sourceInfo.getPackage_name();
        strategy = sourceInfo.getStrategy();
        md5 = sourceInfo.getMd5();

        path = intent.getStringExtra("path");

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
        if ("0".equals(strategy)) {
            file = new File(path, sourceName);
        } else {
            file = new File(path + AppConfig.CACHE_FOLDER_NAME, sourceName);
        }
        new DownloadHttp(StringUtil.getHostName(url), listener).downloadSource(url, file, new Subscriber() {
            @Override
            public void onCompleted() {
                try {
                    if ("0".equals(strategy)) {
                        if (md5.equals(MethodsUtil.md5sum(path + sourceName))) {
                            FileUtil.unZipFolder(path + sourceName, path);
                        } else {
                            FileUtil.deleteFolder(path + sourceName);
                        }
                    } else {
                        if (md5.equals(MethodsUtil.md5sum(path + AppConfig.CACHE_FOLDER_NAME + File.separator + sourceName))) {

                        } else {
                            FileUtil.deleteFolder(path + AppConfig.CACHE_FOLDER_NAME + File.separator + sourceName);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
        LocalBroadcastManager.getInstance(DownloadService.this).sendBroadcast(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {

    }
}
