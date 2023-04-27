package com.example.module_user_login.models;

public class Notification {
    String status, date, email, id, item;

    public Notification() {
    }

    public Notification(String status, String date, String email, String id, String item) {
        this.status = status;
        this.date = date;
        this.email = email;
        this.id = id;
        this.item = item;
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
}
