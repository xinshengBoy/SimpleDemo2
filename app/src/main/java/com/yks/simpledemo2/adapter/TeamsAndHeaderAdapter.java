package com.yks.simpledemo2.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.yks.simpledemo2.R;
import com.yks.simpledemo2.bean.CategoryBean;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * 作者：
 * time:2019/04/30
 */
public class TeamsAndHeaderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<CategoryBean> mData;
    private List<CategoryBean.Team> teamList = new ArrayList<>();
    private int itemLayoutId;

    public TeamsAndHeaderAdapter(Context context,List<CategoryBean> data,int itemLayoutId){
        this.context = context;
        this.itemLayoutId = itemLayoutId;
        setCategoryList(data);
    }

    public void setCategoryList(List<CategoryBean> list){
        this.mData = list;
        for (int i=0;i<mData.size();i++){
            if (teamList != null){
                teamList.addAll(mData.get(i).getTeamList());
            }
        }
        notifyDataSetChanged();
    }

    public List<CategoryBean> getCategoryList(){
        return mData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return new HeaderViewHolder(LayoutInflater.from(context).inflate(R.layout.header_team_list,parent,false));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(itemLayoutId,parent,false));
    }

    @Override
    public int getItemCount() {
        return teamList == null ? 0 : teamList.size();
    }

    /**
     * 返回值相同会被默认为同一项
     * @param position
     * @return
     */
    @Override
    public long getHeaderId(int position) {
        return getSortType(position);
    }

    public int getSortType(int position){
        int sort = -1;
        int sum = 0;
        for (int i=0;i<mData.size();i++){
            if (position >= sum){
                sort ++;
            }else {
                return sort;
            }
            sum += mData.get(i).getTeamList().size();
        }
        return sort;
    }

    private int getRandomColor() {
        SecureRandom rgen = new SecureRandom();
        return Color.HSVToColor(150, new float[]{
                rgen.nextInt(359), 1, 1
        });
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextView tv_team_title = (TextView) holder.itemView;
        tv_team_title.setText(mData.get(getSortType(position)).getSortName());
        tv_team_title.setBackgroundColor(getRandomColor());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CategoryViewHolder viewHolder = (CategoryViewHolder) holder;
        viewHolder.txt_team_name.setText(teamList.get(position).getName());
        viewHolder.txt_team_dec.setText(teamList.get(position).getDec());
        viewHolder.txt_team_price.setText(teamList.get(position).getPrice());
        Glide.with(context).load(teamList.get(position).getImgPath()).placeholder(R.mipmap.frog_logo).centerCrop().into(viewHolder.iv_team);
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class CategoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_team;
        private TextView txt_team_name,txt_team_dec,txt_team_price;
        public CategoryViewHolder(View itemView) {
            super(itemView);
            iv_team = itemView.findViewById(R.id.iv_team);
            txt_team_name = itemView.findViewById(R.id.txt_team_name);
            txt_team_dec = itemView.findViewById(R.id.txt_team_dec);
            txt_team_price = itemView.findViewById(R.id.txt_team_price);
        }
    }
}
