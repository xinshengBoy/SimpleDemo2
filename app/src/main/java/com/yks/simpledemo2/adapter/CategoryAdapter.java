package com.yks.simpledemo2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yks.simpledemo2.R;
import com.yks.simpledemo2.bean.CategoryBean;

import java.util.List;

/**
 * 描述：左侧的分类适配器
 * 作者：zzh
 * time:2019/04/30
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{

    private Context context;
    private List<CategoryBean> mData;
    private int itemLayoutId;
    private OnItemClickListener onItemClickListener;

    public CategoryAdapter (Context context,List<CategoryBean> mData,int itemLayoutId){
        this.context = context;
        this.mData = mData;
        this.itemLayoutId = itemLayoutId;
    }

    public void setCategoryList(List<CategoryBean> list){
        this.mData = list;
        notifyDataSetChanged();
    }

    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(itemLayoutId,null));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.ViewHolder holder, int position) {
        holder.left_category_name.setText(mData.get(position).getSortName());
        holder.left_category_name.setSelected(mData.get(position).isSelected());
        holder.left_category_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null){
                    onItemClickListener.onItemClick(position);
                }
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView left_category_name;
        public ViewHolder(View itemView) {
            super(itemView);
            left_category_name = itemView.findViewById(R.id.left_category_name);
        }
    }

}
