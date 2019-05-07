package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;
import com.yks.simpledemo2.R;
import com.yks.simpledemo2.tools.Info;

/**
 * 描述：一款非常炫酷的TextView
 * 作者：zzh
 * 参考：https://www.jianshu.com/p/47df0b7b76be        https://github.com/hanks-zyh/HTextView
 * time:2019/05/06
 */
public class HTextViewActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_htextview);

        initView();
    }

    private void initView() {
        Info.setActionBar(HTextViewActivity.this,R.id.headerLayout,"HTextView");

        HTextView ht_scale = findViewById(R.id.ht_scale);
        ht_scale.setAnimateType(HTextViewType.SCALE);
        ht_scale.animateText("what can i do with it,site down");

        HTextView ht_evaporate = findViewById(R.id.ht_evaporate);
        ht_evaporate.setAnimateType(HTextViewType.EVAPORATE);
        ht_evaporate.animateText("Older people,steve jobs");

        HTextView ht_fall = findViewById(R.id.ht_fall);
        ht_fall.setAnimateType(HTextViewType.FALL);
        ht_fall.animateText("is how it works ? what is it?");

        HTextView ht_line = findViewById(R.id.ht_line);
        ht_line.setAnimateType(HTextViewType.LINE);
        ht_line.animateText("it's very hot,eating some iceteams ?");

        HTextView ht_sparkle = findViewById(R.id.ht_sparkle);
        ht_sparkle.setAnimateType(HTextViewType.SPARKLE);
        ht_sparkle.animateText("very well,continue,come on!");

        HTextView ht_anvil = findViewById(R.id.ht_anvil);
        ht_anvil.setAnimateType(HTextViewType.ANVIL);
        ht_anvil.animateText("the bird is fall,ok ?");
    }
}
