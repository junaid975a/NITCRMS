package com.example.module_user_login.models;

public class ValidityExtend {
    String itemName, itemId, newUptoDate, requestDate,requestAcceptDate,oldUptoDate,status,validityReqDate,requestID,description,email,userName;

    public ValidityExtend() {
    }


    public ValidityExtend(String itemName, String itemId, String newUptoDate, String requestDate, String requestAcceptDate, String oldUptoDate, String status, String validityReqDate, String requestID, String description, String email,String userName) {
        this.itemName = itemName;
        this.itemId = itemId;
        this.newUptoDate = newUptoDate;
        this.requestDate = requestDate;
        this.requestAcceptDate = requestAcceptDate;
        this.oldUptoDate = oldUptoDate;
        this.status = status;
        this.validityReqDate = validityReqDate;
        this.requestID = requestID;
        this.description = description;
        this.email = email;
        this.userName=userName;
    }


    public String getUserName() {
        return userName;
    }

    public String getItemName() {
        return itemName;
    }



    public String getNewUptoDate() {
        return newUptoDate;
    }
    public void setNewUptoDate(String newUptoDate) {
        this.newUptoDate = newUptoDate;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public String getRequestAcceptDate() {
        return requestAcceptDate;
    }

    public String getOldUptoDate() {
        return oldUptoDate;
    }

    public String getStatus() {
        return status;
    }
    public String getEmail() {
        return email;
    }
    public String getItemId() {
        return itemId;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public String getValidityReqDate() {
        return validityReqDate;
    }

    public String getRequestID() {
        return requestID;
    }
}
