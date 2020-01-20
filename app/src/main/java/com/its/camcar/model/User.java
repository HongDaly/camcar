package com.its.camcar.model;

import java.io.Serializable;

public class User implements Serializable{
    private String id;
    private String fullName;
    private String phone;
    private String password;
    private String role;
    private String verifyCardUrl;
    private String profileUrl;


    public User() {
    }

    public User(String id, String fullName, String phone, String password, String role, String verifyCardUrl, String profileUrl) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.password = password;
        this.role = role;
        this.verifyCardUrl = verifyCardUrl;
        this.profileUrl = profileUrl;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getVerifyCardUrl() {
        return verifyCardUrl;
    }

    public void setVerifyCardUrl(String verifyCardUrl) {
        this.verifyCardUrl = verifyCardUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
