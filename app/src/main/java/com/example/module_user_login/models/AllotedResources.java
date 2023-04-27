package com.example.module_user_login.models;

public class AllotedResources {
    String userName,allotmentID, allotmentDate,forDuration,itemID, itemName, userEmail,requestDate,requestID,location,upto_date;

    public AllotedResources() {
    }

    public AllotedResources(String allotmentID, String allotmentDate, String forDuration, String itemID, String itemName, String userEmail,String requestDate,String userName,String requestID,String location,String upto_date) {
        this.allotmentID = allotmentID;
        this.allotmentDate = allotmentDate;
        this.forDuration = forDuration;
        this.itemID = itemID;
        this.itemName = itemName;
        this.userEmail = userEmail;
        this.requestDate = requestDate;
        this.userName = userName;
        this.requestID = requestID;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getAllotmentID() {
        return allotmentID;
    }

    public void setAllotmentID(String allotmentID) {
        this.allotmentID = allotmentID;
    }

    public String getAllotmentDate() {
        return allotmentDate;
    }

    public void setAllotmentDate(String allotmentDate) {
        this.allotmentDate = allotmentDate;
    }

    public String getForDuration() {
        return forDuration;
    }

    public void setForDuration(String forDuration) {
        this.forDuration = forDuration;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUserEmail() {
        return userEmail;
    }
    public String getLocation(){ return location;}

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
