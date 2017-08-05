package com.cyril.collection.post;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by cyril on 2017/1/13.
 */
public abstract class BaseEntity<T> implements Func1<T, T> {

    public abstract Observable getObservable(ApiService methods);

    public abstract Subscriber getSubscriber();

    @Override
    public T call(T t) {
        return t;
    }
}
