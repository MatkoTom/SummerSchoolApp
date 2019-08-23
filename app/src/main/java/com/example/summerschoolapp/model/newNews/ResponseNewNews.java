package com.example.summerschoolapp.model.newNews;

import com.example.summerschoolapp.common.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseNewNews extends BaseModel {
    @SerializedName("data")
    @Expose
    public NewNewsInnerResponse data;
}
