package com.cyril.collection.widget.tagcloud;

import android.graphics.Color;
import android.view.View;

/**
 * Created by cyril on 2017/8/4.
 */

public class Tag {
    private int popularity;
    private float locX, locY, locZ;
    private float loc2DX, loc2DY;
    private float scale;
    private float[] argb;
    private static final int DEFAULT_POPULARITY = 5;
    private View mView;

    public Tag() {
        this(0f, 0f, 0f, 1.0f, 0);
    }

    public Tag(int popularity) {
        this(0f, 0f, 0f, 1.0f, popularity);
    }

    public Tag(float locX, float locY, float locZ) {
        this(locX, locY, locZ, 1.0f, DEFAULT_POPULARITY);
    }

    public Tag(float locX, float locY, float locZ, float scale) {
        this(locX, locY, locZ, scale, DEFAULT_POPULARITY);
    }

    public Tag(float locX, float locY, float locZ, float scale, int popularity) {
        this.locX = locX;
        this.locY = locY;
        this.locZ = locZ;

        this.loc2DX = 0;
        this.loc2DY = 0;

        this.argb = new float[]{1.0f, 0.5f, 0.5f, 0.5f};
        this.scale = scale;
        this.popularity = popularity;
    }

    public float getLocX() {
        return locX;
    }

    public void setLocX(float locX) {
        this.locX = locX;
    }

    public float getLocY() {
        return locY;
    }

    public void setLocY(float locY) {
        this.locY = locY;
    }

    public float getLocZ() {
        return locZ;
    }

    public void setLocZ(float locZ) {
        this.locZ = locZ;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public float getLoc2DX() {
        return loc2DX;
    }

    public void setLoc2DX(float loc2DX) {
        this.loc2DX = loc2DX;
    }

    public float getLoc2DY() {
        return loc2DY;
    }

    public void setLoc2DY(float loc2DY) {
        this.loc2DY = loc2DY;
    }

    public View getView() {
        return mView;
    }

    public void setView(View view) {
        this.mView = view;
    }

    public void setAlpha(float alpha) {
        this.argb[0] = alpha;
    }

    public void setColorByArray(float[] rgb) {
        if (rgb != null) {
            System.arraycopy(rgb, 0, this.argb, this.argb.length - rgb.length, rgb.length);
        }
    }

    public int getColor() {
        int[] result = new int[4];
        for (int i = 0; i < 4; i++) {
            result[i] = (int) (this.argb[i] * 0xff);
        }
        return Color.argb(result[0], result[1], result[2], result[3]);
    }
}
