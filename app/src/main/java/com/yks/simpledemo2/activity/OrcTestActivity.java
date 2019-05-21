package com.yks.simpledemo2.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.github.chrisbanes.photoview.PhotoView;
import com.yks.simpledemo2.R;
import com.yks.simpledemo2.tools.Info;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import net.lemonsoft.lemonbubble.LemonBubble;
import net.lemonsoft.lemonhello.LemonHello;
import net.lemonsoft.lemonhello.LemonHelloAction;
import net.lemonsoft.lemonhello.LemonHelloInfo;
import net.lemonsoft.lemonhello.LemonHelloView;
import net.lemonsoft.lemonhello.interfaces.LemonHelloActionDelegate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.ref.WeakReference;

import kr.co.namee.permissiongen.PermissionGen;
import okhttp3.Call;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 描述：识别图片文字并将文字识别的结果合成语音播放
 * 作者：zzh
 * 详情：主要用的是百度的文字识别和语音合成
 * 详见地址：https://console.bce.baidu.com/ai/?_=1557288147159&fromai=1#/ai/ocr/report/index
 * time:2019/05/08
 */
public class OrcTestActivity extends Activity implements View.OnClickListener, SpeechSynthesizerListener {

    private Context mContext = OrcTestActivity.this;
    private Activity mActivity = OrcTestActivity.this;

    private final int LUBANYASUO = 0;
    private final int GETACCESSTOKENFAIL = 1;
    private final int GETACCESSTOKENSUCCESS = 2;
    private final int ORCSUCCESS = 3;

    private PhotoView iv_orc;
    private TextView txt_orcresult;
    private Button btn_takephoto,btn_orc,btn_speak;
    private  String imgPath = "";//存储拍照返回的图片存储路径（绝对路径）
    private String path = Environment.getExternalStorageDirectory() + "/YksReceive/";
    private int REQUEST_CODE_CAMERA = 4;
    private MyHandler handler;
    private String ACCESS_TOKEN = "";
    private String BAIDUAI_ID = "16202917";
    private String BAIDUAI_KEY = "BuGi1KBm5GmqOUgKwTE5Sy6E";
    private String BAIDUAI_SECRET_KEY = "LdnCcLuYZdcDPYXovcbmaF0Ljle4hkVg";
    private String result = "";
    protected com.baidu.tts.client.SpeechSynthesizer mSpeechSynthesizer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_orc);

        handler = new MyHandler(mActivity);
        initView();
        initialTts();
    }

    private void initView() {
        Info.setActionBar(mActivity,R.id.headerLayout,"文字识别");

        iv_orc = findViewById(R.id.iv_orc);
        txt_orcresult = findViewById(R.id.txt_orcresult);
        btn_takephoto = findViewById(R.id.btn_takephoto);
        btn_orc = findViewById(R.id.btn_orc);
        btn_speak = findViewById(R.id.btn_speak);

        btn_takephoto.setOnClickListener(this);
        btn_orc.setOnClickListener(this);
        btn_speak.setOnClickListener(this);
    }

    /**
     * 描述：初始化语音合成
     * 作者：zzh
     */
    private void initialTts() {
        this.mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        this.mSpeechSynthesizer.setContext(mContext);
        this.mSpeechSynthesizer.setSpeechSynthesizerListener(this);

        this.mSpeechSynthesizer.setAppId(BAIDUAI_ID);
        this.mSpeechSynthesizer.setApiKey(BAIDUAI_KEY, BAIDUAI_SECRET_KEY);

        // 以下setParam 参数选填。不填写则默认值生效
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "4"); // 设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "9"); // 设置合成的音量，0-9 ，默认 5
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, "4");// 设置合成的语速，0-9 ，默认 5
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_PITCH, "5");// 设置合成的语调，0-9 ，默认 5
        mSpeechSynthesizer.initTts(TtsMode.ONLINE);// 引擎初始化tts接口
    }

    @Override
    public void onSynthesizeStart(String s) {

    }

    @Override
    public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {

    }

    @Override
    public void onSynthesizeFinish(String s) {

    }

    @Override
    public void onSpeechStart(String s) {

    }

    @Override
    public void onSpeechProgressChanged(String s, int i) {

    }

    @Override
    public void onSpeechFinish(String s) {

    }

    @Override
    public void onError(String s, SpeechError speechError) {

    }

    @Override
    public void onClick(View view) {
        if (view == btn_takephoto){//拍照
            takePhoto();
        }else if (view == btn_orc){//文字识别
            if (imgPath.equals("")){
                Info.showToast(mContext,"图片为空，请先拍照",false);
                return;
            }
            File file = new File(imgPath);
            if (!file.exists()) {
                Info.showToast(mContext, "图片文件不存在", false);
                return;
            }
            getBaiduAIToken();
        }else if (view == btn_speak){//语音合成
            if (result.equals("")){
                Info.showToast(mContext, "暂无识别内容，无法合成", false);
                return;
            }
            mSpeechSynthesizer.speak(result);
        }
    }

    private class MyHandler extends Handler {
        WeakReference<Activity> mWeakReference;

        MyHandler(Activity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mWeakReference.get() != null) {
                if (msg.what == LUBANYASUO){//todo 压缩图片
                    //鲁班压缩图片，先删除原先的图片，再压缩
                    Luban.with(mContext)
                            .load(imgPath)
//                        .ignoreBy(100)
                            .setFocusAlpha(false)
                            .setTargetDir(path)
                            .setCompressListener(new OnCompressListener() {
                                @Override
                                public void onStart() {

                                }

                                @Override
                                public void onSuccess(File file) {
                                    File files = new File(imgPath);
                                    if (files.exists()) {
                                        files.delete();
                                    }
                                    imgPath = file.getAbsolutePath();
                                    Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
                                    iv_orc.setImageBitmap(bitmap);
                                    LemonBubble.hide();
                                }

                                @Override
                                public void onError(Throwable e) {

                                }
                            }).launch();
                }else if (msg.what == GETACCESSTOKENFAIL){//todo 获取token失败
                    Bundle bundle = msg.getData();
                    Info.showToast(mContext, bundle.getString("msg"), false);
                }else if (msg.what == GETACCESSTOKENSUCCESS){//todo 获取token成功
                    OrcImageAction();
                }else if (msg.what == ORCSUCCESS){//todo 识别成功
                    LemonBubble.hide();
                    txt_orcresult.setText(result);
                    LemonHello.getSuccessHello("成功",result).addAction(new LemonHelloAction("确定", new LemonHelloActionDelegate() {
                        @Override
                        public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                            lemonHelloView.hide();
                        }
                    })).show(mContext);
                }
            }

        }
    }

    /**
     * 描述：拍照，打开摄像头，设置图片保存的路径，申请摄像头前会要申请使用
     * 作者：zzh
     */
    private void takePhoto() {
        PermissionGen.needPermission(mActivity,100,new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE});

        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }
        //调用相机
        Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //下面这句指定调用相机拍照后的照片存储的路径
        imgPath = path+System.currentTimeMillis()+".jpg";
        File file = new File(path);
        if (!file.mkdirs()) {
            file.mkdirs();
        }
        takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(imgPath)));
        startActivityForResult(takeIntent, REQUEST_CODE_CAMERA);

    }

    @SuppressLint("CheckResult")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_CAMERA){
            Info.showProgress(mContext,"获取中...");
            handler.sendEmptyMessage(LUBANYASUO);
        }
    }

    /**
     * 描述：获取百度AI的token
     * 作者：zzh
     */
    private void getBaiduAIToken(){
        Info.showProgress(mContext,"识别中...");
        String url = "https://aip.baidubce.com/oauth/2.0/token?"
                +"grant_type=client_credentials"
                +"&client_id=" + BAIDUAI_KEY
                +"&client_secret=" + BAIDUAI_SECRET_KEY;
        OkHttpUtils.post().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                sendMessage(GETACCESSTOKENFAIL,"msg",e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    if (!response.equals("")) {
                        JSONObject object = new JSONObject(response);
                        ACCESS_TOKEN = object.getString("access_token");
                        handler.sendEmptyMessage(GETACCESSTOKENSUCCESS);
                    }else {
                        sendMessage(GETACCESSTOKENFAIL,"msg","返回内容为空");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    sendMessage(GETACCESSTOKENFAIL,"msg",e.getMessage());
                }
            }
        });
    }

    /**
     * 描述：调用百度文字识别接口，获取返回数据
     * 作者：zzh
     */
    private void OrcImageAction(){
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic";
        OkHttpUtils.post().url(url)
                .addParams("access_token",ACCESS_TOKEN)
                .addParams("image",Info.imageToBase64(imgPath))
                .addHeader("Content-Type","application/x-www-form-urlencoded")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        sendMessage(GETACCESSTOKENFAIL,"msg","获取失败1："+e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("words_result");
                            if (array != null && array.length() != 0){
                                if (!result.equals("")){
                                    result = "";
                                }
                                for (int i=0;i<array.length();i++){
                                    JSONObject object1 = array.getJSONObject(i);
                                    result += object1.getString("words")+",";
                                }
                                handler.sendEmptyMessage(ORCSUCCESS);
                            }else {
                                sendMessage(GETACCESSTOKENFAIL,"msg","获取失败2：识别内容为空");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 发handler消息
     *
     * @param code    what的值
     * @param msgName bundler的key
     * @param msg     bundle的value
     */
    private void sendMessage(int code, String msgName, String msg) {
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putString(msgName, msg);
        message.what = code;
        message.setData(bundle);
        handler.sendMessage(message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LemonBubble.forceHide();
        handler.removeCallbacksAndMessages(null);
        if (mSpeechSynthesizer != null){
            mSpeechSynthesizer.stop();
            mSpeechSynthesizer.release();
            mSpeechSynthesizer = null;
        }
        result = "";
        result = null;
    }
}
