package com.example.summerschoolapp.model.newsList;

import com.example.summerschoolapp.common.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseNewsList extends BaseModel {
    @SerializedName("data")
    @Expose
    public NewsListInnerResponse data;
}
