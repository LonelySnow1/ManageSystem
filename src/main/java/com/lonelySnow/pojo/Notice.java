package com.lonelySnow.pojo;

import java.time.LocalDateTime;

public class Notice {
    // 主键
    private Integer id;
    // 发布时间
    private LocalDateTime time;
    // 标题
    private String title;
    // 详细内容
    private String detail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "notice{" +
                "id=" + id +
                ", time=" + time +
                ", title='" + title + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
