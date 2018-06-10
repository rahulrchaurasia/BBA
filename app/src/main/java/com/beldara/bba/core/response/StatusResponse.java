package com.beldara.bba.core.response;

import com.beldara.bba.core.APIResponse;
import com.beldara.bba.core.model.StatusEntity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by IN-RB on 10-06-2018.
 */

public class StatusResponse extends APIResponse {


    private List<StatusEntity> result;

    public List<StatusEntity> getResult() {
        return result;
    }

    public void setResult(List<StatusEntity> result) {
        this.result = result;
    }


}
