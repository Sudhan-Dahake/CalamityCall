package com.example.calamitycall;

import java.io.Serializable;

public class ForumThread implements Serializable {
    private String title;
    private String description;

    // Constructor
    public ForumThread(String title, String description) {
        this.title = title;
        this.description = description;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
