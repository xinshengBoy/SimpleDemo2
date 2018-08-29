package com.yks.simpledemo2.bean;

/**
 * 描述：
 * 作者：
 * time:2018/08/28
 */

public class BaiduBean {
    private String code;//简写
    private String name;//名称

    public BaiduBean(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
