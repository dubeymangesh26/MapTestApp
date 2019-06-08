package com.dotcom.maptestapp;

public class User {


    private String emails,status;
    public User(){

    }
    public User(String emails, String status) {
        this.emails = emails;
        this.status = status;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
