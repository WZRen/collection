package com.cyril.collection.downSource;

import android.support.annotation.NonNull;

import com.cyril.collection.utils.FileUtil;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by cyril on 2017/1/17.
 */
public class DownloadHttp {
    private static final int DEFAULT_TIMEOUT = 15;
    private Retrofit retrofit;

    public DownloadHttp(String url, DownloadProgressListener listener) {
        DownloadProgressInterceptor interceptor = new DownloadProgressInterceptor(listener);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .client(client)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void downloadSource(@NonNull String url, final File file, Subscriber subscriber) {
        try {
            retrofit.create(DownloadApi.class)
                    .download(url)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .map(new Func1<ResponseBody, InputStream>() {
                        @Override
                        public InputStream call(ResponseBody responseBody) {
                            return responseBody.byteStream();
                        }
                    })
                    .observeOn(Schedulers.computation())
                    .doOnNext(new Action1<InputStream>() {
                        @Override
                        public void call(InputStream inputStream) {
                            try {
                                FileUtil.writeFile(inputStream, file);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
