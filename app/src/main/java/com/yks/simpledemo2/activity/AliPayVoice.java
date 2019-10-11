package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.qzs.voiceannouncementlibrary.VoiceUtils;
import com.yks.simpledemo2.R;
import com.yks.simpledemo2.tools.Info;

import net.lemonsoft.lemonbubble.LemonBubble;

/**
 * 描述：支付宝语音播报
 * 作者：zzh
 * time:2018/09/28
 */

public class AliPayVoice extends Activity implements View.OnClickListener{

    private EditText et_input;
    private Button btn_speak;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_alipay_voice);
        initView();
    }

    private void initView() {
        Info.setActionBar(AliPayVoice.this,R.id.headerLayout,"支付宝语音播报");

        et_input = findViewById(R.id.et_alipay_input);
        btn_speak = findViewById(R.id.btn_alipay_speak);

        btn_speak.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_speak){
            String input = et_input.getText().toString().trim();
            if (input.equals("")){
                LemonBubble.showError(AliPayVoice.this,"请输入金额",2000);
            }else {
                Info.hideKeyboard(AliPayVoice.this,et_input);
                VoiceUtils.with(AliPayVoice.this).Play(input,true);
            }
        }
    }
}
