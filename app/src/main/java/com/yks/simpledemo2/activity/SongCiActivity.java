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
import com.yks.simpledemo2.bean.SongCiBean;
import com.yks.simpledemo2.widget.MyActionBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：宋词
 * 作者：zzh
 * time:2018/09/29
 */

public class SongCiActivity extends Activity {

    private RecyclerView recycler_songci;
    private List<SongCiBean> mList = new ArrayList<>();
    private com.zhy.adapter.recyclerview.CommonAdapter<SongCiBean> adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_songci);

        initView();
    }

    private void initView() {
        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "宋词");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        recycler_songci = findViewById(R.id.view_recycler_songci);
        //横向滚动
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_songci.setLayoutManager(manager);
        initData();
        //设置适配器
        adapter = new com.zhy.adapter.recyclerview.CommonAdapter<SongCiBean>(SongCiActivity.this,R.layout.item_songci,mList) {
            @Override
            protected void convert(com.zhy.adapter.recyclerview.base.ViewHolder holder, SongCiBean songCiBean, int position) {
                holder.setText(R.id.txt_item_songci_title,songCiBean.getRhythmic());
                holder.setText(R.id.txt_item_songci_author,songCiBean.getAuthor());
                holder.setText(R.id.txt_item_songci_content,songCiBean.getParagraphs());
            }
        };
        recycler_songci.setAdapter(adapter);
        recycler_songci.setItemAnimator(new DefaultItemAnimator());
        recycler_songci.addItemDecoration(new DividerItemDecoration(SongCiActivity.this,DividerItemDecoration.HORIZONTAL));
    }

    private void initData(){
        StringBuilder builder = new StringBuilder();
        try {
            AssetManager manager = SongCiActivity.this.getAssets();
            BufferedReader reader = new BufferedReader(new InputStreamReader(manager.open("songci.json")));
            String line;
            while ((line = reader.readLine()) != null){
                builder.append(line);
            }

        }catch (IOException e){
            e.printStackTrace();
        }

        String json = builder.toString();

        mList = JSON.parseArray(json,SongCiBean.class);
    }
}
