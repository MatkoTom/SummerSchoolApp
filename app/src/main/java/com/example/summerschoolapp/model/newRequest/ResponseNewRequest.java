package com.example.summerschoolapp.model.newRequest;

import com.example.summerschoolapp.common.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseNewRequest extends BaseModel {

    @SerializedName("data")
    @Expose
    public NewRequestInnerResponse data;
}
