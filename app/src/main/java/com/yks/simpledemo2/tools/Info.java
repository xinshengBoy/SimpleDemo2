package com.yks.simpledemo2.tools;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import net.lemonsoft.lemonbubble.LemonBubble;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * 描述：判断输入的文字是否是汉字
     * 作者：zzh
     * @param input 输入的文字
     * @return 返回的结果
     */
    public static boolean isHanzi(String input){
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(input);
        if (m.find()){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 描述：md5加密
     * 作者：zzh
     * @param string 要加密的字符
     * @return 加密后的结果
     */
    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
