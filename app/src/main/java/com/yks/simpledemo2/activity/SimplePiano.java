package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.yks.simpledemo2.R;
import com.yks.simpledemo2.tools.Info;

import net.lemonsoft.lemonhello.LemonHello;

/**
 * 描述：简易钢琴
 * 作者：zzh
 * time:2018/09/19
 */

public class SimplePiano extends Activity implements View.OnClickListener{

    private MediaPlayer player;
    private Button btn_piano1,btn_piano2,btn_piano3,btn_piano4,btn_piano5,btn_piano6,btn_piano7,btn_piano8;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_simple_piano);

        player = new MediaPlayer();

        initView();
    }

    private void initView() {
        Info.setActionBar(this,R.id.headerLayout,"简易钢琴");

        btn_piano1 = findViewById(R.id.btn_piano1);
        btn_piano2 = findViewById(R.id.btn_piano2);
        btn_piano3 = findViewById(R.id.btn_piano3);
        btn_piano4 = findViewById(R.id.btn_piano4);
        btn_piano5 = findViewById(R.id.btn_piano5);
        btn_piano6 = findViewById(R.id.btn_piano6);
        btn_piano7 = findViewById(R.id.btn_piano7);
        btn_piano8 = findViewById(R.id.btn_piano8);

        btn_piano1.setOnClickListener(this);
        btn_piano2.setOnClickListener(this);
        btn_piano3.setOnClickListener(this);
        btn_piano4.setOnClickListener(this);
        btn_piano5.setOnClickListener(this);
        btn_piano6.setOnClickListener(this);
        btn_piano7.setOnClickListener(this);
        btn_piano8.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == btn_piano1){
            play(R.raw.white1);
        }else if (view == btn_piano2){
            play(R.raw.white2);
        }else if (view == btn_piano3){
            play(R.raw.white3);
        }else if (view == btn_piano4){
            play(R.raw.white4);
        }else if (view == btn_piano5){
            play(R.raw.white5);
        }else if (view == btn_piano6){
            play(R.raw.white6);
        }else if (view == btn_piano7){
            play(R.raw.white7);
        }else if (view == btn_piano8){
            play(R.raw.white8);
        }
    }

    private void play(int resource){
        try {
            player.release();
            player = MediaPlayer.create(SimplePiano.this,resource);
            player.start();
        }catch (Exception e){
            LemonHello.getErrorHello("错误",e.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null){
            player.release();
            player = null;
        }
    }
}
