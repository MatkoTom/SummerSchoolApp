package com.example.summerschoolapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserBigResponse {
    @SerializedName("user")
    @Expose
    public User user;
}
