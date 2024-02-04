package com.example.clubnetwork;
import java.io.Serializable;
public class UserProfile implements Serializable {
    private String name;
    private String email;
    private String regNo;
    private String password;
    private  String classs;
    private  String year;
    private String literary;
    private String cmc;
    private String ai;



    // Required default constructor for Firebase
    public UserProfile() {
    }

    public UserProfile(String name, String email, String regNo, String password,String classs,String year,String literary,String cmc,String ai) {
        this.name = name;
        this.email = email;
        this.regNo = regNo;
        this.password = password;
        this.classs = classs;
        this.year = year;
        this.literary=literary;
        this.cmc=cmc;
        this.ai=ai;
    }

    // Add getter and setter methods here
    // Getter methods
    public String getName() {
        return name;
    }

    public String getClasss() {
        return classs;
    }

    public String getyear() {
        return year;
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
    public String getLiterary(){return literary;}
    public String getcmc(){return cmc;}
    public String getai(){return ai;}

    // Setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setClasss(String classs) {
        this.classs = classs;
    }

    public void setYear(String year) {
        this.year = year;
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
    public void setLiterary(String literary){this.literary=literary;}
    public void setcmc(String cmc){this.cmc=cmc;}
    public void setai(String ai){this.ai=ai;}

}

