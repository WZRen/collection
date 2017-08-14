package com.cyril.collection.dealImg;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyril.collection.R;
import com.cyril.collection.utils.FileSizeUtil;
import com.cyril.collection.utils.FileUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cyril on 2017/5/8.
 */
public class PicCompressionActivity extends Activity {

    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_compression);
        imageView = (ImageView) findViewById(R.id.image);
        textView = (TextView) findViewById(R.id.textView);

        ImageFactory factory = new ImageFactory();
        Resources res = getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.pic_wel);

        //将图片压缩并保持在本地存储
//        try {
//            factory.compressAndGenImage(bitmap, FileUtil.getSourceCache(this)+"loading.jpg", 1024);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        //按图片尺寸压缩
//        Bitmap compressBmp = factory.ratio(bitmap, 120f, 240f);
//        Bitmap compressBmp = factory.ratio(FileUtil.getSourceCache(this)+"loading.jpg", 120f, 240f);
        //质量压缩
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        Bitmap compressBmp = factory.compressImage(bitmap);
        Date endDate = new Date(System.currentTimeMillis());
        long diff = endDate.getTime() - curDate.getTime();
        Log.e("wzr_time", diff + "");
        try {
            factory.storeImage(compressBmp, FileUtil.getSourceCache(this) + "loading.jpg");
            Log.e("wzr_size", FileSizeUtil.getFileOrFilesSize(FileUtil.getSourceCache(this) + "loading.jpg", 2)+"KB");
        } catch (Exception e) {
            e.printStackTrace();
        }


        //将bitmap转换为字符串
        String imgStr = factory.convertIconToString(compressBmp);
        Log.e("wzr", imgStr);
        //textView.setText(imgStr);

        //bitmap转drawable
        //Drawable drawable =new BitmapDrawable(bitmap);

        //将字符串转化为bitmap
//        imageView.setImageBitmap(factory.convertStringToIcon(imgStr));

        //Compress image by size

        imageView.setImageBitmap(compressBmp);

    }
}
