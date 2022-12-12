package com.example.jobseeker.Worker;

import java.io.Serializable;

public class LanguageClass implements Serializable {

    public String language;
    public String level;

    public LanguageClass(){

    }

    public LanguageClass(String language, String level) {
        this.language = language;
        this.level = level;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

}
