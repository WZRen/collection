package com.cyril.collection.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by cyril on 2017/7/5.
 */

public class PictureUtils {
    public static Bitmap conformBitmap(Bitmap bitmapOne, Bitmap bitmapTwo) {
        int widthOne = bitmapOne.getWidth();
        int heightOne = bitmapOne.getHeight();
        int widthTwo = bitmapTwo.getWidth();
        int heightTwo = bitmapTwo.getHeight();


        Bitmap bitmap = Bitmap.createBitmap(widthOne + widthTwo, heightOne + heightTwo, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Rect dst = new Rect();
        dst.left = 0;
        dst.top = 0;
        dst.right = widthOne;
        dst.bottom = heightOne;
        canvas.drawBitmap(bitmapOne, null, dst, null);

        Rect dst2 = new Rect();
        dst2.left = 0;
        dst2.top = heightOne;
        double xScale = widthOne / widthTwo;
        dst2.bottom = (int) (heightOne + heightTwo * xScale);
        dst2.right = widthOne;

        canvas.drawBitmap(bitmapTwo, null, dst2, null);

        dst2 = null;
        dst = null;

        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();

        return bitmap;
    }
}
