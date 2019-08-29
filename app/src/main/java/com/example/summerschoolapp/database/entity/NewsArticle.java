package com.example.summerschoolapp.database.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.summerschoolapp.model.News;

@Entity
public class NewsArticle implements Parcelable {

    @PrimaryKey
    @ColumnInfo(name = "article_id")
    private int article_id;

    @ColumnInfo(name = "title_log")
    private String title_log;

    @ColumnInfo(name = "message_log")
    private String message_log;

    @ColumnInfo(name = "first_name")
    private String first_name;

    @ColumnInfo(name = "last_name")
    private String last_name;

    @ColumnInfo(name = "location_latitude")
    private String location_latitude;

    @ColumnInfo(name = "location_longitude")
    private String location_longitude;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "created_at")
    private String created_at;

    @ColumnInfo(name = "updated_at")
    private String updated_at;

    @ColumnInfo(name = "images")
    private String images;

    @ColumnInfo(name = "files")
    private String files;

    @Ignore
    public NewsArticle() {
    }

    public NewsArticle(int article_id, String title_log, String message_log, String first_name, String last_name, String location_latitude, String location_longitude, String address, String created_at, String updated_at, String images, String files) {
        this.article_id = article_id;
        this.title_log = title_log;
        this.message_log = message_log;
        this.first_name = first_name;
        this.last_name = last_name;
        this.location_latitude = location_latitude;
        this.location_longitude = location_longitude;
        this.address = address;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.images = images;
        this.files = files;
    }

    public static NewsArticle convertToNewsArticle(News news) {

        NewsArticle article = new NewsArticle();
        article.setAddress(news.getAddress());
        article.setArticle_id(news.getId());
        article.setCreated_at(news.getCreatedAt());
        article.setFiles(news.getFiles());
        article.setFirst_name(news.getFirstName());
        article.setLast_name(news.getLastName());
        article.setImages(news.getImages());
        article.setLocation_latitude(news.getLocation_latitude());
        article.setLocation_longitude(news.getLocation_longitude());
        article.setMessage_log(news.getMessage());
        article.setTitle_log(news.getTitle());
        article.setUpdated_at(news.getUpdatedAt());
        return article;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getLocation_latitude() {
        return location_latitude;
    }

    public void setLocation_latitude(String location_latitude) {
        this.location_latitude = location_latitude;
    }

    public String getLocation_longitude() {
        return location_longitude;
    }

    public void setLocation_longitude(String location_longitude) {
        this.location_longitude = location_longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
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

    public String getMessage_log() {
        return message_log;
    }

    public void setMessage_log(String message_log) {
        this.message_log = message_log;
    }

    @Override
    public String toString() {
        return "NewsArticle{" +
                "title='" + title_log + '\'' +
                ", newsContent='" + message_log + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.article_id);
        dest.writeString(this.title_log);
        dest.writeString(this.message_log);
        dest.writeString(this.location_latitude);
        dest.writeString(this.location_longitude);
        dest.writeString(this.address);
        dest.writeString(this.first_name);
        dest.writeString(this.last_name);
        dest.writeString(this.created_at);
        dest.writeString(this.updated_at);
        dest.writeString(this.images);
        dest.writeString(this.files);
    }

    protected NewsArticle(Parcel in) {
        this.article_id = in.readInt();
        this.title_log = in.readString();
        this.message_log = in.readString();
        this.location_latitude = in.readString();
        this.location_longitude = in.readString();
        this.address = in.readString();
        this.first_name = in.readString();
        this.last_name = in.readString();
        this.created_at = in.readString();
        this.updated_at = in.readString();
        this.images = in.readString();
        this.files = in.readString();
    }

    public static final Parcelable.Creator<NewsArticle> CREATOR = new Parcelable.Creator<NewsArticle>() {
        @Override
        public NewsArticle createFromParcel(Parcel source) {
            return new NewsArticle(source);
        }

        @Override
        public NewsArticle[] newArray(int size) {
            return new NewsArticle[size];
        }
    };
}
