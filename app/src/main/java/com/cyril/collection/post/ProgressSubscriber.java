package com.cyril.collection.post;

import android.content.Context;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * Created by cyril on 2017/1/13.
 */
public class ProgressSubscriber<T> extends Subscriber<T> {
    private HttpOnNextListener httpOnNextListener;
    private WeakReference<Context> mActivity;
    private Context mContext;

    //是否取消请求
    private boolean isShow;
    //自定义加载框


    public ProgressSubscriber(HttpOnNextListener httpOnNextListener, Context context) {
        this.httpOnNextListener = httpOnNextListener;
        this.mActivity = new WeakReference<>(context);
        this.mContext = context;
        this.isShow = false;
        initProgressDialog();
    }


    public ProgressSubscriber(HttpOnNextListener httpOnNextListener, Context context, boolean isShow) {
        this.httpOnNextListener = httpOnNextListener;
        this.mActivity = new WeakReference<>(context);
        this.mContext = context;
        this.isShow = isShow;
        initProgressDialog();
    }

    private void initProgressDialog() {
        Context context = mActivity.get();
    }

    private void showProgressDialog() {
        Context context = mActivity.get();
    }

    private void dismissProgressDialog() {

    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        Context context = mActivity.get();
        if (context == null)
            return;
        if (e instanceof SocketTimeoutException || e instanceof ConnectException) {
            Toast.makeText(context, "当前网络不可用", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        e.printStackTrace();
        dismissProgressDialog();
    }

    @Override
    public void onNext(T t) {
        try {
            if (httpOnNextListener != null) {
                httpOnNextListener.onNext(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        showProgressDialog();
    }
}
