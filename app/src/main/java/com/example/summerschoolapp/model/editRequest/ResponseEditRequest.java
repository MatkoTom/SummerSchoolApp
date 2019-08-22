package com.example.summerschoolapp.model.editRequest;

import com.example.summerschoolapp.common.BaseModel;
import com.google.gson.annotations.SerializedName;

public class ResponseEditRequest extends BaseModel {

    @SerializedName("response")
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
