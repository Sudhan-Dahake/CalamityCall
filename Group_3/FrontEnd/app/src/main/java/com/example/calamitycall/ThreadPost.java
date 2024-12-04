package com.example.calamitycall;

import java.io.Serializable;

public class ThreadPost implements Serializable {
    private String title;
    private String author;
    private String timestamp;
    private String content;

    // Constructor
    public ThreadPost(String title, String author, String timestamp, String content) {
        this.title = title;
        this.author = author;
        this.timestamp = timestamp;
        this.content = content;
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) { this.author = author; }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
