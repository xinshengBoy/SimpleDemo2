package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.qzs.voiceannouncementlibrary.VoiceUtils;
import com.yks.simpledemo2.R;
import com.yks.simpledemo2.widget.MyActionBar;

import net.lemonsoft.lemonbubble.LemonBubble;

/**
 * 描述：
 * 作者：
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
        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "支付宝语音播报");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

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
                VoiceUtils.with(AliPayVoice.this).Play(input,true);
            }
        }
    }
}
