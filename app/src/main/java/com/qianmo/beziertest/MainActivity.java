package com.qianmo.beziertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.qianmo.beziertest.view.MyView1;
import com.qianmo.beziertest.view.MyViewCircle;

public class MainActivity extends AppCompatActivity {
    private MyView1 myview;
    private Button btn;
    private MyViewCircle myViewCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        myview = (MyView1) findViewById(R.id.myview);
//        btn = (Button) findViewById(R.id.btn);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myview.setControlPointTwo(!myview.isControlPointTwo());
//            }
//        });

        myViewCircle = (MyViewCircle) findViewById(R.id.myview);

        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewCircle.setRuning(true);
            }
        });

    }
}
