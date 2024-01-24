package com.example.clubnetwork;

public class UserProfile {
    private String name;
    private String email;
    private String regNo;
    private String password;

    // Required default constructor for Firebase
    public UserProfile() {
    }

    public UserProfile(String name, String email, String regNo, String password) {
        this.name = name;
        this.email = email;
        this.regNo = regNo;
        this.password = password;
    }

    // Add getter and setter methods here
    // Getter methods
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRegNo() {
        return regNo;
    }

    public String getPassword() {
        return password;
    }

    // Setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

