package com.cyril.collection.uploadfiles;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import rx.Observable;

/**
 * Created by cyril on 2017/5/23.
 */

public interface UploadService {
    @Multipart
    @POST("v1/public/core/?service=user.updateAvatar")
    Observable<ResponseBody> uploadFile(@Part("a") String a, @PartMap Map<String, RequestBody> params);
}
