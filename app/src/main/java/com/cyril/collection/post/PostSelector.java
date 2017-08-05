package com.cyril.collection.post;

import java.util.Map;

import rx.Observable;


public class PostSelector {
    private ApiService service;
    private int code;
    private Map<String, String> params;
    private String header;

    public PostSelector(ApiService service, int code, Map<String, String> params) {
        this.service = service;
        this.code = code;
        this.params = params;
    }

    public PostSelector(ApiService service, int code, String header, Map<String, String> params) {
        this.service = service;
        this.code = code;
        this.header = header;
        this.params = params;
    }

    public Observable getPost() {
        Observable observable = null;
        switch (code) {
            case RequestCode.SOURCE_UPDATE_CODE:
                observable = service.getSource(params);
                break;
            default:
                observable = service.getResponse(params);
                break;
        }
        return observable;
    }
}
