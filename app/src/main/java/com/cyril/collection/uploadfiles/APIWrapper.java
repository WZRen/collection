package com.cyril.collection.uploadfiles;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by cyril on 2017/5/23.
 */

public class APIWrapper extends RetrofitUtil {
    private static APIWrapper apiWrapper;

    public APIWrapper() {
    }

    public static APIWrapper getInstance() {
        if (apiWrapper == null) {
            apiWrapper = new APIWrapper();
        }
        return apiWrapper;
    }

    public Observable<ResponseBody> uploadFiles(String a, Map<String, RequestBody> param) {
        return getUploadService().uploadFile(a, param);
    }
}
