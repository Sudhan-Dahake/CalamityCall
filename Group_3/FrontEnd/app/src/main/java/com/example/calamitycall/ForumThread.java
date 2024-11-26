package com.example.calamitycall;

public class ForumThread {
    private String title;
    private String description;

    public ForumThread(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
