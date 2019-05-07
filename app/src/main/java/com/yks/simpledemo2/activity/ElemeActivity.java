package com.yks.simpledemo2.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.yks.simpledemo2.R;
import com.yks.simpledemo2.adapter.CategoryAdapter;
import com.yks.simpledemo2.adapter.TeamsAndHeaderAdapter;
import com.yks.simpledemo2.bean.CategoryBean;
import com.yks.simpledemo2.tools.Info;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：仿饿了么外卖点餐的页面
 * 作者：zzh
 * time:2019/04/29
 * 参考网址：https://github.com/heinika/ElmShopByRecyclerView/blob/master/app/src/main/java/com/example/chenlijin/elmshopbyrecyclerview/adapter/TeamsAndHeaderAdapter.java
 */
public class ElemeActivity extends Activity implements CategoryAdapter.OnItemClickListener {

    private Context mContext = ElemeActivity.this;
    private Activity mActivity = ElemeActivity.this;
    private RecyclerView cv_category,cv_teams;
    private List<CategoryBean> categoryList = new ArrayList<>();
    private LinearLayoutManager mCategoryLayoutManager,mTeamsLayoutManager;
    private CategoryAdapter categoryAdapter;
    private TeamsAndHeaderAdapter teamAdapter;
    private int oldSelectedPosition = 0;
    private boolean needMove = false;
    private boolean isChangeByCategoryClick = false;
    private int movePosition;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_eleme);

        initView();
        initData();
        setData();
    }

    private void initView() {
        Info.setActionBar(mActivity,R.id.headerLayout,"饿了么点餐");

        cv_category = findViewById(R.id.cv_category);
        cv_teams = findViewById(R.id.cv_teams);
    }

    private void initData() {
        List<CategoryBean.Team> teamList1 = new ArrayList<>();
        teamList1.add(new CategoryBean.Team("多特蒙德", "http://img1.imgtn.bdimg.com/it/u=1400488354,545185599&fm=21&gp=0.jpg","德甲大黄蜂","$ 100"));
        teamList1.add(new CategoryBean.Team("拜仁慕尼黑", "http://img5.imgtn.bdimg.com/it/u=1016826229,3053766616&fm=21&gp=0.jpg","德甲霸主","$ 150"));
        teamList1.add(new CategoryBean.Team("沃尔夫斯堡", "http://img2.imgtn.bdimg.com/it/u=1102871345,1624426389&fm=15&gp=0.jpg","德甲黑马","$ 80"));
        teamList1.add(new CategoryBean.Team("门兴", "http://c.hiphotos.baidu.com/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=5d24504035fae6cd18b9a3336eda6441/eaf81a4c510fd9f91d25e41e252dd42a2834a493.jpg","不怎么出名","$ 30"));
        CategoryBean c1 = new CategoryBean("德甲", teamList1);
        c1.setSelected(true);

        List<CategoryBean.Team> teamList2 = new ArrayList<>();
        teamList2.add(new CategoryBean.Team("巴塞罗那", "http://www.sinaimg.cn/lf/sports/logo85/130.png","宇宙无敌","$ 350"));
        teamList2.add(new CategoryBean.Team("皇家马德里", "http://www.sinaimg.cn/lf/sports/logo85/157.png","银河战舰","$ 320"));
        teamList2.add(new CategoryBean.Team("马德里竞技", "http://www.sinaimg.cn/lf/sports/logo85/162.png","防守著称","$ 260"));
        CategoryBean c2 = new CategoryBean("西甲", teamList2);

        List<CategoryBean.Team> teamList3 = new ArrayList<>();
        teamList3.add(new CategoryBean.Team("尤文图斯", "http://www.sinaimg.cn/lf/sports/logo85/108.png","意甲老妇人","$ 300"));
        teamList3.add(new CategoryBean.Team("国际米兰", "http://www.sinaimg.cn/lf/sports/logo85/103.png","曾经的米兰王者","$ 280"));
        teamList3.add(new CategoryBean.Team("AC米兰", "http://www.sinaimg.cn/lf/sports/logo85/104.png","前年第二，扶不起的阿斗","$ 260"));
        teamList3.add(new CategoryBean.Team("罗马", "http://www.sinaimg.cn/lf/sports/logo85/111.png","还是不行","$ 260"));
        CategoryBean c3 = new CategoryBean("意甲", teamList3);

        List<CategoryBean.Team> teamList4 = new ArrayList<>();
        teamList4.add(new CategoryBean.Team("曼联", "http://www.sinaimg.cn/lf/sports/logo85/52.png","曾经的英超豪门","$ 350"));
        teamList4.add(new CategoryBean.Team("曼城", "http://www.sinaimg.cn/lf/sports/logo85/216.png","英超豪门","$ 360"));
        teamList4.add(new CategoryBean.Team("切尔西","http://www.sinaimg.cn/lf/sports/logo85/60.png","破车","$ 320"));
        teamList4.add(new CategoryBean.Team("阿森纳","http://www.sinaimg.cn/lf/sports/logo85/61.png","年年增四","$ 320"));
        teamList4.add(new CategoryBean.Team("莱斯特成","http://www.sinaimg.cn/lf/sports/logo85/92.png","英超黑马","$ 280"));
        CategoryBean c4 = new CategoryBean("英超", teamList4);

        List<CategoryBean.Team> teamList5 = new ArrayList<>();
        teamList5.add(new CategoryBean.Team("北京国安", "http://www.sinaimg.cn/ty/2015/0127/U6521P6DT20150127115830.png","绿衫军","￥ 300"));
        teamList5.add(new CategoryBean.Team("广州恒大","http://www.sinaimg.cn/ty/2015/0127/U6521P6DT20150127124548.png","土豪球队","￥ 350"));
        teamList5.add(new CategoryBean.Team("山东鲁能","http://www.sinaimg.cn/ty/2015/0127/U6521P6DT20150127115709.png","青训王者","￥ 320"));
        teamList5.add(new CategoryBean.Team("江苏苏宁","http://www.sinaimg.cn/ty/2016/0108/U6521P6DT20160108153302.png","看着还行","￥ 300"));
        teamList5.add(new CategoryBean.Team("上海上港","http://www.sinaimg.cn/ty/2015/0127/U6521P6DT20150127122231.png","拦路虎","￥ 330"));
        CategoryBean c5 = new CategoryBean("中超", teamList5);


        categoryList.add(c1);
        categoryList.add(c2);
        categoryList.add(c3);
        categoryList.add(c4);
        categoryList.add(c5);
    }

    private void setData(){
        mCategoryLayoutManager = new LinearLayoutManager(mContext);
        mTeamsLayoutManager = new LinearLayoutManager(mContext);
        cv_category.setLayoutManager(mCategoryLayoutManager);
        cv_teams.setLayoutManager(mTeamsLayoutManager);

        categoryAdapter = new CategoryAdapter(mContext,categoryList,R.layout.layout_team_category);
        categoryAdapter.setOnItemClickListener(this);
        cv_category.setAdapter(categoryAdapter);

        teamAdapter = new TeamsAndHeaderAdapter(mContext,categoryList,R.layout.layout_team_detail);
        cv_teams.setAdapter(teamAdapter);
        //给球队添加标题
        final StickyRecyclerHeadersDecoration headerDecor = new StickyRecyclerHeadersDecoration(teamAdapter);
        cv_teams.addItemDecoration(headerDecor);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            cv_teams.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (needMove){
                        needMove = false;
                        //获取要置顶的项在当前屏幕的位置，mIndex是记录的要置顶项在RecyclerView中的位置
                        int n = movePosition - mTeamsLayoutManager.findFirstVisibleItemPosition();
                        if (n >= 0 && n < cv_teams.getChildCount()){
                            ////获取要置顶的项顶部离RecyclerView顶部的距离
                            int top = cv_teams.getChildAt(n).getTop() - dip2px(mContext,28);
                            //移动
                            cv_teams.scrollBy(0,top);
                        }

                        ////第一个完全显示的item和最后一个item。
                        if (isChangeByCategoryClick){
                            isChangeByCategoryClick = false;
                        }else {
                            int firstVisibleItem = mTeamsLayoutManager.findFirstCompletelyVisibleItemPosition();
                            int sort = teamAdapter.getSortType(firstVisibleItem);
                            changeSelected(sort);
                        }
                    }
                }
            });
        }else {
            cv_teams.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (needMove) {
                        needMove = false;
                        //获取要置顶的项在当前屏幕的位置，mIndex是记录的要置顶项在RecyclerView中的位置
                        int n = movePosition - mTeamsLayoutManager.findFirstVisibleItemPosition();
                        if (0 <= n && n < cv_teams.getChildCount()) {
                            //获取要置顶的项顶部离RecyclerView顶部的距离
                            int top = cv_teams.getChildAt(n).getTop() - dip2px(mContext, 28);
                            //最后的移动
                            cv_teams.scrollBy(0, top);
                        }
                    }
                    //第一个完全显示的item和最后一个item。
                    if(!isChangeByCategoryClick){
                        int firstVisibleItem = mTeamsLayoutManager.findFirstCompletelyVisibleItemPosition();
                        int sort = teamAdapter.getSortType(firstVisibleItem);
                        changeSelected(sort);
                    }else {
                        isChangeByCategoryClick = false;
                    }
                }
            });
        }
    }

    @Override
    public void onItemClick(int position) {
        changeSelected(position);
        moveToThisSortFirstItem(position);
        isChangeByCategoryClick = true;
    }

    private void changeSelected(int position){
        categoryList.get(oldSelectedPosition).setSelected(false);
        categoryList.get(position).setSelected(true);
        //增加左侧联动
        cv_category.scrollToPosition(position);
        oldSelectedPosition = position;
        categoryAdapter.notifyDataSetChanged();
    }

    private void moveToThisSortFirstItem(int position){
        movePosition = 0;
        for (int i=0;i<position;i++){
            movePosition += teamAdapter.getCategoryList().get(i).getTeamList().size();
        }
        moveToPosition(movePosition);
    }

    private void moveToPosition(int position){
        //先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
        int firstItem = mTeamsLayoutManager.findFirstVisibleItemPosition();
        int lastItem = mTeamsLayoutManager.findLastVisibleItemPosition();
        //区分情况
        if (position <= firstItem){
            //当要置顶的项在当前显示的第一个项的前面时
            cv_teams.scrollToPosition(position);
        }else if (position <= lastItem){
            //当要置顶的项已经在屏幕中显示时
            int top = cv_teams.getChildAt(position - firstItem).getTop();
            cv_teams.scrollBy(0,top-dip2px(mContext,28));
        }else {
            //当要置顶的项在当前显示的最后一项的后面时
            cv_teams.scrollToPosition(position);
            movePosition = position;
            needMove = true;
        }
    }

    /**
     * 根据手机分辨率从dp转成px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
