package com.cyril.collection.post;


import com.cyril.collection.comconfig.AppConfig;
import com.cyril.collection.modules.Response;
import com.cyril.collection.modules.SourceMod;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by cyril on 2017/1/13.
 */
public interface ApiService {
    @FormUrlEncoded
    @POST(AppConfig.URL + "parserdata.php")
    Observable<Response> getResponse(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST(AppConfig.URL + "jsupdate.php")
    Observable<SourceMod> getSource(@FieldMap Map<String, String> param);

    @Multipart
    @POST(AppConfig.URL + "app.php")
    Observable<ResponseBody> uploadFiles(@Part() List<MultipartBody.Part> files);


    @FormUrlEncoded
    @POST(AppConfig.URL + "parserdata.php")
    Observable<Response> parserData(@FieldMap Map<String, String> param);
}
