package com.example.calamitycall.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.calamitycall.ForumThread;
import com.example.calamitycall.ThreadPost;

import java.util.ArrayList;
import java.util.List;

public class ForumViewModel extends ViewModel {
    private final List<ForumThread> threadList;

    public ForumViewModel() {
        threadList = new ArrayList<>();

        threadList.add(new ForumThread(
                "Survivors List",
                "User reports of disaster survivors.",
                createPosts("Survivors")
        ));

        threadList.add(new ForumThread(
                "Lost And Found Animals",
                "User reports of missing or located animals.",
                createPosts("Animals")
        ));

        threadList.add(new ForumThread(
                "Critical Disaster Information",
                "Information and instructions from authorities to stay safe.",
                createPosts("Disaster Information")
        ));

        // Create threads with specific posts
        threadList.add(new ForumThread(
                "Heavy Rain Expected Tomorrow",
                "Discussion about tomorrow's rain forecast.",
                createPosts("Heavy Rain")
        ));

        threadList.add(new ForumThread(
                "Sunny Week Ahead",
                "Discussing the sunny weather coming up.",
                createPosts("Sunny Weather")
        ));

        threadList.add(new ForumThread(
                "Windstorm Warnings",
                "Local windstorm warnings for the region.",
                createPosts("Windstorm")
        ));
    }

    public List<ForumThread> getThreadList() {
        return threadList;
    }

    public void addThread(ForumThread newThread) {
        threadList.add(newThread);
    }

    // Helper method to create  posts for a given topic
    private List<ThreadPost> createPosts(String topic) {
        List<ThreadPost> posts = new ArrayList<>();
        posts.add(new ThreadPost(topic + " Post 1", "Author 1", "1 hour ago", "This is a post about " + topic + "."));
        posts.add(new ThreadPost(topic + " Post 2", "Author 2", "32 minutes ago", "Another comment about " + topic + "."));
        posts.add(new ThreadPost(topic + " Post 3", "Author 3", "7 minutes ago", "Further discussion of " + topic + "."));
        return posts;
    }
}
