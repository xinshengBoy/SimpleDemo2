package com.yks.simpledemo2.bean;

import java.util.List;

/**
 * 描述：仿饿了么点餐bean
 * 作者：zzh
 * time:2019/04/29
 */
public class CategoryBean {
    private String sortName;//菜单栏
    private boolean selected;//选中
    private List<Team> teamList;//菜单详情

    public CategoryBean(String sortName, List<Team> teamList) {
        this.sortName = sortName;
        this.teamList = teamList;
    }

    public static class Team {
        private String imgPath;
        private String name;
        private String dec;
        private String price;

        public Team(String name, String imgPath,String dec,String price) {
            this.name = name;
            this.imgPath = imgPath;
            this.dec = dec;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public String getImgPath() {
            return imgPath;
        }

        public String getDec() {
            return dec;
        }

        public String getPrice() {
            return price;
        }
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public List<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Team> teamList) {
        this.teamList = teamList;
    }
}
