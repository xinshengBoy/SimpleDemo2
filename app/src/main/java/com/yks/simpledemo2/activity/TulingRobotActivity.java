package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.yks.simpledemo2.R;
import com.yks.simpledemo2.widget.MyActionBar;

import net.lemonsoft.lemonhello.LemonHello;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 描述：图灵只能机器人API对接
 * 作者：zzh
 * 参考文档：https://www.kancloud.cn/turing/www-tuling123-com/718227
 * time:2018/08/24
 */

public class TulingRobotActivity extends Activity implements View.OnClickListener{

    private EditText et_robot;
    private Button btn_robot;
    private TextView txt_tuling;
    private RequestQueue mQueue;
    private static int TULINGSUCCESS = 0;
    private static int TULINGFAIL = 1;
    private String allWords = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tuling_robot);

        //// TODO: 2017/4/18 用于请求数据
        mQueue = Volley.newRequestQueue(getApplicationContext());
        initView();
    }

    private void initView() {
        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "图灵机器人");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

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
                LemonHello.getErrorHello("错误","请输入内容");
            }
        }
    }

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == TULINGSUCCESS){//获取回复成功
                Bundle bundle = msg.getData();
                txt_tuling.setText(bundle.getString("msg"));
            }
        }
    };

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
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
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
                            Message message = new Message();
                            message.what = TULINGSUCCESS;
                            Bundle bundle = new Bundle();
                            bundle.putString("msg",allWords);
                            message.setData(bundle);
                            handler.sendMessage(message);
                        }else {
                            LemonHello.getErrorHello("错误",answer);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(request);
    }
}
