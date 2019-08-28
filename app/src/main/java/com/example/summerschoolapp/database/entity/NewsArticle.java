package com.example.summerschoolapp.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class NewsArticle {

    @PrimaryKey
    @ColumnInfo(name = "article_id")
    private int article_id = 0;

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
}
