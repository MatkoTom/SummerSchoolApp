package com.example.summerschoolapp.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class NewsArticle {

    // TODO @Matko
    // column info aka name should be the same as variable name

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "article_id")
    private int id = 0;

    @ColumnInfo(name = "title_log")
    private String title;

    @ColumnInfo(name = "content_log")
    private String newsContent;

    public NewsArticle(String title, String newsContent) {
        this.title = title;
        this.newsContent = newsContent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    @Override
    public String toString() {
        return "NewsArticle{" +
                "title='" + title + '\'' +
                ", newsContent='" + newsContent + '\'' +
                '}';
    }
}
