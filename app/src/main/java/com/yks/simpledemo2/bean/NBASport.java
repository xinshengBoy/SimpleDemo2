package com.yks.simpledemo2.bean;

/**
 * 描述：
 * 作者：
 * time:2018/10/18
 */

public class NBASport {
    private String homeTeam;//主队
    private String customTeam;//客队
    private String score;//比分
    private String homeLogo;//主队队徽
    private String customLogo;//客队队徽
    private String time;//比赛时间
    private String status;//比赛状态 0：未开赛。1：直播中。2：已结束
    private String videoLink;//集锦链接

    public NBASport(String homeTeam, String customTeam, String score, String homeLogo, String customLogo, String time, String status, String videoLink) {
        this.homeTeam = homeTeam;
        this.customTeam = customTeam;
        this.score = score;
        this.homeLogo = homeLogo;
        this.customLogo = customLogo;
        this.time = time;
        this.status = status;
        this.videoLink = videoLink;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getCustomTeam() {
        return customTeam;
    }

    public String getScore() {
        return score;
    }

    public String getHomeLogo() {
        return homeLogo;
    }

    public String getCustomLogo() {
        return customLogo;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }

    public String getVideoLink() {
        return videoLink;
    }
}
