package com.example.summerschoolapp.model.newRequest;

import com.example.summerschoolapp.common.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.io.File;

public class RequestNewRequest extends BaseModel {

    @SerializedName("title")
    public String title;

    @SerializedName("Request_type")
    public String requestType;

    @SerializedName("location_latitude")
    public String location_latitude;

    @SerializedName("location_longitude")
    public String location_longitude;

    @SerializedName("message")
    public String message;

    @SerializedName("image")
    public File image;
}
