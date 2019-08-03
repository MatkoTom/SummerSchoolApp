package com.example.summerschoolapp.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class NewsArticle {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "article_id")
    private int article_id = 0;

    @ColumnInfo(name = "title_log")
    private String title_log;

    @ColumnInfo(name = "content_log")
    private String content_log;

    public NewsArticle(String title_log, String content_log) {
        this.title_log = title_log;
        this.content_log = content_log;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public String getTitle_log() {
        return title_log;
    }

    public void setTitle_log(String title_log) {
        this.title_log = title_log;
    }

    public String getContent_log() {
        return content_log;
    }

    public void setContent_log(String content_log) {
        this.content_log = content_log;
    }

    @Override
    public String toString() {
        return "NewsArticle{" +
                "title='" + title_log + '\'' +
                ", newsContent='" + content_log + '\'' +
                '}';
    }
}
