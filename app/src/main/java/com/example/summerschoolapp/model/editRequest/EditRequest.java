package com.example.summerschoolapp.model.editRequest;

import com.google.gson.annotations.SerializedName;

import okhttp3.MultipartBody;

public class EditRequest {
    @SerializedName("ID")
    private String ID;

    @SerializedName("Title")
    private String title;

    @SerializedName("Request_type")
    private String requestType;

    @SerializedName("location_latitude")
    private String location_latitude;

    @SerializedName("location_longitude")
    private String location_longitude;

    @SerializedName("message")
    private String message;

    @SerializedName("image")
    private MultipartBody.Part image;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MultipartBody.Part getImage() {
        return image;
    }

    public void setImage(MultipartBody.Part image) {
        this.image = image;
    }
}
