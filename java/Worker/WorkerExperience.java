package com.example.jobseeker.Worker;

public class WorkerExperience {
    public String position ="";
    public String duration = "";
    public String companyName = "";

    public WorkerExperience(String position, String duration, String companyName) {
        this.position = position;
        this.duration = duration;
        this.companyName = companyName;
    }
    public WorkerExperience() {}

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
