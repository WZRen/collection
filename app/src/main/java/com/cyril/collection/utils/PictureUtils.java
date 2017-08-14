package com.cyril.collection.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by cyril on 2017/7/5.
 */

public class PictureUtils {

    //图片拼接 宽高都有处理
    public static Bitmap conformBitmap(Bitmap bitmapOne, Bitmap bitmapTwo) {

        int widthOne = bitmapOne.getWidth();
        int heightOne = bitmapOne.getHeight();
        int widthTwo = bitmapTwo.getWidth();
        int heightTwo = bitmapTwo.getHeight();

        Bitmap bitmap = Bitmap.createBitmap(widthOne, heightOne + heightTwo / 2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();
        // 绘制矩形区域-实心矩形
        // 设置颜色
        paint.setColor(Color.WHITE);
        // 设置样式-填充
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(new Rect(0, 0, widthOne, heightOne + heightTwo / 2), paint);

        Rect dst = new Rect();
        dst.left = 0;
        dst.top = 0;
        dst.right = widthOne;
        dst.bottom = heightOne;
        canvas.drawBitmap(bitmapOne, null, dst, null);

        Rect dst2 = new Rect();

        dst2.left = widthOne - widthTwo / 2;
        dst2.top = heightOne;
        dst2.right = (int) widthOne;
        dst2.bottom = (int) (heightOne + heightTwo / 2);

        canvas.drawBitmap(bitmapTwo, null, dst2, null);

        dst2 = null;
        dst = null;
        paint = null;

        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();

        return bitmap;
    }

    public static String saveBitmap(Bitmap bitmap, String path) {
//        String imagePath= getApplication().getFilesDir().getAbsolutePath()+"/temp.jpg";
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
    }

}
