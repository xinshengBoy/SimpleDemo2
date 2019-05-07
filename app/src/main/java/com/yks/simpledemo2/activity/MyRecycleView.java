package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yks.simpledemo2.R;

/**
 * 描述：recycleview适配器的封装和使用
 * 作者：zzh
 * time:2019/05/03
 */
public class MyRecycleView extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mycycleview);
    }
}
