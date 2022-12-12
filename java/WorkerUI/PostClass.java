package com.example.jobseeker.WorkerUI;

public class PostClass {

    private String Category, Description, JobTitle, Location;

    public PostClass(String category, String description, String jobTitle, String location) {
        Category = category;
        Description = description;
        JobTitle = jobTitle;
        Location = location;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getJobTitle() {
        return JobTitle;
    }

    public void setJobTitle(String jobTitle) {
        JobTitle = jobTitle;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }




}
