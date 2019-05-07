package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;
import com.michaldrabik.tapbarmenulib.TapBarMenu;
import com.yks.simpledemo2.R;
import com.yks.simpledemo2.tools.Info;

/**
 * 描述：3D菜单
 * 参考：https://github.com/Hitomis/CircleMenu
 * 注意：最多添加八个菜单
 * Created by admin on 2018/3/2.
 */

public class Menu3D extends Activity {

    private TapBarMenu menu_tapbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_3d_menu);
        Info.setActionBar(Menu3D.this,R.id.headerLayout,"3D菜单");
        initView();
    }

    private void initView() {
        //tapbar菜单
        menu_tapbar = findViewById(R.id.menu_tapbar);
        menu_tapbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean is_opened = menu_tapbar.isOpened();
                if (is_opened){
                    menu_tapbar.close();
                }else {
                    menu_tapbar.toggle();
                }
            }
        });

//        menu_tapbar.onTouchEvent(new )


        //3D菜单
        CircleMenu menus = findViewById(R.id.menu_3d);
        menus.setMainMenu(0xffA59D9D,R.mipmap.file,R.mipmap.cancel)//设置主按键的背景颜色和图片（打开或关闭）
                .addSubMenu(0xffF50505,R.mipmap.homes)//添加一个子菜单项
                .addSubMenu(0xff8030F0,R.mipmap.file)
                .addSubMenu(0xff30A000,R.mipmap.location)
                .addSubMenu(0xff2080F0,R.mipmap.notice)
                .setOnMenuStatusChangeListener(new OnMenuStatusChangeListener() {
                    @Override
                    public void onMenuOpened() {

                    }

                    @Override
                    public void onMenuClosed() {

                    }
                }).setOnMenuSelectedListener(new OnMenuSelectedListener() {
            @Override
            public void onMenuSelected(int i) {
                Log.d("menus",i+"");
                //当前选中的是哪一个
            }
        });
    }
}
