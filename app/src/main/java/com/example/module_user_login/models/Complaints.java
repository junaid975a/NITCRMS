package com.example.module_user_login.models;

import java.util.Date;

public class Complaints {
    String name, email, complainedItem, complaintDescription,complaintID,date,status,itemId,location,upto_date;

    public Complaints(String name, String email, String complainedItem, String complaintDescription, String complaintID,String date,String itemId,String location,String status, String upto_date) {
        this.name = name;
        this.email = email;
        this.complainedItem = complainedItem;
        this.complaintDescription = complaintDescription;
        this.complaintID = complaintID;
        this.location=location;
        this.itemId=itemId;
        this.date = date;
        this.status = status;
        this.upto_date = upto_date;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComplaintID() {
        return complaintID;
    }

    public void setComplaintID(String complaintID) {
        this.complaintID = complaintID;
    }

    public Complaints() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComplainedItem() {
        return complainedItem;
    }

    public void setComplainedItem(String complainedItem) {
        this.complainedItem = complainedItem;
    }

    public String getComplaintDescription() {
        return complaintDescription;
    }

    public String getItemId(){ return itemId;}
    public String getLocation(){ return location;}

    public void setComplaintDescription(String complaintDescription) {
        this.complaintDescription = complaintDescription;
    }
}
