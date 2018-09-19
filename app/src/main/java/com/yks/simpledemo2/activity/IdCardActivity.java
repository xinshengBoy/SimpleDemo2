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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.yks.simpledemo2.R;
import com.yks.simpledemo2.widget.MyActionBar;

import net.lemonsoft.lemonhello.LemonHello;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 描述：通过API接口获取身份证号码的信息
 * 作者：zzh
 * 参考网址：https://blog.csdn.net/huangxinyu_it/article/details/50894320
 * time:2018/08/27
 */

public class IdCardActivity extends Activity implements View.OnClickListener{

    private EditText et_idcard;
    private Button btn_idcard_search;
    private TextView txt_idcard_info;
    private final int GETIDCARDSUCCESS = 0;
    private final int GETIDCARDFAIL = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_idcard);

        initView();
    }

    private void initView() {
        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "身份证信息");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        et_idcard = findViewById(R.id.et_idcard);
        btn_idcard_search = findViewById(R.id.btn_idcard_search);
        txt_idcard_info = findViewById(R.id.txt_idcard_info);

        btn_idcard_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_idcard_search){
            String idCard = et_idcard.getText().toString().trim();
            if (idCard.equals("")){
                LemonHello.getErrorHello("错误","身份证号码不能为空");
            }else {
                SearchIdCardInfo(idCard);
            }
        }
    }

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == GETIDCARDSUCCESS){//获取身份证信息成功
                Bundle bundle = msg.getData();
                txt_idcard_info.setText(bundle.getString("msg"));
            }else if (msg.what == GETIDCARDFAIL){//获取身份证信息失败
                Bundle bundle = msg.getData();
                LemonHello.getErrorHello("失败",bundle.getString("error"));
            }
        }
    };

    /**
     * 描述：通过身份证号码获取身份证对应的信息
     * 作者：zzh
     * @param idCard 身份证号码
     */
    private void SearchIdCardInfo(String idCard) {
        String url = "http://api.k780.com:88/?app=idcard.get&idcard="+idCard+"&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=json";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    String success = object.getString("success");
                    if (success.equals("1")){
                        //成功
                        String idCardInfo = "";
                        JSONObject object1 = object.getJSONObject("result");
                        idCardInfo += "状态：" + object1.getString("status") + "\n";
                        idCardInfo += "par：" + object1.getString("par") + "\n";
                        idCardInfo += "身份证号码：" + object1.getString("idcard") + "\n";
                        idCardInfo += "出生日期：" + object1.getString("born") + "\n";
                        idCardInfo += "性别：" + object1.getString("sex") + "\n";
                        idCardInfo += "地址：" + object1.getString("att") + "\n";
                        idCardInfo += "邮编：" + object1.getString("postno") + "\n";
                        idCardInfo += "区号：" + object1.getString("areano") + "\n";
                        idCardInfo += "籍贯：" + object1.getString("style_simcall") + "\n";
                        idCardInfo += "城市名称：" + object1.getString("style_citynm") + "\n";

                        Message message = new Message();
                        message.what = GETIDCARDSUCCESS;
                        Bundle bundle = new Bundle();
                        bundle.putString("msg",idCardInfo);
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }else {
                        //失败
                        Message message = new Message();
                        message.what = GETIDCARDFAIL;
                        Bundle bundle = new Bundle();
                        bundle.putString("error",object.getString("msg"));
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Message message = new Message();
                    message.what = GETIDCARDFAIL;
                    Bundle bundle = new Bundle();
                    bundle.putString("error",e.getMessage());
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MainActivity.mQueue.add(request);
    }
}
