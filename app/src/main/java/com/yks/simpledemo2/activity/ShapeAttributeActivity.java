package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yks.simpledemo2.R;
import com.yks.simpledemo2.tools.Info;

/**
 * 描述：所有shape属性
 * 作者：zzh
 * time:2019/07/06
 * 参考网址：http://www.apkbus.com/blog-985982-80336.html
 */
public class ShapeAttributeActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_shape_attribute);

        Info.setActionBar(ShapeAttributeActivity.this,R.id.headerLayout,"所有shape属性");
    }
}
