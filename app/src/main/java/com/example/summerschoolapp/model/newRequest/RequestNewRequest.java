package com.example.summerschoolapp.model.newRequest;

import com.example.summerschoolapp.common.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.io.File;

public class RequestNewRequest extends BaseModel {

    @SerializedName("title")
    public String title;

    @SerializedName("Request_type")
    public String requestType;

    public String location_latitude;

    public String location_longitude;

    public String message;

    public File image;
}
