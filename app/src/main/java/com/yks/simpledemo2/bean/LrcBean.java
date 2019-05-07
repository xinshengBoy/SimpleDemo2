package com.yks.simpledemo2.bean;

/**
 * 描述：歌词解析bean
 * 作者：zzh
 * time:2019/03/29
 */
public class LrcBean {
    private String lrc;
    private long start;
    private long end;

    public String getLrc() {
        return lrc;
    }

    public void setLrc(String lrc) {
        this.lrc = lrc;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }
}
