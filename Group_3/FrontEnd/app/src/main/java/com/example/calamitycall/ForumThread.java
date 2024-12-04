package com.example.calamitycall;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ForumThread implements Serializable {
    private final String title;
    private final String description;
    private List<ThreadPost> posts;

    public ForumThread(String title, String description) {
        this.title = title;
        this.description = description;
        this.posts = new ArrayList<>();
    }

    public ForumThread(String title, String description, List<ThreadPost> posts) {
        this.title = title;
        this.description = description;
        this.posts = posts != null ? posts : new ArrayList<>();
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<ThreadPost> getPosts() {
        return posts;
    }

    public void setPosts(List<ThreadPost> posts) {
        this.posts = posts != null ? posts : new ArrayList<>();
    }
}
