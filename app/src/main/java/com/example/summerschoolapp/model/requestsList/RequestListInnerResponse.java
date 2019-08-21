package com.example.summerschoolapp.model.requestsList;

import com.example.summerschoolapp.common.BaseModel;
import com.example.summerschoolapp.model.Request;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RequestListInnerResponse extends BaseModel {

    @SerializedName("requests")
    @Expose
    public ArrayList<Request> requestList;
}
