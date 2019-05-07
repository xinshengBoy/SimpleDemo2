package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yks.simpledemo2.R;
import com.yks.simpledemo2.bean.LrcBean;
import com.yks.simpledemo2.tools.Info;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：歌词解析
 * 作者：zzh
 * time:2019/03/29
 */
public class LrcActivity extends Activity {

    private TextView txt_lrc;
    private List<LrcBean> mList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_lrc);

        initView();
    }

    private void initView() {
        Info.setActionBar(LrcActivity.this,R.id.headerLayout,"歌词解析");

        txt_lrc = findViewById(R.id.txt_lrc);
        Button btn_lrcStart = findViewById(R.id.btn_lrcStart);

        btn_lrcStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //读取assets目录下的歌词文件
                parseLrc(readLrc());
                txt_lrc.setText(mList.toString());
            }
        });
    }

    private String readLrc(){
        //读取assets目录下的歌词文件
        try {
            InputStreamReader reader = new InputStreamReader(getResources().getAssets().open("later.lrc"));
            BufferedReader buffere = new BufferedReader(reader);
            String line;
            StringBuilder arr = new StringBuilder();
            while ((line = buffere.readLine()) != null){
                arr.append(line);
            }
            return arr.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void parseLrc(String lrc){
        String [] lrcs = lrc.split("]");
        for (int i=0;i<lrcs.length;i++){
            LrcBean bean = new LrcBean();
            bean.setLrc(lrcs[i]);
            mList.add(bean);
        }


        String lrcText = lrc.replaceAll("&#58;", ":")
                .replaceAll("&#10;", "\n")
                .replaceAll("&#46;", ".")
                .replaceAll("&#32;", " ")
                .replaceAll("&#45;", "-")
                .replaceAll("&#13;", "\r").replaceAll("&#39;", "'");
        String [] split = lrcText.split("\n");
        for (int i=0;i<split.length;i++){
            String text = split[i];
            if (text.contains(".")){
                String min = text.substring(text.indexOf("[")+1,text.indexOf("[")+3);
                String second = text.substring(text.indexOf(":")+1,text.indexOf(":")+3);
                String mills = text.substring(text.indexOf(".")+1,text.indexOf(".")+3);
                long startTime = Long.valueOf(min)*60*1000+Long.valueOf(second)*1000+Long.valueOf(mills)*10;
                String result = text.substring(text.indexOf("]")+1);
                LrcBean bean = new LrcBean();
                bean.setStart(startTime);
                bean.setLrc(result);
                mList.add(bean);
                if (mList.size() > 0){
                    mList.get(mList.size() - 2).setEnd(startTime);
                }
                if (i == split.length-1){
                    mList.get(mList.size()-1).setEnd(startTime+100000);
                }
            }
        }
    }
}
