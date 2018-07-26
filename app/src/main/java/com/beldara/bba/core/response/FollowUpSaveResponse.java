package com.beldara.bba.core.response;

import com.beldara.bba.core.APIResponse;
import com.beldara.bba.core.model.FollowOut;

/**
 * Created by IN-RB on 10-06-2018.
 */

public class FollowUpSaveResponse extends APIResponse {
    /**
     * result : {"fid":"684"}
     */

    private FollowOut result;

    public FollowOut getResult() {
        return result;
    }

    public void setResult(FollowOut result) {
        this.result = result;
    }




}
