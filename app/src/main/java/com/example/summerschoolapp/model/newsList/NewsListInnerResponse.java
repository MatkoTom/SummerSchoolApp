package com.example.summerschoolapp.model.newsList;

import com.example.summerschoolapp.common.BaseModel;
import com.example.summerschoolapp.model.News;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NewsListInnerResponse extends BaseModel {
    @SerializedName("news")
    @Expose
    public ArrayList<News> newsList;
}
