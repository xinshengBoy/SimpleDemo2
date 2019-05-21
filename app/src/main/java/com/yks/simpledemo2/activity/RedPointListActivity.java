package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yks.simpledemo2.R;
import com.yks.simpledemo2.bean.RedPointBean;
import com.yks.simpledemo2.tools.BaseRecyclerAdapter;
import com.yks.simpledemo2.tools.Info;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：实现列表的小红点，仿微信订阅号列表
 * 作者：zzh
 * time:2019/05/07
 */
public class RedPointListActivity extends Activity {

    private Context mContext = RedPointListActivity.this;
    private RecyclerView rc_redpoint;
    private List<RedPointBean> mList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_redpoint);

        initView();
        initData();
    }

    private void initView(){
        Info.setActionBar(RedPointListActivity.this,R.id.headerLayout,"列表小红点");

        rc_redpoint = findViewById(R.id.rc_redpoint);
        //横向滚动
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rc_redpoint.setLayoutManager(manager);
        rc_redpoint.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));//默认的分割线
    }

    private void initData(){
        RedPointBean bean1 = new RedPointBean("http://pics.sc.chinaz.com/Files/pic/icons128/7334/q1.png",false,"汝城网","招募公告|郴汽集团汝城分公司招募出租车合伙人（文末有福利）","13:49");

        RedPointBean bean2 = new RedPointBean("http://pics.sc.chinaz.com/Files/pic/icons128/7334/q10.png",false,"程序人生","真正勇猛的程序员，敢于让鲁迅崩溃！","13:12");

        RedPointBean bean3 = new RedPointBean("http://pics.sc.chinaz.com/Files/pic/icons128/7334/q2.png",true,"深圳最生活","免费试睡|准备了600套网红名宿，邀请你来睡","11:43");

        RedPointBean bean4 = new RedPointBean("http://pics.sc.chinaz.com/Files/pic/icons128/7334/q3.png",false,"爱心文摘","32岁复旦女博士临终遗言：人一生最该看透的3件事","00:15");

        RedPointBean bean5 = new RedPointBean("http://pics.sc.chinaz.com/Files/pic/icons128/7326/n1.png",false,"鲤伴","郭德纲徒弟脑出血众筹百万：车房不能卖，命不能丢，但我可以不要脸","昨天");

        RedPointBean bean6 = new RedPointBean("http://pics.sc.chinaz.com/Files/pic/icons128/7326/n10.png",true,"腾讯体育","34岁保罗防的库里找不到篮筐 明天的火勇大战他将如何庆生","昨天");
        RedPointBean bean7 = new RedPointBean("http://pics.sc.chinaz.com/Files/pic/icons128/7326/n2.png",true,"讲武堂","34岁保罗防的库里找不到篮筐 明天的火勇大战他将如何庆生","昨天");
        RedPointBean bean8 = new RedPointBean("http://pics.sc.chinaz.com/Files/pic/icons128/7326/n3.png",true,"今日泉水","34岁保罗防的库里找不到篮筐 明天的火勇大战他将如何庆生","昨天");
        RedPointBean bean9 = new RedPointBean("http://pics.sc.chinaz.com/Files/pic/icons128/7326/n4.png",true,"广州发现","34岁保罗防的库里找不到篮筐 明天的火勇大战他将如何庆生","昨天");
        RedPointBean bean10 = new RedPointBean("http://pics.sc.chinaz.com/Files/pic/icons128/7326/n5.png",true,"想念熊","34岁保罗防的库里找不到篮筐 明天的火勇大战他将如何庆生","昨天");
        RedPointBean bean11 = new RedPointBean("http://pics.sc.chinaz.com/Files/pic/icons128/7326/n6.png",true,"PS报销精选","34岁保罗防的库里找不到篮筐 明天的火勇大战他将如何庆生","昨天");

        mList.add(bean1);
        mList.add(bean2);
        mList.add(bean3);
        mList.add(bean4);
        mList.add(bean5);
        mList.add(bean6);
        mList.add(bean7);
        mList.add(bean8);
        mList.add(bean9);
        mList.add(bean10);
        mList.add(bean11);

        RedPointAdapter adapter = new RedPointAdapter(mList,R.layout.item_redpoint);
        rc_redpoint.setAdapter(adapter);
    }

    private class RedPointAdapter extends BaseRecyclerAdapter<RedPointBean>{

        public RedPointAdapter(List<RedPointBean> mDatas, int itemId) {
            super(mDatas, itemId);
        }

        @Override
        protected void bindData(BaseViewHolder holder, int position, RedPointBean redPointBean) {
            ImageView iv_red_icon = (ImageView) holder.getView(R.id.iv_red_icon);
            ImageView iv_redpoint = (ImageView) holder.getView(R.id.iv_redpoint);
            TextView txt_red_name = (TextView) holder.getView(R.id.txt_red_name);
            TextView txt_red_desc = (TextView) holder.getView(R.id.txt_red_desc);
            TextView txt_red_date = (TextView) holder.getView(R.id.txt_red_date);

            txt_red_name.setText(redPointBean.getMediaName());
            txt_red_desc.setText(redPointBean.getMediaDesc());
            txt_red_date.setText(redPointBean.getMediaTime());
            if (redPointBean.isRead()){
                iv_redpoint.setVisibility(View.GONE);
            }else {
                iv_redpoint.setVisibility(View.VISIBLE);
            }
            Picasso.with(mContext).load(redPointBean.getImgUrl()).into(iv_red_icon);
//            Glide.with(mContext).asBitmap().load(redPointBean.getImgUrl()).placeholder(R.mipmap.frog_logo).into(iv_red_icon);
        }
    }
}
