package com.beldara.bba.core.response;

import com.beldara.bba.core.APIResponse;
import com.beldara.bba.core.model.LeadEntity;

import java.util.List;

/**
 * Created by IN-RB on 09-06-2018.
 */

public class LeadResponse extends APIResponse {


    private List<LeadEntity> result;

    public List<LeadEntity> getResult() {
        return result;
    }

    public void setResult(List<LeadEntity> result) {
        this.result = result;
    }


}
