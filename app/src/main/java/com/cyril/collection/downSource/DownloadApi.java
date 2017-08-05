package com.cyril.collection.downSource;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by cyril on 2017/1/17.
 */
public interface DownloadApi {
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);
}
