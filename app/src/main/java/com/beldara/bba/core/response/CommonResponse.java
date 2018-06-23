package com.beldara.bba.core.response;

import com.beldara.bba.core.APIResponse;

/**
 * Created by IN-RB on 10-06-2018.
 */

public class CommonResponse extends APIResponse {


    /**
     * result : null
     */

    private Object result;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
