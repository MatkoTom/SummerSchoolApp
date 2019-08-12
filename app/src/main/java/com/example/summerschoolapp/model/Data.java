package com.example.summerschoolapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("user")
    @Expose
    public User user;

    @SerializedName("error")
    @Expose
    public Error error;
}
