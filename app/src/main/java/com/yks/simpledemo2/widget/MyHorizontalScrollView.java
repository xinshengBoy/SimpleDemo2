package com.yks.simpledemo2.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

/**
 * 描述：自定义横向滑动的view
 * 作者：zzh
 * time:2019/04/29
 */
public class MyHorizontalScrollView extends HorizontalScrollView {
    private View mView;
    public MyHorizontalScrollView(Context context) {
        super(context);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mView != null){
            mView.scrollTo(l,t);
        }
    }

    /**
     * 描述：设置联动的view
     * 作者：zzh
     * @param view 视图
     */
    public void setScrollView(View view){
        mView = view;
    }
}
