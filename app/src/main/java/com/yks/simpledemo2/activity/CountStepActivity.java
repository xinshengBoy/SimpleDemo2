package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.yks.simpledemo2.R;
import com.yks.simpledemo2.tools.Info;

import net.lemonsoft.lemonhello.LemonHello;
import net.lemonsoft.lemonhello.LemonHelloAction;
import net.lemonsoft.lemonhello.LemonHelloInfo;
import net.lemonsoft.lemonhello.LemonHelloView;
import net.lemonsoft.lemonhello.interfaces.LemonHelloActionDelegate;

import java.util.List;

/**
 * 描述：计步器的简单使用
 * 作者：zzh
 * time:2019/05/08
 * 参考：https://blog.csdn.net/huilin9960/article/details/80506004  效果不好
 */
public class CountStepActivity extends Activity implements SensorEventListener {

    private Context mContext = CountStepActivity.this;
    private Activity mActivity = CountStepActivity.this;
    private TextView txt_countstep;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_countstep);

        initView();
    }

    private void initView() {
        Info.setActionBar(mActivity,R.id.headerLayout,"计步器");

        txt_countstep = findViewById(R.id.txt_countstep);

        SensorManager manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //获取设备支持的所有传感器列表
        assert manager != null;
        List<Sensor> sensorList = manager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : sensorList){
            Log.d("mysensor","支持的传感器有："+sensor.getName());
        }

        //计步器
        Sensor stepCounter = manager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (stepCounter != null){
            manager.registerListener(this, stepCounter,100000);
        }else {
            LemonHello.getErrorHello("提示","当前设备不支持计步器").addAction(new LemonHelloAction("确定", new LemonHelloActionDelegate() {
                @Override
                public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                    lemonHelloView.hide();
                }
            })).show(mContext);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Log.d("mysensor",sensorEvent.toString());
        txt_countstep.setText("您今天走了："+sensorEvent.values[0]+"步");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}
