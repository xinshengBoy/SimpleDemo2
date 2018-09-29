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
import com.yks.simpledemo2.bean.ShiJingBean;
import com.yks.simpledemo2.widget.MyActionBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：所有诗经
 * 作者：钟志华
 * time:2018/09/29
 */

public class ShiJingActivity extends Activity {

    private RecyclerView recycler_shijing;
    private List<ShiJingBean> mList = new ArrayList<>();
    private com.zhy.adapter.recyclerview.CommonAdapter<ShiJingBean> adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_shijing);

        initView();
    }

    private void initView() {
        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "诗经");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        recycler_shijing = findViewById(R.id.view_recycler_shijing);
        //横向滚动
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_shijing.setLayoutManager(manager);
        initData();
        //设置适配器
        adapter = new com.zhy.adapter.recyclerview.CommonAdapter<ShiJingBean>(ShiJingActivity.this,R.layout.item_shijing,mList) {
            @Override
            protected void convert(com.zhy.adapter.recyclerview.base.ViewHolder holder, ShiJingBean shiJingBean, int position) {
                holder.setText(R.id.txt_item_shijing_title,shiJingBean.getTitle());
                holder.setText(R.id.txt_item_shijing_type,shiJingBean.getChapter()+" / "+shiJingBean.getSection());
                holder.setText(R.id.txt_item_shijing_content,shiJingBean.getContent());
            }
        };
        recycler_shijing.setAdapter(adapter);
        recycler_shijing.setItemAnimator(new DefaultItemAnimator());
        recycler_shijing.addItemDecoration(new DividerItemDecoration(ShiJingActivity.this,DividerItemDecoration.HORIZONTAL));
    }

    private void initData(){
        StringBuilder builder = new StringBuilder();
        try {
            AssetManager manager = ShiJingActivity.this.getAssets();
            BufferedReader reader = new BufferedReader(new InputStreamReader(manager.open("shijing.json")));
            String line;
            while ((line = reader.readLine()) != null){
                builder.append(line);
            }

        }catch (IOException e){
            e.printStackTrace();
        }

        String json = builder.toString();

        mList = JSON.parseArray(json,ShiJingBean.class);
    }

}
