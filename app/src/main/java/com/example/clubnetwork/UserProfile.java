package com.example.clubnetwork;

import java.io.Serializable;

public class UserProfile implements Serializable {
    private String name;
    private String registerNumber;
    private String selectedClass;
    private String selectedDepartment;
    private String selectedYear;

    public UserProfile(String name, String registerNumber, String selectedClass, String selectedDepartment, String selectedYear) {
        // Initialize fields...
        this.name = (name != null) ? name : "";
        this.registerNumber = (registerNumber != null) ? registerNumber : "";
        this.selectedClass = (selectedClass != null) ? selectedClass : ""; // Change "DefaultClass" to your actual default value
        this.selectedDepartment = (selectedDepartment != null) ? selectedDepartment : ""; // Change "DefaultDepartment" to your actual default value
        this.selectedYear = (selectedYear != null) ? selectedYear : ""; // Change "DefaultYear" to your actual default value

    }

    public String getName() {
        return name;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public String getSelectedClass() {
        return selectedClass;
    }

    public String getSelectedDepartment() {
        return selectedDepartment;
    }

    public String getSelectedYear() {
        return selectedYear;
    }

    public String setClass(String a) {
        this.selectedClass=a;
        return selectedClass;
    }

    public String setDepartment(String cse) {
        this.selectedDepartment=cse;
        return selectedDepartment;
    }

    public String setYear(String i) {

        this.selectedYear=i;
        return selectedYear;
    }
}
