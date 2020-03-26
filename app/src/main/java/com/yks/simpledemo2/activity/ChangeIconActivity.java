package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.yks.simpledemo2.R;
import com.yks.simpledemo2.tools.Info;

/**
 * 描述：动态更改桌面图标
 * 作者：zzh
 * 参考网址：http://www.apkbus.com/blog-822719-78065.html
 * time:2018/08/24
 */

public class ChangeIconActivity extends Activity implements View.OnClickListener{

    private Context mContext = ChangeIconActivity.this;

    private Button btn_changeicon_11,btn_changeicon_818,btn_changeicon_init;
    private ComponentName mDefault,mDouble11,mDouble818;
    private PackageManager manager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_change_icon);

        initView();
    }

    private void initView() {
        Info.setActionBar(ChangeIconActivity.this,R.id.headerLayout,"更换图标");

        btn_changeicon_init = findViewById(R.id.btn_changeicon_init);
        btn_changeicon_11 = findViewById(R.id.btn_changeicon_11);
        btn_changeicon_818 = findViewById(R.id.btn_changeicon_818);

        btn_changeicon_init.setOnClickListener(this);
        btn_changeicon_11.setOnClickListener(this);
        btn_changeicon_818.setOnClickListener(this);

        mDefault = getComponentName();
        mDouble11 = new ComponentName(getBaseContext(),"com.yks.simpledemo2.Test11");
        mDouble818 = new ComponentName(getBaseContext(),"com.yks.simpledemo2.Test818");

        manager = getApplicationContext().getPackageManager();
    }

    @Override
    public void onClick(View view) {
        if (view == btn_changeicon_init){
            initIcon();
            Info.playRingtone(mContext,true);
        }else if (view == btn_changeicon_11){
            changeIcon11();
            Info.playRingtone(mContext,true);
        }else if (view == btn_changeicon_818){
            changeIcon818();
            Info.playRingtone(mContext,true);
        }
    }

    private void initIcon() {
        disableComponent(mDouble11);
        disableComponent(mDouble818);
        enableComponent(mDefault);
    }

    private void changeIcon11() {
        disableComponent(mDefault);
        disableComponent(mDouble818);
        enableComponent(mDouble11);
    }

    private void changeIcon818() {
        disableComponent(mDefault);
        disableComponent(mDouble11);
        enableComponent(mDouble818);
    }

    /**
     * 描述：设置当前可用的
     * 作者：zzh
     * @param name 名称
     */
    private void enableComponent(ComponentName name){
        manager.setComponentEnabledSetting(name,PackageManager.COMPONENT_ENABLED_STATE_ENABLED,PackageManager.DONT_KILL_APP);
    }
    /**
     * 描述：移除之前的
     * 作者：zzh
     * @param name 名称
     */
    private void disableComponent(ComponentName name){
        manager.setComponentEnabledSetting(name,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,PackageManager.DONT_KILL_APP);
    }
}
