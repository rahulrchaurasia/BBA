package com.beldara.bba.core.response;

import com.beldara.bba.core.APIResponse;
import com.beldara.bba.core.model.LoginEntity;

import java.util.List;

/**
 * Created by IN-RB on 09-06-2018.
 */

public class LoginResponse extends APIResponse {


    private List<LoginEntity> result;

    public List<LoginEntity> getResult() {
        return result;
    }

    public void setResult(List<LoginEntity> result) {
        this.result = result;
    }


}
