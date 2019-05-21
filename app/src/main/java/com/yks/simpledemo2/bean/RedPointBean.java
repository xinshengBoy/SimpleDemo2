package com.yks.simpledemo2.bean;

/**
 * 描述：仿微信订阅号列表的小红点
 * 作者：zzh
 * time:2019/05/07
 */
public class RedPointBean {
    private String imgUrl;//图标地址
    private boolean isRead;//是否已阅读过，未阅读显示小红点
    private String mediaName;//公众号名称
    private String mediaDesc;//新消息描述
    private String mediaTime;//新消息发布时间

    public RedPointBean(String imgUrl, boolean isRead, String mediaName, String mediaDesc, String mediaTime) {
        this.imgUrl = imgUrl;
        this.isRead = isRead;
        this.mediaName = mediaName;
        this.mediaDesc = mediaDesc;
        this.mediaTime = mediaTime;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public boolean isRead() {
        return isRead;
    }

    public String getMediaName() {
        return mediaName;
    }

    public String getMediaDesc() {
        return mediaDesc;
    }

    public String getMediaTime() {
        return mediaTime;
    }
}
