package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yks.simpledemo2.R;
import com.yks.simpledemo2.tools.HalfFind;
import com.yks.simpledemo2.tools.Info;
import com.yks.simpledemo2.tools.QuickSort;

import java.lang.ref.WeakReference;
import java.util.regex.Pattern;

/**
 * 描述：算法
 * 作者：zzh
 * time:2019/07/03
 */
public class AlgorithmActivity extends Activity implements View.OnClickListener {

    private Context mContext = AlgorithmActivity.this;
    private Activity mActivity = AlgorithmActivity.this;
    private final int SORTERROR = 0;//错误提示
    private final int SORTSUCCESS = 1;//排序成功
    private final int HALFFINDERROR = 2;//二分法查找失败
    private final int HALFFINDSUCCESS = 3;//二分法查找成功

    private MyHandler handler;

    private EditText et_algo_sort,et_half_find,et_half_find_key;
    private Button btn_algo_sort,btn_half_find;
    private TextView txt_algo_result,txt_half_find;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_algorithm);

        handler = new MyHandler(mActivity);
        initView();
    }

    private void initView() {
        Info.setActionBar(mActivity,R.id.headerLayout,"算法");
        //冒泡排序
        et_algo_sort = findViewById(R.id.et_algo_sort);
        btn_algo_sort = findViewById(R.id.btn_algo_sort);
        txt_algo_result = findViewById(R.id.txt_algo_result);
        //二分法
        et_half_find = findViewById(R.id.et_half_find);
        et_half_find_key = findViewById(R.id.et_half_find_key);
        btn_half_find = findViewById(R.id.btn_half_find);
        txt_half_find = findViewById(R.id.txt_half_find);

        btn_algo_sort.setOnClickListener(this);
        btn_half_find.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_algo_sort){//冒泡排序
            Info.hideKeyboard(mContext,et_algo_sort);
            String input = et_algo_sort.getText().toString().trim();
            if (input.equals("")){
                sendMessage(SORTERROR,"请先输入数字，并以逗号分隔");
                return;
            }
            //如果字符最后一位是逗号，将这个逗号移除
            if (input.substring(input.length()-1).equals(",")){
                input = input.substring(0,input.length()-1);
            }
            //根据逗号拆分字符
            String [] splits = input.split(",");
            //存储拆分出来的int数组
            int [] arrays = new int[splits.length];
            for (int i=0;i<splits.length;i++){
                //使用正则判断这个字符是否是数字
                Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");
                if (pattern.matcher(splits[i]).matches()){
                    arrays[i] = Integer.parseInt(splits[i]);
                }
            }
            //开启线程计算排序
            new Thread(new Runnable() {
                @Override
                public void run() {
                    QuickSort quickSort = new QuickSort();
                    String res = quickSort.doQuickSort(arrays);
                    sendMessage(SORTSUCCESS,res);
                }
            }).start();
        }else if (view == btn_half_find){//二分查找
            String input = et_half_find.getText().toString().trim();
            if (input.equals("")){
                sendMessage(HALFFINDERROR,"请先输入数字，并以逗号分隔");
                return;
            }
            String inputKey = et_half_find_key.getText().toString().trim();
            if (inputKey.equals("")){
                sendMessage(HALFFINDERROR,"请先输入要查找的关键字");
                return;
            }

            //如果字符最后一位是逗号，将这个逗号移除
            if (input.substring(input.length()-1).equals(",")){
                input = input.substring(0,input.length()-1);
            }
            //根据逗号拆分字符
            String [] splits = input.split(",");
            //存储拆分出来的int数组
            int [] arrays = new int[splits.length];
            for (int i=0;i<splits.length;i++){
                //使用正则判断这个字符是否是数字
                Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");
                if (pattern.matcher(splits[i]).matches()){
                    arrays[i] = Integer.parseInt(splits[i]);
                }
            }
            //开启线程计算排序
            String finalInput = input;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HalfFind halfFind = new HalfFind();
                    int position = halfFind.recursionBinarySearch(arrays,Integer.parseInt(inputKey),0,arrays.length-1);
                    int secondPosition = halfFind.commonBinarySearch(arrays,Integer.parseInt(inputKey));
                    if (position == -1 || secondPosition == -1){
                        sendMessage(HALFFINDSUCCESS,"未找到此值的位置");
                    }else if (position == secondPosition){
                        sendMessage(HALFFINDSUCCESS,"在数组["+ finalInput +"]查找的key："+inputKey+"在数组中的位置是："+position);
                    }else {
                        sendMessage(HALFFINDERROR,"错误！");
                    }
                }
            }).start();
        }
    }

    private class MyHandler extends Handler {
        final WeakReference<Activity> mWeakReference;

        MyHandler(Activity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mWeakReference.get() != null) {
                if (msg.what == SORTERROR) {//错误提示
                    Bundle bundle = msg.getData();
                    Info.showToast(mContext, bundle.getString("msg"), false);
                    txt_algo_result.setText(bundle.getString("msg"));
                    Info.playRingtone(mContext, false);
                    Info.hideKeyboard(mContext,et_algo_sort);
                }else if (msg.what == SORTSUCCESS){
                    Bundle bundle = msg.getData();
                    txt_algo_result.setText("排序结果："+bundle.getString("msg"));
                    Info.playRingtone(mContext,true);
                    Info.hideKeyboard(mContext,et_algo_sort);
                }else if (msg.what == HALFFINDERROR){
                    Bundle bundle = msg.getData();
                    Info.showToast(mContext, bundle.getString("msg"), false);
                    txt_half_find.setText(bundle.getString("msg"));
                    Info.playRingtone(mContext, false);
                    Info.hideKeyboard(mContext,et_half_find);
                } else if (msg.what == HALFFINDSUCCESS){
                    Bundle bundle = msg.getData();
                    txt_half_find.setText(bundle.getString("msg"));
                    Info.playRingtone(mContext, true);
                    Info.hideKeyboard(mContext,et_half_find);
                }
            }
        }
    }

    /**
     * 描述：发送handler消息
     * 作者：zzh
     * @param id 需要进入到的handler
     * @param msg 传递的消息
     */
    private void sendMessage(int id,String msg){
        Bundle bundle = new Bundle();
        bundle.putString("msg",msg);
        Message message = new Message();
        message.what = id;
        message.setData(bundle);
        handler.sendMessage(message);
    }
}
