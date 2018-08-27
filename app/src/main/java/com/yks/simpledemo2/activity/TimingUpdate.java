package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yks.simpledemo2.R;
import com.yks.simpledemo2.widget.MyActionBar;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * 定时更新页面的内容
 * Created by admin on 2018/5/28.
 */

public class TimingUpdate extends Activity{

    private TextView txt_timing_update1,txt_timing_update2,txt_timing_update3;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_timing_update);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "定时更新");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        txt_timing_update1 = findViewById(R.id.txt_timing_update1);
        txt_timing_update2 = findViewById(R.id.txt_timing_update2);
        txt_timing_update3 = findViewById(R.id.txt_timing_update3);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                double i = 0.01 + 99.99 * random.nextDouble();
                DecimalFormat df = new DecimalFormat("#.00");
                txt_timing_update1.setText(df.format(i));
                handler.postDelayed(this,1000);
            }
        };
        handler.postDelayed(runnable,1000);

//        Timer timer = new Timer();
//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
//                Random random = new Random();
//                int i = random.nextInt(100);
//                txt_timing_update2.setText(i+"人");
//            }
//        };
//        timer.schedule(task,1000);

        //倒计时,最大值可为99999999毫秒，约为99999秒
        new CountDownTimer(999999999,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                txt_timing_update3.setText(millisUntilFinished / 1000 + "秒");
            }

            @Override
            public void onFinish() {
                txt_timing_update3.setText("倒计时结束");
            }
        }.start();
    }
}
