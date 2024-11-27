package com.example.calamitycall.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calamitycall.R;
import com.example.calamitycall.ThreadPost;
import com.example.calamitycall.ThreadPostAdapter;

import java.util.ArrayList;
import java.util.List;

import com.example.calamitycall.ForumThread;

public class ThreadPostsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_thread_posts, container, false);

        // Initialize RecyclerView
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView_posts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Retrieve the thread data
        ForumThread thread = (ForumThread) getArguments().getSerializable("selected_thread");

        // Set thread title and description in the thread_info section
        TextView threadTitle = rootView.findViewById(R.id.thread_title);
        TextView threadDescription = rootView.findViewById(R.id.thread_description);
        threadTitle.setText(thread.getTitle());
        threadDescription.setText(thread.getDescription());

        // Dummy Data for Posts
        List<ThreadPost> postList = new ArrayList<>();
        postList.add(new ThreadPost("Post Title 1", "by John Doe", "2 hours ago", "This is the content of the post. It describes a mundane local weather event, like a brief rainstorm..."));
        postList.add(new ThreadPost("Post Title 2", "by Jane Doe", "1 day ago", "Another post discussing a local weather issue, perhaps a storm or heatwave..."));

        // Set up Adapter
        ThreadPostAdapter adapter = new ThreadPostAdapter(postList);
        recyclerView.setAdapter(adapter);

        // Button to create new post
        Button btnCreatePost = rootView.findViewById(R.id.btn_create_post);
        btnCreatePost.setOnClickListener(v -> {
            // Logic to create a new post (open a form or dialog, for instance)

        });

        return rootView;
    }
}
