package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yks.simpledemo2.R;
import com.yks.simpledemo2.tools.Info;

import net.lemonsoft.lemonbubble.LemonBubble;

import king.bird.spiderlib.SpiderUtil;

/**
 * 描述：爬虫
 * 作者：zzh
 * time:2018/11/01
 * 参考：http://www.wanandroid.com/blog/show/2367
 */

public class ReptilianActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_reptilian);

        Info.setActionBar(ReptilianActivity.this,R.id.headerLayout,"爬虫");

        EditText et_reptilian = findViewById(R.id.et_reptilian);
        Button btn_reptilians = findViewById(R.id.btn_reptilians);
        TextView txt_reptilian = findViewById(R.id.txt_reptilian);

        et_reptilian.setText("http://www.mzitu.com/tag/ugirls/");

        btn_reptilians.setOnClickListener(view -> {
            LemonBubble.showRoundProgress(ReptilianActivity.this,"加载中...");
            String input = et_reptilian.getText().toString();
            if (input.equals("")){
                LemonBubble.showError(ReptilianActivity.this,"请输入网址");
            }else {
                SpiderUtil.Companion.getImageByUrl(input, hashSet -> {
                    String a = hashSet.toString();
                    txt_reptilian.setText(a);
                    LemonBubble.hide();
                });
            }
        });

    }
}
