package com.cyril.collection.dealImg;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyril.collection.R;
import com.cyril.collection.utils.PictureUtils;

/**
 * Created by cyril on 2017/8/6.
 */

public class PhotosynthActivity extends Activity {
    private ImageView picIV1, picIV2, resultIV;
    private TextView tv1, tv2;
    private Button okBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photosynth);
        initView();
    }

    private void initView() {
        picIV1 = (ImageView) findViewById(R.id.pic_1);
        picIV2 = (ImageView) findViewById(R.id.pic_2);
        resultIV = (ImageView) findViewById(R.id.result_img);
        tv1 = (TextView) findViewById(R.id.pic_attr_1);
        tv2 = (TextView) findViewById(R.id.pic_attr_2);

        final Bitmap b1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.loadingpage);
        final Bitmap b2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.zjmaxcode);

        picIV1.setImageBitmap(b1);
        picIV2.setImageBitmap(b2);
        tv1.setText("W:" + b1.getWidth() + "\nH:" + b1.getHeight());
        tv2.setText("W:" + b2.getWidth() + "\nH:" + b2.getHeight());

        findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultIV.setImageBitmap(PictureUtils.conformBitmap(b1, b2));
            }
        });
    }
}
