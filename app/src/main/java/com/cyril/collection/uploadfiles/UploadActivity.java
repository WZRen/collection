package com.cyril.collection.uploadfiles;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;

import com.cyril.collection.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by cyril on 2017/5/23.
 */

public class UploadActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
    }

    public void uploadFiles() {
        String path1 = Environment.getExternalStorageDirectory() + File.separator + "test.png";
        String path2 = Environment.getExternalStorageDirectory() + File.separator + "test.jpg";
        ArrayList<String> pathList = new ArrayList<>();
        pathList.add(path1);
        pathList.add(path2);

        Map<String, RequestBody> bodyMap = new HashMap<>();
        if (pathList.size() > 0) {
            for (int i = 0; i < pathList.size(); i++) {
                File file = new File(pathList.get(i));
                bodyMap.put("file" + i + "\"; filename=\"" + file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
            }
        }
        APIWrapper.getInstance().uploadFiles("226", bodyMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {

                    }
                })
        ;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.upload:
                uploadFiles();
                break;
        }
    }
}
