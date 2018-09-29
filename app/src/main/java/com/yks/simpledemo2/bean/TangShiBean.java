package com.yks.simpledemo2.bean;

/**
 * 描述：唐诗的json对象
 * 作者：zzh
 * time:2018/09/29
 */

public class TangShiBean {
    private String author;//作者
    private String title;//标题
    private String paragraphs;//内容

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(String paragraphs) {
        this.paragraphs = paragraphs;
    }
}
