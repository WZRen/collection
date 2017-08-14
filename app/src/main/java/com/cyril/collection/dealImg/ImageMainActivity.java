package com.cyril.collection.dealImg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.cyril.collection.R;

/**
 * Created by cyril on 2017/8/6.
 */

public class ImageMainActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_main);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.photosynth:
                startActivity(new Intent(ImageMainActivity.this, PhotosynthActivity.class));
                break;
            case R.id.compression:
                startActivity(new Intent(ImageMainActivity.this, PicCompressionActivity.class));
                break;
        }
    }
}
