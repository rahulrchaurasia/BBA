package com.beldara.bba.core.model;

/**
 * Created by IN-RB on 09-06-2018.
 */

public class RegisterEnity {
    /**
     * City : 677
     * Email_Id : test123@gmail.com
     * NewEmpCode :
     * PAN_No : AVTPC4754G
     * UserPassword : 123456
     * contact_No : 8999999899
     * first_Name : test
     * last_Name : test
     * parentBrokerId :
     * parentEmpCode : utm_source=google-play&utm_medium=organic
     * source :
     */

    private String City;
    private String Email_Id;
    private String NewEmpCode;
    private String PAN_No;
    private String UserPassword;
    private String contact_No;
    private String first_Name;
    private String last_Name;
    private String parentBrokerId;
    private String parentEmpCode;
    private String source;

    private String lat;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    private String lng;

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getEmail_Id() {
        return Email_Id;
    }

    public void setEmail_Id(String Email_Id) {
        this.Email_Id = Email_Id;
    }

    public String getNewEmpCode() {
        return NewEmpCode;
    }

    public void setNewEmpCode(String NewEmpCode) {
        this.NewEmpCode = NewEmpCode;
    }

    public String getPAN_No() {
        return PAN_No;
    }

    public void setPAN_No(String PAN_No) {
        this.PAN_No = PAN_No;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String UserPassword) {
        this.UserPassword = UserPassword;
    }

    public String getContact_No() {
        return contact_No;
    }

    public void setContact_No(String contact_No) {
        this.contact_No = contact_No;
    }

    public String getFirst_Name() {
        return first_Name;
    }

    public void setFirst_Name(String first_Name) {
        this.first_Name = first_Name;
    }

    public String getLast_Name() {
        return last_Name;
    }

    public void setLast_Name(String last_Name) {
        this.last_Name = last_Name;
    }

    public String getParentBrokerId() {
        return parentBrokerId;
    }

    public void setParentBrokerId(String parentBrokerId) {
        this.parentBrokerId = parentBrokerId;
    }

    public String getParentEmpCode() {
        return parentEmpCode;
    }

    public void setParentEmpCode(String parentEmpCode) {
        this.parentEmpCode = parentEmpCode;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
