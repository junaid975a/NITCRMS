package com.example.module_user_login.models;

public class Requests {
    String requesterName,requesterEmail,requestedItem,requestID,date,status,location,upto_date;

    public Requests(String requesterName, String requesterEmail, String requestedItem, String requestID,String date,String location, String status, String upto_date) {
        this.requesterName = requesterName;
        this.requesterEmail = requesterEmail;
        this.requestedItem = requestedItem;
        this.requestID = requestID;
        this.date = date;
        this.status = status;
        this.location=location;
        this.upto_date = upto_date;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUpto_date() {
        return upto_date;
    }

    public void setUpto_date(String upto_date) {
        this.upto_date = upto_date;
    }

    public Requests() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }

    public String getRequesterEmail() {
        return requesterEmail;
    }

    public void setRequesterEmail(String requesterEmail) {
        this.requesterEmail = requesterEmail;
    }

    public String getRequestedItem() {
        return requestedItem;
    }

    public String getRequestID() {
        return requestID;
    }

    public String getLocation(){ return location;}

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public void setRequestedItem(String requestedItem) {
        this.requestedItem = requestedItem;
    }
}
