package com.cyril.collection.post;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by cyril on 2017/1/13.
 */
public class DataPosts extends BaseEntity {
    private Subscriber subscriber;
    private int code;
    private Map<String, String> params;
    private String header;


    public DataPosts(Subscriber subscriber, int code) {
        this.subscriber = subscriber;
        this.code = code;
        params = new HashMap<>();
    }

    public DataPosts(Subscriber subscriber, int code, Map<String, String> params) {
        this.subscriber = subscriber;
        this.code = code;
        this.params = params;
    }

    public DataPosts(Subscriber subscriber, int code, String header, Map<String, String> params) {
        this.subscriber = subscriber;
        this.code = code;
        this.header = header;
        this.params = params;
    }



    @Override
    public Observable getObservable(ApiService methods) {
        PostSelector selector;
        if (header != null) {
            selector = new PostSelector(methods, code, header, params);
        } else {
            selector = new PostSelector(methods, code, params);
        }
        return selector.getPost();
    }

    @Override
    public Subscriber getSubscriber() {
        return subscriber;
    }
}
