package com.beldara.bba.core.response;

import java.util.List;

/**
 * Created by IN-RB on 10-06-2018.
 */

public class StatusResponse {


    /**
     * message : Ok
     * result : [{"id":"1","status":"Good Nice Product"},{"id":"2","status":"Good Nice Product"}]
     * status : Success
     * statusId : 1
     */

    private String message;
    private String status;
    private int statusId;
    private List<StatusEntity> result;

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

    public List<StatusEntity> getResult() {
        return result;
    }

    public void setResult(List<StatusEntity> result) {
        this.result = result;
    }

    public static class StatusEntity {
        /**
         * id : 1
         * status : Good Nice Product
         */

        private String id;
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
