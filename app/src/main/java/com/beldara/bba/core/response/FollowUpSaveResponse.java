package com.beldara.bba.core.response;

import com.beldara.bba.core.APIResponse;

/**
 * Created by IN-RB on 10-06-2018.
 */

public class FollowUpSaveResponse extends APIResponse {


    /**
     * result : true
     */

    private boolean result;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
