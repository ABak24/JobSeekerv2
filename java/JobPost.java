package com.example.jobseeker;

public class JobPost {
    private String JobTitle;
    private String Category;
    private String Location;
    private String Description;
    private String employerId;



    public JobPost(String jobTitle, String category, String location, String description, String employerId) {
        JobTitle = jobTitle;
        Category = category;
        Location = location;
        Description = description;
        this.employerId = employerId;
    }


    public String getJobTitle() {
        return JobTitle;
    }

    public void setJobTitle(String jobTitle) {
        JobTitle = jobTitle;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getEmployerId() {
        return employerId;
    }

    public void setEmployerId(String employerId) {
        this.employerId = employerId;
    }


    public JobPost(){}
}
