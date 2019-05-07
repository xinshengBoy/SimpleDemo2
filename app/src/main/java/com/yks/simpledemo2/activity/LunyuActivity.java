package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.yks.simpledemo2.R;
import com.yks.simpledemo2.bean.LunyuBean;
import com.yks.simpledemo2.tools.Info;
import com.yks.simpledemo2.widget.MyActionBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：论语
 * 作者：zzh
 * time:2018/09/29
 */

public class LunyuActivity extends Activity {

    private List<LunyuBean> mList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_lunyu);

        initView();
    }

    private void initView() {
        Info.setActionBar(LunyuActivity.this,R.id.headerLayout,"论语");
        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "论语");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        RecyclerView recycler_lunyu = findViewById(R.id.view_recycler_lunyu);
        //横向滚动
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_lunyu.setLayoutManager(manager);
        initData();
        //设置适配器
        com.zhy.adapter.recyclerview.CommonAdapter<LunyuBean> adapter = new com.zhy.adapter.recyclerview.CommonAdapter<LunyuBean>(LunyuActivity.this, R.layout.item_lunyu, mList) {
            @Override
            protected void convert(com.zhy.adapter.recyclerview.base.ViewHolder holder, LunyuBean lunyuBean, int position) {
                holder.setText(R.id.txt_item_lunyu_chapter, lunyuBean.getChapter());
                holder.setText(R.id.txt_item_lunyu_content, lunyuBean.getParagraphs());
            }
        };
        recycler_lunyu.setAdapter(adapter);
        recycler_lunyu.setItemAnimator(new DefaultItemAnimator());
        recycler_lunyu.addItemDecoration(new DividerItemDecoration(LunyuActivity.this,DividerItemDecoration.HORIZONTAL));
    }

    private void initData(){
        StringBuilder builder = new StringBuilder();
        try {
            AssetManager manager = LunyuActivity.this.getAssets();
            BufferedReader reader = new BufferedReader(new InputStreamReader(manager.open("lunyu.json")));
            String line;
            while ((line = reader.readLine()) != null){
                builder.append(line);
            }

        }catch (IOException e){
            e.printStackTrace();
        }

        String json = builder.toString();

        mList = JSON.parseArray(json,LunyuBean.class);
    }
}
