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
import com.yks.simpledemo2.bean.TangShiBean;
import com.yks.simpledemo2.widget.MyActionBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：唐诗
 * 作者：钟志华
 * time:2018/09/29
 */

public class TangShiActivity extends Activity {

    private List<TangShiBean> mList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tangshi);

        initView();
    }

    private void initView() {
        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "唐诗");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        RecyclerView recycler_tangshi = findViewById(R.id.view_recycler_tangshi);
        //横向滚动
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_tangshi.setLayoutManager(manager);
        initData();
        //设置适配器
        com.zhy.adapter.recyclerview.CommonAdapter<TangShiBean> adapter = new com.zhy.adapter.recyclerview.CommonAdapter<TangShiBean>(TangShiActivity.this, R.layout.item_tangshi, mList) {
            @Override
            protected void convert(com.zhy.adapter.recyclerview.base.ViewHolder holder, TangShiBean tangShiBean, int position) {
                holder.setText(R.id.txt_item_tangshi_title, tangShiBean.getTitle());
                holder.setText(R.id.txt_item_tangshi_author, tangShiBean.getAuthor());
                holder.setText(R.id.txt_item_tangshi_content, tangShiBean.getParagraphs());
            }
        };
        recycler_tangshi.setAdapter(adapter);
        recycler_tangshi.setItemAnimator(new DefaultItemAnimator());
        recycler_tangshi.addItemDecoration(new DividerItemDecoration(TangShiActivity.this,DividerItemDecoration.HORIZONTAL));
    }

    private void initData(){
        StringBuilder builder = new StringBuilder();
        try {
            AssetManager manager = TangShiActivity.this.getAssets();
            BufferedReader reader = new BufferedReader(new InputStreamReader(manager.open("tangshi.json")));
            String line;
            while ((line = reader.readLine()) != null){
                builder.append(line);
            }

        }catch (IOException e){
            e.printStackTrace();
        }

        String json = builder.toString();

        mList = JSON.parseArray(json,TangShiBean.class);
    }

}
