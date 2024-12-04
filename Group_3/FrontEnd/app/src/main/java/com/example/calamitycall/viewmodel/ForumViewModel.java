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

        threadList.add(new ForumThread(
                "Snow in the Mountains",
                "Latest snow accumulation predictions for the mountains.",
                createPosts("Snowy Weather")
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
        posts.add(new ThreadPost(topic + " Post 1", "Author 1", "10 mins ago", "This is a post about " + topic + "."));
        posts.add(new ThreadPost(topic + " Post 2", "Author 2", "1 hour ago", "Another discussion about " + topic + "."));
        return posts;
    }
}
