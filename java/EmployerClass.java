package com.example.jobseeker;

public class EmployerClass {

String employerName;
    String employerLastName;
    String employerCompanyName;
    String employerPhoneNumber;
    String employerLocation;
    String employerDescription;

public EmployerClass() {}

    public EmployerClass(String employerName, String employerLastName, String employerCompanyName,
                         String employerPhoneNumber, String employerLocation, String employerDescription) {
        this.employerName = employerName;
        this.employerLastName = employerLastName;
        this.employerCompanyName = employerCompanyName;
        this.employerPhoneNumber = employerPhoneNumber;
        this.employerLocation = employerLocation;
        this.employerDescription = employerDescription;
    }


    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public String getEmployerLastName() {
        return employerLastName;
    }

    public void setEmployerLastName(String employerLastName) {
        this.employerLastName = employerLastName;
    }

    public String getEmployerCompanyName() {
        return employerCompanyName;
    }

    public void setEmployerCompanyName(String employerCompanyName) {
        this.employerCompanyName = employerCompanyName;
    }

    public String getEmployerPhoneNumber() {
        return employerPhoneNumber;
    }

    public void setEmployerPhoneNumber(String employerPhoneNumber) {
        this.employerPhoneNumber = employerPhoneNumber;
    }

    public String getEmployerLocation() {
        return employerLocation;
    }

    public void setEmployerLocation(String employerLocation) {
        this.employerLocation = employerLocation;
    }

    public String getEmployerDescription() {
        return employerDescription;
    }

    public void setEmployerDescription(String employerDescription) {
        this.employerDescription = employerDescription;
    }


}
