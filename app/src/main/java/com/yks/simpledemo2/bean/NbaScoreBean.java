package com.yks.simpledemo2.bean;

/**
 * 描述：NBA球员数据bean
 * 作者：zzh
 * time:2019/04/29
 */
public class NbaScoreBean {

    private String player;//球员
    private String position;//位置
    private String costTime;//出场时间
    private String shoot;//投篮
    private String threePoint;//三分
    private String penaltyShoot;//罚球
    private String frontRebound;//前篮板
    private String defensiveRebound;//后篮板
    private String totalRebound;//总篮板
    private String assists;//助攻
    private String steals;//抢断
    private String block;//盖帽
    private String mistake;//失误
    private String foul;//犯规
    private String plusMinus;//正负值
    private String score;//得分

    public NbaScoreBean(String player, String position, String costTime, String shoot, String threePoint, String penaltyShoot, String frontRebound, String defensiveRebound, String totalRebound, String assists, String steals, String block, String mistake, String foul, String plusMinus, String score) {
        this.player = player;
        this.position = position;
        this.costTime = costTime;
        this.shoot = shoot;
        this.threePoint = threePoint;
        this.penaltyShoot = penaltyShoot;
        this.frontRebound = frontRebound;
        this.defensiveRebound = defensiveRebound;
        this.totalRebound = totalRebound;
        this.assists = assists;
        this.steals = steals;
        this.block = block;
        this.mistake = mistake;
        this.foul = foul;
        this.plusMinus = plusMinus;
        this.score = score;
    }

    public String getPlayer() {
        return player;
    }

    public String getPosition() {
        return position;
    }

    public String getCostTime() {
        return costTime;
    }

    public String getShoot() {
        return shoot;
    }

    public String getThreePoint() {
        return threePoint;
    }

    public String getPenaltyShoot() {
        return penaltyShoot;
    }

    public String getFrontRebound() {
        return frontRebound;
    }

    public String getDefensiveRebound() {
        return defensiveRebound;
    }

    public String getTotalRebound() {
        return totalRebound;
    }

    public String getAssists() {
        return assists;
    }

    public String getSteals() {
        return steals;
    }

    public String getBlock() {
        return block;
    }

    public String getMistake() {
        return mistake;
    }

    public String getFoul() {
        return foul;
    }

    public String getPlusMinus() {
        return plusMinus;
    }

    public String getScore() {
        return score;
    }
}
