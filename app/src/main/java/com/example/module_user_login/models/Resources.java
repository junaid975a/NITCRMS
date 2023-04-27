package com.example.module_user_login.models;

public class Resources {
    String id, alloted, category, item;
    public Resources() {
    }

    public Resources(String id, String alloted, String category, String item) {
        this.id = id;
        this.alloted = alloted;
        this.category = category;
        this.item = item;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlloted() {
        return alloted;
    }

    public void setAlloted(String alloted) {
        this.alloted = alloted;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
