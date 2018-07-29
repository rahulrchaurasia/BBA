package com.beldara.bba.core.response;

import com.beldara.bba.core.APIResponse;
import com.beldara.bba.core.model.SupplierEntity;

import java.util.List;

/**
 * Created by IN-RB on 29-07-2018.
 */

public class SupplierListResponse extends APIResponse {


    private List<SupplierEntity> result;

    public List<SupplierEntity> getResult() {
        return result;
    }

    public void setResult(List<SupplierEntity> result) {
        this.result = result;
    }


}
