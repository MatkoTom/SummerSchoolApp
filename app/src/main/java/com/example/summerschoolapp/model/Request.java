package com.example.summerschoolapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Request implements Parcelable {

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

    @SerializedName("Address")
    private String address;

    @SerializedName("image")
    private String image;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ID);
        dest.writeString(this.title);
        dest.writeString(this.requestType);
        dest.writeString(this.location_latitude);
        dest.writeString(this.location_longitude);
        dest.writeString(this.message);
        dest.writeString(this.address);
        dest.writeString(this.image);
    }

    public Request() {
    }

    protected Request(Parcel in) {
        this.ID = in.readString();
        this.title = in.readString();
        this.requestType = in.readString();
        this.location_latitude = in.readString();
        this.location_longitude = in.readString();
        this.message = in.readString();
        this.address = in.readString();
        this.image = in.readString();
    }

    public static final Parcelable.Creator<Request> CREATOR = new Parcelable.Creator<Request>() {
        @Override
        public Request createFromParcel(Parcel source) {
            return new Request(source);
        }

        @Override
        public Request[] newArray(int size) {
            return new Request[size];
        }
    };
}
