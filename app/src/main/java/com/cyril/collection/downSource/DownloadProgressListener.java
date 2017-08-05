package com.cyril.collection.downSource;

/**
 * Created by cyril on 2017/1/17.
 */
public interface DownloadProgressListener {
    void update(long bytesRead, long contentLength, boolean done);
}
