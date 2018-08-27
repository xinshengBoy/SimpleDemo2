package com.example.zhongzhihua.widget;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhongzhihua.R;

/**
 * 标题头，具体使用可见CheackEarPhone.class
 * @author user
 *
 */
public class MyActionBar{

	private static View view;
	public static View actionbar(final Activity activity, LinearLayout linear, Bundle bundle) {
		view = LayoutInflater.from(activity).inflate(R.layout.actionbar,null);
		linear.setBackgroundColor(Color.BLACK);
//		linear.getLayoutParams().height = 100;
		//中间标题
		TextView actionbar_title = (TextView)view.findViewById(R.id.actionbar_title);
		actionbar_title.setText(bundle.getString("title", "--"));
		//返回图标
		if (bundle.getBoolean("back",true)) {
			view.findViewById(R.id.actionbar_back).setVisibility(View.VISIBLE);
		}else {
			view.findViewById(R.id.actionbar_back).setVisibility(View.GONE);
		}
		//左边文字
		if (bundle.getString("leftText") == null) {
			view.findViewById(R.id.actionbar_left).setVisibility(View.GONE);
		}else {
			view.findViewById(R.id.actionbar_left).setVisibility(View.VISIBLE);
			TextView actionbar_left = (TextView) view.findViewById(R.id.actionbar_left);
			actionbar_left.setText(bundle.getString("leftText"));
		}
		//右边图标
		if (bundle.getBoolean("rightImage",true)) {
			view.findViewById(R.id.actionbar_right_img).setVisibility(View.VISIBLE);
		}else {
			view.findViewById(R.id.actionbar_right_img).setVisibility(View.GONE);
		}
		//右边文字
		if (bundle.getString("rightText") == null) {
			view.findViewById(R.id.actionbar_right).setVisibility(View.GONE);
		}else {
			view.findViewById(R.id.actionbar_right).setVisibility(View.VISIBLE);
			TextView actionbar_right = (TextView) view.findViewById(R.id.actionbar_right);
			actionbar_right.setText(bundle.getString("rightText"));
		}

		linear.setOrientation(LinearLayout.HORIZONTAL);
		linear.setGravity(Gravity.CENTER_VERTICAL);

		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		// activity.addContentView(view,params);
		linear.addView(view, params);

		view.findViewById(R.id.actionbar_back).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				activity.finish();
			}
		});
		return view;
	}

	/**
	 * actionbar左侧图片的点击接口调用
	 */
	public interface LeftImgListener{
		void onLeftImgClick();
	}
	/**
	 * actionbar左侧文字的点击接口调用
	 */
	public interface LeftListener{
		void onLeftClick();
	}
	/**
	 * actionbar中间标题的点击接口调用
	 */
	public interface TitleListener{
		void onTitleClick();
	}
	/**
	 * actionbar右侧文字的点击接口调用
	 */
	public interface RightListener{
		void onRightClick();
	}
	/**
	 * actionbar右侧图片的点击接口调用
	 */
	public interface RightImgListener{
		void oRightImgClick();
	}
	/**
	 * actionbar左侧图片的点击事件（返回）
	 * @param listener 点击事件
	 */
	public static void LeftImgBar(final LeftImgListener listener){
		view.findViewById(R.id.actionbar_back).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				listener.onLeftImgClick();
			}
		});
	}
	/**
	 * actionbar左侧文字的点击事件
	 * @param listener 点击事件
     */
	public static void LeftBar(final LeftListener listener){
		view.findViewById(R.id.actionbar_left).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				listener.onLeftClick();
			}
		});
	}
	/**
	 * actionbar中间标题的点击事件
	 * @param listener 点击事件
	 */
	public static void TitleBar(final TitleListener listener){
		view.findViewById(R.id.actionbar_title).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				listener.onTitleClick();
			}
		});
	}
	/**
	 * actionbar右侧文字的点击事件
	 * @param listener 点击事件
	 */
	public static void RightBar(final RightListener listener){
		view.findViewById(R.id.actionbar_right).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				listener.onRightClick();
			}
		});
	}
	/**
	 * actionbar右侧图片的点击事件
	 * @param listener 点击事件
	 */
	public static void RightImgBar(final RightImgListener listener){
		view.findViewById(R.id.actionbar_right_img).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				listener.oRightImgClick();
			}
		});

	}
}
