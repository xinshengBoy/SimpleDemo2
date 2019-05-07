package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yks.simpledemo2.R;
import com.yks.simpledemo2.bean.NbaScoreBean;
import com.yks.simpledemo2.tools.CommonAdapter;
import com.yks.simpledemo2.tools.Info;
import com.yks.simpledemo2.tools.ViewHolder;
import com.yks.simpledemo2.widget.MyHorizontalScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：实现NBA球员数据统计，左侧和顶部标题头固定，右侧可滑动
 * 参考：https://github.com/ddssingsong/StockLinkScrool/blob
 * 作者：zzh
 * time:2019/04/29
 */
public class TotalNBAScoreActivity extends Activity {

    private Context mContext = TotalNBAScoreActivity.this;
    private MyHorizontalScrollView title_horsv,content_horsv;
    private ListView lv_left_container,lv_right_container;
    private List<NbaScoreBean> mList = new ArrayList<>();
    private List<String> leftList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_nbascore);

        findView();
        initData();
        initView();
    }

    private void findView() {
        Info.setActionBar(this,R.id.headerLayout,"NBA球员数据");

        title_horsv = findViewById(R.id.title_horsv);
        content_horsv = findViewById(R.id.content_horsv);
        lv_left_container = findViewById(R.id.lv_left_container);
        lv_right_container = findViewById(R.id.lv_right_container);
    }

    private void initData() {
        NbaScoreBean bean1 = new NbaScoreBean("戈登","小前锋","36:45","10-19","4-13","3-4","0","4","4","0","0","2","2","0","-5","27");
        NbaScoreBean bean2 = new NbaScoreBean("塔克","大前锋","39:01","0-4","0-3","0-0","1","2","3","1","4","0","1","5","9","0");
        NbaScoreBean bean3 = new NbaScoreBean("卡佩拉","中锋","26:49","1-2","0-0","2-2","0","6","6","2","0","0","0","2","-17","4");
        NbaScoreBean bean4 = new NbaScoreBean("哈登","得分后卫","38:40","9-28","4-16","13-14","0","4","4","6","3","0","4","3","3","35");
        NbaScoreBean bean5 = new NbaScoreBean("保罗","控球后卫","36:11","5-9","3-6","4-7","1","2","3","4","4","0","4","3","3","17");
        NbaScoreBean bean6 = new NbaScoreBean("豪斯","","20:54","1-4","1-4","0-0","0","3","3","0","0","0","1","2","3","3");
        NbaScoreBean bean7 = new NbaScoreBean("香珀特","","20:54","1-4","1-4","0-0","0","1","1","0","1","0","0","4","3","3");
        NbaScoreBean bean8 = new NbaScoreBean("内内","","13:34","3-3","0-0","2-2","1","1","2","1","3","0","0","3","7","8");
        NbaScoreBean bean9 = new NbaScoreBean("格林","","07:12","1-1","1-1","0-0","0","0","0","0","0","1","1","1","-16","3");
        NbaScoreBean bean10 = new NbaScoreBean("切奥扎","","00:00","0-0","0-0","0-0","0","0","0","0","0","0","0","0","0","0");
        NbaScoreBean bean11 = new NbaScoreBean("克拉克","","00:00","0-0","0-0","0-0","0","0","0","0","0","0","0","0","0","0");
        NbaScoreBean bean12 = new NbaScoreBean("法里埃德","","00:00","0-0","0-0","0-0","0","0","0","0","0","0","0","0","0","0");
        NbaScoreBean bean13 = new NbaScoreBean("哈尔滕斯泰因","","00:00","0-0","0-0","0-0","0","0","0","0","0","0","0","0","0","0");

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
        mList.add(bean12);
        mList.add(bean13);

        for (int i=0;i<mList.size();i++){
            leftList.add(mList.get(i).getPlayer());
        }
    }

    private void initView() {
        //设置联动
        title_horsv.setScrollView(content_horsv);
        content_horsv.setScrollView(title_horsv);
        //添加左侧数据
        LeftAdapter leftAdapter = new LeftAdapter(mContext,leftList, R.layout.layout_item_left);
        lv_left_container.setAdapter(leftAdapter);
        setListViewHeightBasedOnChildren(lv_left_container);
        //添加右侧数据
        RightAdapter rightAdapter = new RightAdapter(mContext,mList,R.layout.layout_item_right);
        lv_right_container.setAdapter(rightAdapter);
        setListViewHeightBasedOnChildren(lv_right_container);
    }

    private class LeftAdapter extends CommonAdapter<String>{

        public LeftAdapter(Context context, List<String> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, String item) {
            TextView left_container_name = helper.getView(com.yks.simpledemo2.R.id.left_container_name);
            left_container_name.setText(item);
        }
    }

    private class RightAdapter extends CommonAdapter<NbaScoreBean>{

        public RightAdapter(Context context, List<NbaScoreBean> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, NbaScoreBean item) {
            TextView right_item1 = helper.getView(R.id.right_item1);
            TextView right_item2 = helper.getView(R.id.right_item2);
            TextView right_item3 = helper.getView(R.id.right_item3);
            TextView right_item4 = helper.getView(R.id.right_item4);
            TextView right_item5 = helper.getView(R.id.right_item5);
            TextView right_item6 = helper.getView(R.id.right_item6);
            TextView right_item7 = helper.getView(R.id.right_item7);
            TextView right_item8 = helper.getView(R.id.right_item8);
            TextView right_item9 = helper.getView(R.id.right_item9);
            TextView right_item10 = helper.getView(R.id.right_item10);
            TextView right_item11 = helper.getView(R.id.right_item11);
            TextView right_item12 = helper.getView(R.id.right_item12);
            TextView right_item13 = helper.getView(R.id.right_item13);
            TextView right_item14 = helper.getView(R.id.right_item14);
            TextView right_item15 = helper.getView(R.id.right_item15);

            right_item1.setText(item.getPosition());
            right_item2.setText(item.getCostTime());
            right_item3.setText(item.getShoot());
            right_item4.setText(item.getThreePoint());
            right_item5.setText(item.getPenaltyShoot());
            right_item6.setText(item.getFrontRebound());
            right_item7.setText(item.getDefensiveRebound());
            right_item8.setText(item.getTotalRebound());
            right_item9.setText(item.getAssists());
            right_item10.setText(item.getSteals());
            right_item11.setText(item.getBlock());
            right_item12.setText(item.getMistake());
            right_item13.setText(item.getFoul());
            right_item14.setText(item.getPlusMinus());
            right_item15.setText(item.getScore());
        }
    }

    /**
     * 计算ListView的高度
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        /**
         * getAdapter这个方法主要是为了获取到ListView的数据条数，所以设置之前必须设置Adapter
         */
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {

            View listItem = listAdapter.getView(i, null, listView);
            //计算每一项的高度
            listItem.measure(0, 0);
            //总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        //真正的高度需要加上分割线的高度
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
