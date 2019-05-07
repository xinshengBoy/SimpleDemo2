package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.yks.simpledemo2.R;
import com.yks.simpledemo2.bean.BaiduBean;
import com.yks.simpledemo2.tools.GoogleTranslateUtil;
import com.yks.simpledemo2.tools.Info;

import net.lemonsoft.lemonhello.LemonHello;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：谷歌翻译接口
 * 作者：zzh
 * 参考文档：https://blog.csdn.net/pifutan/article/details/78554691
 * time:2018/08/27
 */

public class GoogleTranslateActivity extends Activity implements View.OnClickListener{

    private EditText et_input,et_baidu_translate;
    private Button btn_translate,btn_baidu_translate,btn_baidu_from,btn_baidu_to;
    private TextView txt_translateInfo,txt_baidu_info;
    private String APPID = "20180827000199360";
    private String APPKEY = "4SdzsC5S9WFUWsj9ZfsF";
    private String SALT = "1435660288";
    private String [] code = new String[]{"zh","en","yue","wyw","jp","kor","fra","spa","th","ara","ru","pt","de","it","el","nl","pl","bul","est","dan","fin","cs","rom","slo","swe","hu","cht","vie"};
    private String [] name = new String[]{"中文","英语","粤语","文言文","日语","韩语","法语","西班牙语","泰语","阿拉伯语","俄语","葡萄牙语","德语","意大利语","希腊语","荷兰语","波兰语","保加利亚语","爱沙尼亚语","丹麦语","芬兰语","捷克语","罗马尼亚语","斯洛文尼亚语","瑞典语","匈牙利语","繁体中文","越南语"};
    private List<BaiduBean> mList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_google_translate);

        initView();
        initData();
    }

    private void initView() {
        Info.setActionBar(GoogleTranslateActivity.this,R.id.headerLayout,"谷歌翻译");

        et_input = findViewById(R.id.et_google_translate);
        btn_translate = findViewById(R.id.btn_translate);
        txt_translateInfo = findViewById(R.id.txt_google_info);

        et_baidu_translate = findViewById(R.id.et_baidu_translate);
        btn_baidu_translate = findViewById(R.id.btn_baidu_translate);
        txt_baidu_info = findViewById(R.id.txt_baidu_info);
        btn_baidu_from = findViewById(R.id.btn_baidu_from);
        btn_baidu_to = findViewById(R.id.btn_baidu_to);

        btn_baidu_from.setText(name[0]);
        btn_baidu_to.setText(name[1]);

        txt_baidu_info.setTextIsSelectable(true);

        btn_translate.setOnClickListener(this);
        btn_baidu_translate.setOnClickListener(this);
        btn_baidu_from.setOnClickListener(this);
        btn_baidu_to.setOnClickListener(this);
    }

    private void initData(){
        for (int i=0;i<code.length;i++){
            BaiduBean bean = new BaiduBean(code[i],name[i]);
            mList.add(bean);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == btn_translate){
            String input = et_input.getText().toString();
            if (input.equals("")){
                LemonHello.getErrorHello("错误","请输入要翻译的内容");
            }else {
                boolean isGB = Info.isHanzi(input);
                if (isGB){
                    googleTranslate(input,"en");
                }else {
                    googleTranslate(input,"cn");
                }
            }
        }else if (view == btn_baidu_translate){
            String input = et_baidu_translate.getText().toString();
            if (input.equals("")){
                LemonHello.getErrorHello("错误","请输入要翻译的内容");
            }else {
                String from = getChoice(btn_baidu_from.getText().toString());
                String to = getChoice(btn_baidu_to.getText().toString());


                baiduTanslate(input,from,to);
            }
        }else if (view == btn_baidu_from){
            showChoice(true);
        }else if (view == btn_baidu_to){
            showChoice(false);
        }
    }

    /**
     * 描述：通过API接口获取翻译的内容
     * 作者：zzh
     * @param words 要翻译的内容
     * @param targe 标志：是将中文翻译成英文还是将英文翻译成中文
     */
    private void googleTranslate(String words,String targe) {
        GoogleTranslateUtil.TranslateCallback callback = new GoogleTranslateUtil.TranslateCallback() {
            @Override
            public void onTranslateDone(String result) {
                txt_translateInfo.setText(result);
            }
        };

        new GoogleTranslateUtil().translate(GoogleTranslateActivity.this,"auto",targe,words,callback);
    }

    private void baiduTanslate(String words,String from,String to){
        String sign = Info.md5(APPID+words+SALT+APPKEY);
        String url = "http://api.fanyi.baidu.com/api/trans/vip/translate?q="+words+"&from="+from+"&to="+to+"&appid="+APPID+"&salt="+SALT+"&sign="+sign;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (response.contains("error_code")){
                        String error_code = object.getString("error_code");
                        String error_msg = object.getString("error_msg");
                        Info.showToast(GoogleTranslateActivity.this,error_code+":"+error_msg,false);
                    }else {
                        JSONArray array = object.getJSONArray("trans_result");
                        String info = "";
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object1 = array.getJSONObject(i);
                            info += object1.getString("dst");
                        }
                        txt_baidu_info.setText(info);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LemonHello.getErrorHello("错误",e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LemonHello.getErrorHello("错误",error.getMessage());
            }
        });
        MainActivity.mQueue.add(request);
    }

    private void showChoice(final boolean isFrom){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("语言选择");
        builder.setItems(name, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (isFrom){
                    btn_baidu_from.setText(name[i]);
                }else {
                    btn_baidu_to.setText(name[i]);
                }
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    /**
     * 描述：通过名称获取code
     * 作者：zzh
     * @param check 选择的语言
     * @return 语言对应的code
     */
    private String getChoice(String check){
        for (int i=0;i<name.length;i++){
            if (check.equals(name[i])){
                return code[i];
            }
        }
        return "";
    }
}
