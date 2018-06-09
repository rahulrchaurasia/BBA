package com.beldara.bba.core.response;

import com.beldara.bba.core.APIResponse;
import com.beldara.bba.core.model.FollowUpHistoryEntity;


import java.util.List;

/**
 * Created by IN-RB on 10-06-2018.
 */

public class FollowUpHistoryResponse extends APIResponse {


    private List<FollowUpHistoryEntity> result;

    public List<FollowUpHistoryEntity> getResult() {
        return result;
    }

    public void setResult(List<FollowUpHistoryEntity> result) {
        this.result = result;
    }


}
