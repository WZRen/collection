package com.cyril.collection.addtabrow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.cyril.collection.R;


/**
 * Created by cyril on 2017/2/13.
 */
public class AddTableRowActivity extends AppCompatActivity implements View.OnClickListener {

    private Button resetBtn, addBtn;
    private TableLayout tableLayout;

    private int orders = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tablerow);
        initView();
    }

    private void initView() {
        resetBtn = (Button) findViewById(R.id.btnReset);
        addBtn = (Button) findViewById(R.id.btnAdd);
        tableLayout = (TableLayout) findViewById(R.id.dictTable);

        resetBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnReset:
                orders = 0;
                tableLayout.removeAllViews();
                break;
            case R.id.btnAdd:
                if (orders == 0) {
                    addHeader();
                }
                addNewRow();
                break;
        }
    }

    private void addHeader() {
        TableRow row = new TableRow(this);

        LinearLayout.LayoutParams lp = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 5, 0);

        TextView text1 = new TextView(this);
        text1.setLayoutParams(lp);
        text1.setText("序号");
        text1.setGravity(Gravity.CENTER);
        text1.setPadding(10, 5, 5, 10);
        text1.setBackgroundColor(ContextCompat.getColor(AddTableRowActivity.this, android.R.color.holo_blue_light));


        TextView text2 = new TextView(this);
        text2.setLayoutParams(lp);
        text2.setText("字典名称");
        text2.setGravity(Gravity.CENTER);
        text2.setPadding(10, 5, 5, 10);
        text2.setBackgroundColor(ContextCompat.getColor(AddTableRowActivity.this, android.R.color.holo_green_light));


        TextView oper = new TextView(this);
        oper.setText("操作");
        oper.setGravity(Gravity.CENTER);
        oper.setPadding(10, 5, 5, 10);
        oper.setBackgroundColor(ContextCompat.getColor(AddTableRowActivity.this, android.R.color.holo_red_light));

        row.addView(text1);

        row.addView(text2);

        row.addView(oper);

        tableLayout.addView(row);
    }

    private void addNewRow() {
        TableRow row = new TableRow(this);
        TableLayout.LayoutParams params = new TableLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 5, 0, 0);
        row.setLayoutParams(params);
        row.setId(0);
        orders++;


        LinearLayout.LayoutParams lp = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 5, 0);
        TextView text1 = new TextView(this);
        text1.setLayoutParams(lp);
        text1.setText(String.valueOf(orders));
        text1.setGravity(Gravity.CENTER);
        text1.setPadding(10, 5, 5, 10);
        text1.setBackgroundColor(ContextCompat.getColor(AddTableRowActivity.this, android.R.color.holo_blue_light));

        TextView text2 = new TextView(this);
        text2.setLayoutParams(lp);
        text2.setText("item");
        text2.setGravity(Gravity.CENTER);
        text2.setPadding(10, 5, 5, 10);
        text2.setBackgroundColor(ContextCompat.getColor(AddTableRowActivity.this, android.R.color.holo_green_light));


        TextView oper = new TextView(this);
        oper.setText("删除");
        oper.setGravity(Gravity.CENTER);
        oper.setPadding(10, 5, 5, 10);
        oper.setBackgroundColor(ContextCompat.getColor(AddTableRowActivity.this, android.R.color.holo_red_light));
        oper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tableLayout.removeView(tableLayout.findViewById(0));
            }
        });


        row.addView(text1);
        row.addView(text2);
        row.addView(oper);

        tableLayout.addView(row);
    }
}
