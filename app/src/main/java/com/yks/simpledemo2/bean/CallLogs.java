package com.yks.simpledemo2.bean;

/**
 * 描述：通话记录bean
 * 作者：zzh
 * time:2018/10/26
 */

public class CallLogs {
    private String userName;//联系人
    private String number;//电话号码
    private String date;//日期
    private String duration;//通话时长
    private String type;//通话类型

    public CallLogs(String userName, String number, String date, String duration, String type) {
        this.userName = userName;
        this.number = number;
        this.date = date;
        this.duration = duration;
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public String getNumber() {
        return number;
    }

    public String getDate() {
        return date;
    }

    public String getDuration() {
        return duration;
    }

    public String getType() {
        return type;
    }
}
