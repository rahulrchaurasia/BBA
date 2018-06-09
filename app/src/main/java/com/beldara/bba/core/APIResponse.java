package com.beldara.bba.core;

import java.util.List;

/**
 * Created by IN-RB on 09-06-2018.
 */

public class APIResponse {


    /**
     * message : Ok
     * result : [{"id":"1","email":"pradeepkhandekar@gmail.com","type":"0","Username":"Pradeep Khandekar"}]
     * status : Success
     * statusId : 1
     */

    private String message;
    private String status;
    private int statusId;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

}


