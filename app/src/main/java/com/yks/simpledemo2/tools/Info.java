package com.yks.simpledemo2.tools;

import android.content.Context;
import android.util.Log;

import net.lemonsoft.lemonbubble.LemonBubble;

/**
 * 描述：公共方法封装
 * 作者：zzh
 * Created by admin on 2018/8/21.
 */

public class Info {

    /**
     * 弹框提示
     * @param msg 提示的内容
     * @param isRight 提示的类型（成功或失败）
     */
    public static void showToast(Context context,String msg , boolean isRight){
        if (isRight){
            LemonBubble.showRight(context, msg, 1500);
        }else {
            LemonBubble.showError(context, msg, 1500);
        }
    }

    /**
     * 描述：打印输出
     * 作者：zzh
     * @param flag 标记
     * @param msg 打印内容
     */
    public static void showLog(String flag,String msg){
        Log.d(flag,msg);
    }
}
