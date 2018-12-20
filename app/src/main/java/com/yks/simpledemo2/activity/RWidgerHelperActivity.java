package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.yks.simpledemo2.R;
import com.yks.simpledemo2.widget.MyActionBar;

/**
 * 描述：代替selector，各个state状态背景/边框/文字变色 RWidgetHelper
 * 作者：zzh
 * time:2018/10/15
 * 参考：https://github.com/RuffianZhong/RWidgetHelper/
 */

public class RWidgerHelperActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_r_helper);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "基础控件定义");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);
    }
}
