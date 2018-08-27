package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yks.simpledemo2.R;
import com.yks.simpledemo2.bean.RecyclerBean;
import com.yks.simpledemo2.tools.Info;
import com.yks.simpledemo2.widget.MyActionBar;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：仪表盘
 * 作者：zzh
 * 参考网址：https://github.com/glomadrian/velocimeter-view
 * Created by admin on 2018/8/21.
 */

public class RecyclerviewrActivity extends Activity {

    private SwipeRefreshLayout view_swipe;
    private RecyclerView view_recycler;
    private List<RecyclerBean> mList = new ArrayList<>();
    private CommonAdapter<RecyclerBean> adapter;
    private HeaderAndFooterWrapper headwrapper;
    private LoadMoreWrapper endwrapper;
    private int positions = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recyclerview);
        initView();
    }

    private void initView() {
        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "RecyclerView");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        view_swipe = findViewById(R.id.view_swipe);
        view_recycler = findViewById(R.id.view_recycler);

        //swiperefresh的最大下拉位置
        view_swipe.setProgressViewOffset(true,50,200);
        //设置下拉的圆圈的颜色
        view_swipe.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        //横向滚动
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        view_recycler.setLayoutManager(manager);
        //设置分割线
//        view_recycler.addItemDecoration(new DividerItemDecoration(RecyclerviewrActivity.this,DividerItemDecoration.VERTICAL));//默认的分割线
        DividerItemDecoration divider = new DividerItemDecoration(RecyclerviewrActivity.this,DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this,R.drawable.custom_divider));
        view_recycler.addItemDecoration(divider);
        //如果可以确定每个items的高度，设置这个能提高性能
        view_recycler.setHasFixedSize(true);
        initData();
        //设置适配器
        adapter = new CommonAdapter<RecyclerBean>(RecyclerviewrActivity.this,R.layout.item_recyclerview,mList) {
            @Override
            protected void convert(ViewHolder holder, RecyclerBean recyclerBean, int position) {
                holder.setText(R.id.txt_recycler_title,recyclerBean.getTitle());
                holder.setText(R.id.txt_recycler_write,recyclerBean.getWrite());
            }
        };
        //设置下拉刷新的头部
        initHeaderAndFooter();

        endwrapper = new LoadMoreWrapper(headwrapper);
        endwrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        RecyclerBean bean = new RecyclerBean();
                        bean.setTitle("这是加载的第"+positions+"条数据哟");
                        bean.setWrite("钟志华"+positions);
                        mList.add(bean);
                        positions += 1;
                        endwrapper.notifyDataSetChanged();
//                        adapter.notifyDataSetChanged();
                    }
                },3000);
            }
        });
        view_recycler.setAdapter(endwrapper);
        adapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                TextView txt = view.findViewById(R.id.txt_recycler_title);
                String a = txt.getText().toString().trim();
                Info.showToast(RecyclerviewrActivity.this,a,true);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        view_swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        RecyclerBean bean = new RecyclerBean();
                        bean.setTitle("这是加载的第"+positions+"条数据哟");
                        bean.setWrite("钟志华"+positions);
                        mList.add(0,bean);
                        positions += 1;

                        adapter.notifyDataSetChanged();
                        view_swipe.setRefreshing(false);
                    }
                },500);
            }
        });
    }

    //初始化数据
    private void initData(){
        for (int i=1;i<20;i++){
            RecyclerBean bean = new RecyclerBean();
            bean.setTitle("庆祝中国人民共和国第"+i+"次全国人民代表大会隆重开幕");
            bean.setWrite("钟志华（2"+i+"）");
            mList.add(bean);
        }
    }
    //下拉刷新
    private void initHeaderAndFooter(){
        headwrapper = new HeaderAndFooterWrapper(adapter);
        TextView txt1 = new TextView(this);
        txt1.setText("Header 1");
        TextView txt2 = new TextView(this);
        txt2.setText("Header 2");
        headwrapper.addHeaderView(txt1);
        headwrapper.addHeaderView(txt2);
    }
}
