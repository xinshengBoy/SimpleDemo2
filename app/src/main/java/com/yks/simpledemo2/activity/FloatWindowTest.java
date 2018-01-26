package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.yhao.floatwindow.FloatWindow;
import com.yhao.floatwindow.MoveType;
import com.yhao.floatwindow.Screen;
import com.yks.simpledemo2.R;

/**
 * 描述：悬浮框
 * https://github.com/yhaolpz/FloatWindow
 * Created by admin on 2018/1/26.
 */

public class FloatWindowTest extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getApplicationContext(),"我进来了",Toast.LENGTH_LONG).show();
        ImageView img = new ImageView(getApplicationContext());
        img.setImageResource(R.mipmap.home);

        FloatWindow.with(getApplicationContext())
                .setView(img)
                .setWidth(Screen.width,0.2f)
                .setHeight(Screen.height,0.2f)
                .setFilter(true,FloatWindowTest.class)//指定页面显示
                .setDesktopShow(true)//桌面显示
                .setMoveType(MoveType.slide)//可拖动，释放后自动贴边
                .setMoveStyle(500,new AccelerateInterpolator())//贴边动画
                .build();
    }
}
