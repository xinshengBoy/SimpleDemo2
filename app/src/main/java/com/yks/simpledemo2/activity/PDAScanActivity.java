package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.yks.simpledemo2.R;
import com.yks.simpledemo2.tools.Info;

import net.lemonsoft.lemonbubble.LemonBubble;

/**
 * 描述：
 * 作者：
 * time:2018/09/07
 */

public class PDAScanActivity extends Activity {

    private EditText et_pdascan;
    private String scanResult = "";
    private final int SCANSUCCESS = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pdascan);

        IntentFilter filter = new IntentFilter();
        filter.addAction("com.symbol.scanconfig.SCANDEMO");
        registerReceiver(receiver,filter);

        initView();
    }

    private void initView() {
        Info.setActionBar(PDAScanActivity.this,R.id.headerLayout,"PDA扫描");

        et_pdascan = findViewById(R.id.et_pdascan);
    }

    private final Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == SCANSUCCESS){
                if (!scanResult.equals("")){
                    et_pdascan.setText(scanResult);
                    LemonBubble.showRight(PDAScanActivity.this,scanResult+"已扫描");
                }
            }
        }
    };

    /**
     * 描述：获取扫描结果
     * 作者：zzh
     */
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra("data")) {
                scanResult = intent.getStringExtra("data");
                handler.sendEmptyMessage(SCANSUCCESS);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
