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
import com.yks.simpledemo2.tools.Info;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import net.lemonsoft.lemonbubble.LemonBubble;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

import okhttp3.Call;

/**
 * 描述：图灵只能机器人API对接
 * 作者：zzh
 * 参考文档：https://www.kancloud.cn/turing/www-tuling123-com/718227
 * time:2018/08/24
 */

public class TulingRobotActivity extends Activity implements View.OnClickListener{

    private Context mContext = TulingRobotActivity.this;

    private final int TULINGSUCCESS = 0;
    private final int TULINGFAIL = 1;

    private EditText et_robot;
    private Button btn_robot;
    private TextView txt_tuling;
    private String allWords = "";
    private MyHandler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tuling_robot);

        handler = new MyHandler(TulingRobotActivity.this);
        initView();
    }

    private void initView() {
        Info.setActionBar(this,R.id.headerLayout,"图灵机器人");

        et_robot = findViewById(R.id.et_robot);
        btn_robot = findViewById(R.id.btn_robot);
        txt_tuling = findViewById(R.id.txt_tuling);
        btn_robot.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_robot){
            String text = et_robot.getText().toString().trim();
            if (!text.equals("")) {
                getTulingInfo(text);
            }else {
                sendMessage(TULINGFAIL,"请输入内容");
            }
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
                if (msg.what == TULINGSUCCESS) {//获取回复成功
                    txt_tuling.setText(allWords);
                    Info.playRingtone(mContext, true);
                } else if (msg.what == TULINGFAIL) {
                    Bundle bundle = msg.getData();
                    Info.showToast(mContext, bundle.getString("msg"), false);
                    Info.playRingtone(mContext, false);
                }
            }
        }
    }

    /**
     * 描述：获取接口返回
     * 作者：zzh
     * @param text 你的提问
     */
    private void getTulingInfo(String text){
        if (allWords.equals("")){
            allWords = text + "\n";
        }else {
            allWords += text + "\n";
        }
        String url = "http://openapi.tuling123.com/openapi/api/v2";
        JSONObject object = new JSONObject();
        try {
            object.put("reqType",0);

            JSONObject object1 = new JSONObject();
            object1.put("text",text);
            JSONObject object2 = new JSONObject();
            object2.put("inputText",object1);
            object.put("perception",object2);

            JSONObject object3 = new JSONObject();
            object3.put("apiKey","90ca0c6864fa485190269daec7f86454");
            object3.put("userId","100047");
            object.put("userInfo",object3);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpUtils.postString().url(url)
                .content(object.toString())
                .tag(this)
                .build()
                .connTimeOut(10000L)
                .readTimeOut(10000L)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        sendMessage(TULINGFAIL,"失败1："+e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject object = new JSONObject(response.toString());
                            JSONObject object1 = object.getJSONObject("intent");
                            String code = object1.getString("code");
                            JSONArray array = object.getJSONArray("results");
                            for (int i=0;i<array.length();i++){
                                JSONObject object2 = array.getJSONObject(i);
                                JSONObject object3 = object2.getJSONObject("values");
                                String answer = object3.getString("text");
                                if (code.equals("10004")){
                                    allWords += answer + "\n";
                                    handler.sendEmptyMessage(TULINGSUCCESS);
                                }else {
                                    sendMessage(TULINGFAIL,answer);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            sendMessage(TULINGFAIL,"失败2："+e);
                        }
                    }
                });
    }

    /**
     * 描述：发handler消息
     * 作者：zzh
     * @param id 需要进入到的handler
     * @param msg 传递的消息
     */
    private void sendMessage(int id, String msg) {
        Bundle bundle = new Bundle();
        bundle.putString("msg",msg);
        Message message = new Message();
        message.what = id;
        message.setData(bundle);
        handler.sendMessage(message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LemonBubble.forceHide();
        OkHttpUtils.getInstance().cancelTag(this);
        handler.removeCallbacksAndMessages(null);
    }
}
