package com.cyril.collection.uncertaintyjson;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cyril.collection.R;
import com.cyril.collection.modules.Response;
import com.cyril.collection.post.DataPosts;
import com.cyril.collection.post.HttpManager;
import com.cyril.collection.post.HttpOnNextListener;
import com.cyril.collection.post.ProgressSubscriber;
import com.cyril.collection.post.RequestCode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cyril on 2017/7/10.
 */

public class DiffTypeOfJsonActivity extends Activity {
    private TextView dataTV;
    private EditText editText;
    private Button requestBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diff_json);
        initView();
    }

    private void initView() {
        dataTV = (TextView) findViewById(R.id.parser_data);
        editText = (EditText) findViewById(R.id.a_et);
        requestBtn = (Button) findViewById(R.id.request_data);
        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parserData(editText.getText().toString());
            }
        });

    }

    private void parserData(String uid) {
        Map<String, String> param = new HashMap<>();
        param.put("a", "get_users");
        param.put("uid", uid);
        DataPosts posts = new DataPosts(new ProgressSubscriber(new HttpOnNextListener<Response>() {
            @Override
            public void onNext(Response response) {
                dataTV.setText(response.getData().getUserInfo().getEmail());
            }
        }, this), RequestCode.PARSER_DATA_CODE, param);
        HttpManager manager = HttpManager.getInstance();
        manager.doPost(posts);
    }
}
