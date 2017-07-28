package com.kevin.www.sharedemo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends CheckLoginActivity implements View.OnClickListener {


    private TextView textView1, textView2, textView3, textView4, textView5, textView6;
    private ShareEntity shareEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
        bindListener();
    }

    private void bindView() {
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView) findViewById(R.id.textView5);
        textView6 = (TextView) findViewById(R.id.textView6);
    }

    private void bindListener() {
        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        textView3.setOnClickListener(this);
        textView4.setOnClickListener(this);
        textView5.setOnClickListener(this);
        textView6.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView1:
                structShareBean("text");
                openShareSheet(shareEntity);
                break;
            case R.id.textView2:
                structShareBean("image");
                openShareSheet(shareEntity);
                break;
            case R.id.textView3:
                structShareBean("music");
                openShareSheet(shareEntity);
                break;
            case R.id.textView4:
                structShareBean("video");
                openShareSheet(shareEntity);
                break;
            case R.id.textView5:
                structShareBean("web");
                openShareSheet(shareEntity);
                break;
            case R.id.textView6:
                structShareBean("mini");
                openShareSheet(shareEntity);
                break;

        }
    }

    private void structShareBean(String type) {
        ShareEntity shareEntity = new ShareEntity();
        shareEntity.shareTitle = type + "类型数据分享";
        shareEntity.shareType = type;
        this.shareEntity = shareEntity;
    }
}
