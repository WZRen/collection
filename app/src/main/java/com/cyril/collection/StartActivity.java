package com.cyril.collection;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

import com.cyril.collection.tagclouddemo.TextTagsAdapter;
import com.cyril.collection.tagclouddemo.VectorTagsAdapter;
import com.cyril.collection.tagclouddemo.ViewTagsAdapter;
import com.cyril.collection.utils.PictureUtils;
import com.cyril.collection.widget.tagcloud.TagCloudView;

import java.lang.reflect.Field;

/**
 * Created by cyril on 2017/2/13.
 */
public class StartActivity extends AppCompatActivity {

    private Button btn;
    private WebView webView;
    private ImageView imageView;

    private TagCloudView tagCloudView;
    private TextTagsAdapter textTagsAdapter;
    private ViewTagsAdapter viewTagsAdapter;
    private VectorTagsAdapter vectorTagsAdapter;
    private String[] tags;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        initView();
    }

    private void initView() {
        //反射
        btn = (Button) findViewById(this.getResources().getIdentifier("tag_text", "id", this.getPackageName()));
        //反射
        try {
//            Field field = R.color.class.getField("colorPrimary");
//            int i = field.getInt(new R.color());
//            Log.e("wzr", i+"");
            Resources res = getResources();
            int color = res.getColor(res.getIdentifier("colorAccent", "color", getPackageName()));
            btn.setBackgroundColor(color);
//            btn3.setBackgroundColor(getResources().getIdentifier("colorAccent", "color", getPackageName()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        webView = (WebView) findViewById(R.id.webview);
        imageView = (ImageView) findViewById(R.id.image_view);

        Bitmap one = BitmapFactory.decodeResource(getResources(), R.drawable.loadingpage);
        Bitmap two = BitmapFactory.decodeResource(getResources(), R.drawable.bank_bhb);
        imageView.setImageBitmap(PictureUtils.conformBitmap(one, two));


//        webView.loadUrl("http://192.168.111.20/test.html");

        tags = new String[]{"JS缓存", "Add TabRow", "配置化", "获取颜色值", "图片处理", "异常捕获", "发送邮件", "扫码登录", "JsBridge", "上传", "Json类型", "数字递增动画", "备忘日历"};

        tagCloudView = (TagCloudView) findViewById(R.id.tag_cloud);
        tagCloudView.setBackgroundColor(Color.LTGRAY);

        textTagsAdapter = new TextTagsAdapter(tags);

        viewTagsAdapter = new ViewTagsAdapter();
        vectorTagsAdapter = new VectorTagsAdapter();

        tagCloudView.setAdapter(textTagsAdapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagCloudView.setAdapter(textTagsAdapter);
            }
        });

        findViewById(R.id.tag_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagCloudView.setAdapter(viewTagsAdapter);
            }
        });

        findViewById(R.id.tag_vector).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagCloudView.setAdapter(vectorTagsAdapter);
            }
        });
    }

    private int getID(String type) {
        try {
            Field field = R.drawable.class.getField(type);
            int i = field.getInt(new R.drawable());
            Log.e("wzr", "i: " + i);
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
