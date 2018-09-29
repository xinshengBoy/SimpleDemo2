package com.yks.simpledemo2.bean;

/**
 * 描述：宋词的json对象
 * 作者：zzh
 * time:2018/09/29
 */

public class SongCiBean {
    private String author;//作者
    private String rhythmic;//标题
    private String paragraphs;//内容

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getRhythmic() {
        return rhythmic;
    }

    public void setRhythmic(String rhythmic) {
        this.rhythmic = rhythmic;
    }

    public String getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(String paragraphs) {
        this.paragraphs = paragraphs;
    }
}
