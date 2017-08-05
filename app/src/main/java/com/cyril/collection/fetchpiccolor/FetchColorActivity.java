package com.cyril.collection.fetchpiccolor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.cyril.collection.R;

/**
 * Created by cyril on 2017/2/22.
 */
public class FetchColorActivity extends AppCompatActivity {

    private EditText xET, yET;
    private Button startBtn;
    private ImageView iconIV, displayIV;
    private int[] icons = {R.drawable.bank_bhb, R.drawable.bank_ceb, R.drawable.bank_cgnb,
            R.drawable.bank_cmbc, R.drawable.bank_cqbank, R.drawable.bank_cscb, R.drawable.bank_dyccb,
            R.drawable.bank_egbank, R.drawable.bank_fxcb, R.drawable.bank_hzcb};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_color);
        initView();
    }

    private void initView() {
        xET = (EditText) findViewById(R.id.x);
        yET = (EditText) findViewById(R.id.y);

        startBtn = (Button) findViewById(R.id.startBtn);
        iconIV = (ImageView) findViewById(R.id.icon_image);
        displayIV = (ImageView) findViewById(R.id.display_image);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x = Integer.parseInt(xET.getText().toString());
                int y = Integer.parseInt(yET.getText().toString());
                startFetch(x, y);
            }
        });
    }

    private void startFetch(int x, int y) {
        iconIV.setImageResource(icons[x]);
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), icons[x], options);
            x = bitmap.getWidth();
            y = bitmap.getHeight();
            int count = 0, total = 0;
            int[] rgb = new int[3];
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    total++;
                    int rgbPixel = bitmap.getPixel(i, j);
                    if ((Color.red(rgbPixel) == 255 || Color.red(rgbPixel) == 0) &&
                            (Color.green(rgbPixel) == 255 || Color.green(rgbPixel) == 0) &&
                            (Color.blue(rgbPixel) == 255 || Color.blue(rgbPixel) == 0)) {

                    } else {
                        count++;
                        rgb[0] += (rgbPixel & 0xff0000) >> 16;
                        rgb[1] += (rgbPixel & 0xff00) >> 8;
                        rgb[2] += (rgbPixel & 0xff);
                    }
                }
            }

            Log.e("wzr", "x: " + x + "\ny: " + y + "\ntotal: " + total + "\ncount: " + count + "\nred: " + Integer.toHexString(rgb[0] / count) + "\n" + "green: " + Integer.toHexString(rgb[1] / count) + "\n" + "blue: " + Integer.toHexString(rgb[2] / count));
            displayIV.setBackgroundColor(Color.parseColor("#" + Integer.toHexString(rgb[0] / count) + Integer.toHexString(rgb[1] / count) + Integer.toHexString(rgb[2] / count)));
//            Log.e("Value", "pixel: " + Integer.toHexString(rgbPixel));
//            Log.e("Value", "rgb: r---" + Color.red(rgbPixel) + "  g-- " + Color.green(rgbPixel) + " b--" + Color.blue(rgbPixel));
//            displayIV.setBackgroundColor(Color.parseColor("#" + Integer.toHexString(rgbPixel)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
