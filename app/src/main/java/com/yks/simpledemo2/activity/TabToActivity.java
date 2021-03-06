package com.yks.simpledemo2.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.yks.simpledemo2.R;
import com.yks.simpledemo2.tools.Info;

/**
 * 描述：TabHost跳转
 * 作者：zzh
 * time:2018/11/06
 */

public class TabToActivity extends TabActivity{

    public static TabHost tabHost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tabtoactivity);

        Info.setActionBar(this,R.id.headerLayout,"TabHost跳转");
        tabHost = getTabHost();
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("First").setContent(new Intent(this,FirstGroupTab.class)));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("Second").setContent(new Intent(this,SecondTab.class)));
        tabHost.setCurrentTab(0);
    }
}
