package com.example.module_user_login.models;

public class AcceptedComplaints {
    String acceptDate, complaintDate, complaintDescription
            ,complaintID,
            email,
            id,
            item,
            name,
            itemId,
            location;

    public AcceptedComplaints() {
    }

    public AcceptedComplaints(String acceptDate, String complaintDate, String complaintDescription, String complaintID, String email, String id, String item, String name,String itemId,String location) {
        this.acceptDate = acceptDate;
        this.complaintDate = complaintDate;
        this.complaintDescription = complaintDescription;
        this.complaintID = complaintID;
        this.email = email;
        this.id = id;
        this.item = item;
        this.name = name;
        this.itemId=itemId;
        this.location=location;
    }

    public String getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(String acceptDate) {
        this.acceptDate = acceptDate;
    }

    public String getComplaintDate() {
        return complaintDate;
    }

    public void setComplaintDate(String complaintDate) {
        this.complaintDate = complaintDate;
    }

    public String getComplaintDescription() {
        return complaintDescription;
    }

    public void setComplaintDescription(String complaintDescription) {
        this.complaintDescription = complaintDescription;
    }

    public String getComplaintID() {
        return complaintID;
    }

    public void setComplaintID(String complaintID) {
        this.complaintID = complaintID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getName() {
        return name;
    }
    public String getItemId(){return  itemId;}
    public String getLocation(){ return location;}

    public void setName(String name) {
        this.name = name;
    }
}
