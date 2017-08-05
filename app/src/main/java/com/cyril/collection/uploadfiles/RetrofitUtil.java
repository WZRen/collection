package com.cyril.collection.uploadfiles;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cyril on 2017/5/23.
 */

public class RetrofitUtil {
    private static final String API_HOST = "http://api.stay4it.com/";
    private static Retrofit retrofit;
    private static UploadService uploadService;

    private static Retrofit getRetrofit() {
        if (retrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = builder.addInterceptor(loggingInterceptor).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(API_HOST)
                    .addConverterFactory(GsonConverterFactory.create(buildGson()))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    public static UploadService getUploadService() {
        if (uploadService == null) {
            uploadService = getRetrofit().create(UploadService.class);
        }
        return uploadService;
    }

    public static Gson buildGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .registerTypeAdapter(Integer.class, new IntegerDefaultAdapter())
                .registerTypeAdapter(int.class, new IntegerDefaultAdapter())
                .create();
    }

}
