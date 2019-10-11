package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * 描述：通过API接口获取身份证号码的信息
 * 作者：zzh
 * 参考网址：https://blog.csdn.net/huangxinyu_it/article/details/50894320
 * time:2018/08/27
 */

public class IdCardActivity extends Activity implements View.OnClickListener{

    private Context mContext = IdCardActivity.this;
    private final int GETIDCARDSUCCESS = 0;
    private final int GETIDCARDFAIL = 1;

    private EditText et_idcard;
    private Button btn_idcard_search;
    private TextView txt_idcard_info;

    private String idCardInfo = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_idcard);

        initView();
    }

    private void initView() {
        Info.setActionBar(IdCardActivity.this,R.id.headerLayout,"身份证信息");

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
                sendMessage(GETIDCARDFAIL,"身份证号码不能为空");
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
                LemonBubble.hide();
                txt_idcard_info.setText(idCardInfo);
                Info.playRingtone(mContext,true);
            }else if (msg.what == GETIDCARDFAIL){//获取身份证信息失败
                Bundle bundle = msg.getData();
                Info.showToast(mContext, bundle.getString("msg"), false);
                Info.playRingtone(mContext, false);
            }
        }
    };

    /**
     * 描述：通过身份证号码获取身份证对应的信息
     * 作者：zzh
     * @param idCard 身份证号码
     */
    private void SearchIdCardInfo(String idCard) {
        Info.showProgress(mContext,"查询中...");
        String url = "http://api.k780.com:88/?app=idcard.get&idcard="+idCard+"&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=json";
        OkHttpUtils.post().url(url)
                .tag(this)
                .build()
                .connTimeOut(10000L)
                .readTimeOut(10000L)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        sendMessage(GETIDCARDFAIL,"失败1："+e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject object = new JSONObject(response);
                            String success = object.getString("success");
                            if (success.equals("1")){
                                idCardInfo = "";
                                //成功
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

                                handler.sendEmptyMessage(GETIDCARDSUCCESS);
                            }else {
                                sendMessage(GETIDCARDFAIL,object.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            sendMessage(GETIDCARDFAIL,"失败2："+e);
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
        handler.removeCallbacksAndMessages(null);
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
