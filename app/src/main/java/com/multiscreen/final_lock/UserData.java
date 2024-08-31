package com.multiscreen.final_lock;

public class UserData {
    String fullname;
    String image;
    String date;
    String time;
    String key;
    String email;
    String contact;

    public UserData() {
    }

    public UserData(String fullname, String image, String date, String time, String key, String email, String contact) {
        this.fullname = fullname;
        this.image = image;
        this.date = date;
        this.time = time;
        this.key = key;
        this.email = email;
        this.contact = contact;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}

