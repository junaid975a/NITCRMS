package com.example.module_user_login.models;

public class Users {
    private String contact;
    private String email;
    private String id;
    private String password;
    private String username;
    private String usertype;
    private String imageURL;

    public Users(String contact, String email, String id, String password, String username, String usertype, String imageURL) {
        this.contact = contact;
        this.email = email;
        this.id = id;
        this.password = password;
        this.username = username;
        this.usertype = usertype;
        this.imageURL = imageURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Users() {
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }
}
