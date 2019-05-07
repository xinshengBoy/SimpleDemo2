package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.king.view.supertextview.SuperTextView;
import com.yks.simpledemo2.R;
import com.yks.simpledemo2.tools.Info;

/**
 * 描述：打字效果，textview控件
 * 作者：zzh
 * time:2018/10/13
 * 参考网址：https://github.com/jenly1314/SuperTextView/
 */

public class TypingWordActivity extends Activity implements View.OnClickListener{

    private SuperTextView superText;
    private Button normal,writing,changecolor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_typing_word);

        initView();
    }

    private void initView() {
        Info.setActionBar(this,R.id.headerLayout,"打字效果");

        superText = findViewById(R.id.superText);
        normal = findViewById(R.id.btn_superText_normal);
        writing = findViewById(R.id.btn_superText_writing);
        changecolor = findViewById(R.id.btn_superText_changecolor);

        superText.setDynamicText("窗前明月光，疑是地上霜。\n举头望明月，低头思故乡。");

        normal.setOnClickListener(this);
        writing.setOnClickListener(this);
        changecolor.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == normal){
            changeStyle(SuperTextView.DynamicStyle.NORMAL);
        }else if (view == writing){
            changeStyle(SuperTextView.DynamicStyle.TYPEWRITING);
        }else if (view == changecolor){
            changeStyle(SuperTextView.DynamicStyle.CHANGE_COLOR);
        }
    }

    /**
     * 描述：点击按钮换风格
     * 作者：zzh
     * @param style 风格
     */
    private void changeStyle(SuperTextView.DynamicStyle style){
        superText.setDynamicStyle(style);
        superText.start();
    }
}
