package com.example.summerschoolapp.model.editRequest;

import com.example.summerschoolapp.common.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseEditRequest extends BaseModel {
    @SerializedName("data")
    @Expose
    public EditRequestInnerResponse data;

}
