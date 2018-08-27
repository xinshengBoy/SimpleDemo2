package com.yks.simpledemo2.tools;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 描述：toast的方法封装
 * 作者：zzh
 * time:2018/08/24
 */

public class ToastUtils {

    public static Toast mToast;

    /**
     * 描述：传入文字
     * @param context 上下文
     * @param text 要显示的文字
     */
    public static void show(Context context,String text){
        if (mToast == null){
            mToast = Toast.makeText(context,text,Toast.LENGTH_SHORT);
        }else {
            mToast.setText(text);
        }
        mToast.setGravity(Gravity.CENTER,0,0);
        mToast.show();
    }

    /**
     * 描述：传入资源文件
     * @param context 上下文
     * @param resId 资源文件
     */
    public static void show(Context context,int resId){
        if (mToast == null){
            mToast = Toast.makeText(context,resId,Toast.LENGTH_SHORT);
        }else {
            mToast.setText(resId);
        }
        mToast.setGravity(Gravity.CENTER,0,0);
        mToast.show();
    }

    /**
     * 描述：传入文字带图片
     * @param context 上下文
     * @param text 文字内容
     * @param resImg 图片资源
     */
    public static void showImg(Context context,String text,int resImg){
        if (mToast == null){
            mToast = Toast.makeText(context,text,Toast.LENGTH_SHORT);
        }else {
            mToast.setText(text);
        }

        LinearLayout view = (LinearLayout) mToast.getView();
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(resImg);
        view.addView(imageView);

        mToast.show();
    }
}
