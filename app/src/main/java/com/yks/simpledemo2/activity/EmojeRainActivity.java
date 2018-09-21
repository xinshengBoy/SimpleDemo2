package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.yks.simpledemo2.R;
import com.yks.simpledemo2.widget.MyActionBar;
import com.yks.simpledemo2.widget.RainView;

import net.lemonsoft.lemonhello.LemonHello;

/**
 * 描述：表情雨，仿微信的表情雨
 * 作者：作者：zzh
 * time:2018/09/20
 * 参考：https://github.com/taixiang/rain_emoji/blob/master/app/src/main/java/com/rain/MainActivity.java
 */

public class EmojeRainActivity extends Activity implements View.OnClickListener{

    private RainView view_emojerain;
    private EditText et_emojeCount;
    private Button btn_emojeOne,btn_emojeTwo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_emoje_rain);

        initView();
    }

    private void initView() {
        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "表情雨");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        view_emojerain = findViewById(R.id.view_emojerain);
        et_emojeCount = findViewById(R.id.et_emojeCount);
        btn_emojeOne = findViewById(R.id.btn_emojeOne);
        btn_emojeTwo = findViewById(R.id.btn_emojeTwo);

        btn_emojeOne.setOnClickListener(this);
        btn_emojeTwo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_emojeOne){
            start(R.mipmap.emoje1);
        }else if (view == btn_emojeTwo){
            start(R.mipmap.emoje2);
        }
    }

    private void start(int imgId){
        String num = et_emojeCount.getText().toString().trim();
        if (num.equals("")){
            LemonHello.getWarningHello("提示","请输入数字");
        }else {
            int count = Integer.parseInt(num);
            view_emojerain.setImgId(imgId, count);
            view_emojerain.start(true);
        }
    }
}
