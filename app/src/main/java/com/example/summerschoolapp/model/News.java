package com.example.summerschoolapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.File;

public class News {

    @SerializedName("Title")
    private String title;

    @SerializedName("Message")
    private String message;

    @SerializedName("Location_longitude")
    private String location_longitude;

    @SerializedName("Location_latitude")
    private String location_latitude;

    @SerializedName("Address")
    private String address;

    @SerializedName("Files")
    private File files;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLocation_longitude() {
        return location_longitude;
    }

    public void setLocation_longitude(String location_longitude) {
        this.location_longitude = location_longitude;
    }

    public String getLocation_latitude() {
        return location_latitude;
    }

    public void setLocation_latitude(String location_latitude) {
        this.location_latitude = location_latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public File getFiles() {
        return files;
    }

    public void setFiles(File files) {
        this.files = files;
    }
}
