package com.example.summerschoolapp.model.newRequest;

import com.example.summerschoolapp.common.BaseModel;
import com.google.gson.annotations.SerializedName;

public class NewRequestInnerResponse extends BaseModel {
    @SerializedName("ok")
    public String ok;

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

    @Override
    public String toString() {
        return "ResponseNewRequest{" +
                "ok='" + ok + '\'' +
                '}';
    }
}
