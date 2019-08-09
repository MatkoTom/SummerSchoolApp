package com.example.summerschoolapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BigDataResponse {

    @SerializedName("data")
    @Expose
    public Data data;
}
