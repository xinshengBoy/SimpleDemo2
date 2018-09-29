package com.yks.simpledemo2.bean;

/**
 * 描述：诗经的就送对象
 * 作者：zzh
 * time:2018/09/29
 */

public class ShiJingBean {
    private String title;//标题
    private String chapter;//风格
    private String section;//时代
    private String content;//内容

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
