package com.example.summerschoolapp.model.editNews;

import com.example.summerschoolapp.common.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseEditNews extends BaseModel {
    @SerializedName("data")
    @Expose
    public EditNewsInnerResponse data;
}
