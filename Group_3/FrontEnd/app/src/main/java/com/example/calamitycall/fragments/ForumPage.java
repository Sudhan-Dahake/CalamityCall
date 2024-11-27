package com.example.calamitycall.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calamitycall.ForumAdapter;
import com.example.calamitycall.ForumThread;
import com.example.calamitycall.R;

import java.util.ArrayList;
import java.util.List;

public class ForumPage extends Fragment {
    private ForumThread selectedThread;  // Declare selectedThread here

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.forum_page, container, false);

        // Set up RecyclerView
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView_threads);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Dummy Data
        List<ForumThread> threadList = new ArrayList<>();
        threadList.add(new ForumThread("Heavy Rain Expected Tomorrow", "Discussion about tomorrow's rain forecast."));
        threadList.add(new ForumThread("Sunny Week Ahead", "Discussing the sunny weather coming up."));
        threadList.add(new ForumThread("Windstorm Warnings", "Local windstorm warnings for the region."));
        threadList.add(new ForumThread("Snow in the Mountains", "Latest snow accumulation predictions for the mountains."));

        // Set up adapter
        // When a thread is clicked, set the selectedThread and open thread posts
        ForumAdapter adapter = new ForumAdapter(threadList, new ForumAdapter.OnThreadClickListener() {
            @Override
            public void onThreadClick(ForumThread thread) {
                // When a thread is clicked, set the selectedThread and open thread posts
                selectedThread = thread;
                openThreadPosts(thread);
            }
        });
        recyclerView.setAdapter(adapter);

        // Button to create post
        Button btnCreatePost = rootView.findViewById(R.id.btn_create_post);
        btnCreatePost.setOnClickListener(v -> {
            // Logic to open create post dialog
            createPost();
        });

        return rootView;
    }

    // Method to open posts for a selected thread
    private void openThreadPosts(ForumThread thread) {
        // Create new Fragment to display posts
        ThreadPostsFragment postsFragment = new ThreadPostsFragment();

        // Pass thread data as arguments
        Bundle args = new Bundle();
        args.putSerializable("selected_thread", thread);
        postsFragment.setArguments(args);

        // Use FragmentTransaction to replace the current fragment with the new one
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, postsFragment);  // Ensure you have a container in the layout for fragments
        transaction.addToBackStack(null);  // Allow the user to navigate back
        transaction.commit();
    }

    private void createPost() {
        if (selectedThread != null) {
            // Create a new fragment for creating a post
            CreatePostFragment createPostFragment = new CreatePostFragment();

            // Pass selected thread to the CreatePostFragment
            Bundle args = new Bundle();
            args.putSerializable("selected_thread", selectedThread);  // Pass selected thread for context
            createPostFragment.setArguments(args);

            // Use FragmentTransaction to show the create post fragment
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, createPostFragment);
            transaction.addToBackStack(null);  // Add to back stack so the user can return to the previous screen
            transaction.commit();
        }
    }
}
