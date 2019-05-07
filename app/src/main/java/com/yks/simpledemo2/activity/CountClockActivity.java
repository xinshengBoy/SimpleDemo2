package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.app.Service;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yks.simpledemo2.R;
import com.yks.simpledemo2.tools.Info;

/**
 * 描述：倒计时
 * 作者：zzh
 * time:2019/04/18
 */
public class CountClockActivity extends Activity {

    private TextView txt_countclock;
    private Button btn_start_countclock;
    private MyCount myCount;
    private Vibrator vibrator;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_countclock);
        myCount = new MyCount(60000,1000);
        vibrator = (Vibrator) getApplication().getSystemService(Service.VIBRATOR_SERVICE);
        initView();
    }

    private void initView() {
        Info.setActionBar(CountClockActivity.this,R.id.headerLayout,"倒计时");

        txt_countclock = findViewById(R.id.txt_countclock);
        btn_start_countclock = findViewById(R.id.btn_start_countclock);
        btn_start_countclock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = btn_start_countclock.getText().toString().trim();
                if (text.equals("开始")){
                    myCount.start();
                    btn_start_countclock.setText("取消");
                }else {
                    myCount.cancel();
                    vibrator.cancel();
                    btn_start_countclock.setText("开始");
                }
            }
        });
    }

    private class MyCount extends CountDownTimer {

        /**
         * @param millisInFuture   总的时间
         * @param countDownInterval 每次计数的间隔
         */
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            txt_countclock.setText(millisUntilFinished/1000+"秒...");
        }

        @Override
        public void onFinish() {
            txt_countclock.setText("倒计时");
            btn_start_countclock.setText("开始");
            vibrator.vibrate(new long[]{300,300,300,1000},-1);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myCount.cancel();
        vibrator.cancel();
    }
}
