package com.beldara.bba.core.requestmodel;

/**
 * Created by IN-RB on 10-06-2018.
 */

public class FollowupRequestEntity {


    /**
     * user_id : 465
     * mstatus_id : 1
     * status_id : 0
     * seller_id : 765
     * remark : nice product
     * followup_date : 2018-06-09 22:59:36
     * ip : 127.1.1.0
     * lat : test
     * lang : test1
     */

    private String user_id;
    private String status_id;
    private String seller_id;
    private String remark;
    private String followup_date;
    private String ip;
    private String lat;
    private String lang;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }



    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFollowup_date() {
        return followup_date;
    }

    public void setFollowup_date(String followup_date) {
        this.followup_date = followup_date;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
