package com.cyril.collection.post;


import com.cyril.collection.comconfig.AppConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by cyril on 2017/1/13.
 */
public class HttpManager {
    private volatile static HttpManager INSTANCE;

    ApiService service;

    private HttpManager() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .serializeNulls()
                .setLenient()
                .create();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                        .addHeader("Connection", "keep-alive")
//                        .addHeader("Accept-Encoding", "gzip")
                        .addHeader("User-Agent", "Dalvik/2.1.0 (Linux; U; Android 5.1.1; 2014813 MIUI/V7.5.2.0.LHJCNDE)")
                        .build();
                return chain.proceed(request);
            }
        }).connectTimeout(20, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(AppConfig.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        service = retrofit.create(ApiService.class);
    }

    public static HttpManager getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpManager();
                }
            }
        }
        return INSTANCE;
    }

    public void doPost(BaseEntity base) {
        try {
            Observable observable = base.getObservable(service)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(base);
            observable.subscribe(base.getSubscriber());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
