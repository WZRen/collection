package com.cyril.collection.downSource;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cyril on 2017/1/17.
 */
//下载进度数据
public class Download implements Parcelable {

    private int progress;
    private long currentSize;
    private long totalSize;

    public Download() {
    }

    public Download(Parcel in) {
        this.progress = in.readInt();
        this.currentSize = in.readLong();
        this.totalSize = in.readLong();
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public long getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(long currentSize) {
        this.currentSize = currentSize;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.progress);
        parcel.writeLong(this.currentSize);
        parcel.writeLong(this.totalSize);
    }

    public static final Parcelable.Creator<Download> CREATOR = new Parcelable.Creator<Download>() {
        @Override
        public Download createFromParcel(Parcel parcel) {
            return new Download(parcel);
        }

        @Override
        public Download[] newArray(int i) {
            return new Download[i];
        }
    };

}
