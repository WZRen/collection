package com.cyril.collection.codelogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.cyril.collection.R;
import com.cyril.collection.codelogin.zxing.activity.CaptureActivity;
import com.cyril.collection.utils.HttpUtils;


/**
 * Created by cyril on 2017/5/11.
 */

public class BCLoginActivity extends Activity implements View.OnClickListener{

    private EditText usernameET;

    private static final String URL = "http://192.168.111.20/BC_login/";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bc_login);

        usernameET = (EditText)findViewById(R.id.username);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            String randnumber = data.getExtras().getString("result");
            String username = usernameET.getText().toString();

            String url = URL + "saveUsername.php?randnumber="+randnumber+"&username=" +username;

            HttpUtils.login(url);
        }
    }
}
