package com.cyril.collection.sendmail;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cyril.collection.R;

/**
 * Created by cyril on 2017/5/10.
 */
public class SendMailActivity extends Activity {

    private Button sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);

        sendBtn = (Button) findViewById(R.id.sendMail);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new NewMail("测试", "测试邮件发送","","")).start();
            }
        });
    }
}
