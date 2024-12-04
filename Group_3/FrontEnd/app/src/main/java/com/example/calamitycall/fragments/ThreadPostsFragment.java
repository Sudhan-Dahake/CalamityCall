package com.example.calamitycall.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calamitycall.R;
import com.example.calamitycall.ThreadPost;
import com.example.calamitycall.ThreadPostAdapter;

import java.util.ArrayList;
import java.util.List;

import com.example.calamitycall.ForumThread;

public class ThreadPostsFragment extends Fragment {
    private ThreadPostAdapter.OnPostClickListener listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_thread_posts, container, false);

        listener = post -> {
            Toast.makeText(getContext(), "Clicked on: " + post.getTitle(), Toast.LENGTH_SHORT).show();
        };

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView_posts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Retrieve the thread data
        ForumThread thread = (ForumThread) getArguments().getSerializable("selected_thread");

        // Set thread title and description in the thread_info section
        TextView threadTitle = rootView.findViewById(R.id.thread_title);
        TextView threadDescription = rootView.findViewById(R.id.thread_description);

        if (thread != null) {
            threadTitle.setText(thread.getTitle());
            threadDescription.setText(thread.getDescription());

            // Retrieve posts from the thread and set up the adapter
            List<ThreadPost> postList = thread.getPosts();
            ThreadPostAdapter adapter = new ThreadPostAdapter(postList, listener);
            recyclerView.setAdapter(adapter);
        } else {
            threadTitle.setText("No thread selected");
            threadDescription.setText("");
        }

        // Button to create new post
        Button btnCreatePost = rootView.findViewById(R.id.btn_create_post);
        btnCreatePost.setOnClickListener(v -> createPost());

        return rootView;
    }

    // Method to handle creating a new post
    private void createPost() {
        ForumThread thread = (ForumThread) getArguments().getSerializable("selected_thread");
        if (thread != null) {
            // Navigate to a fragment where the user can create a post within the thread
            CreatePostFragment createPostFragment = new CreatePostFragment();

            // Pass selected thread to CreatePostFragment
            Bundle args = new Bundle();
            args.putSerializable("selected_thread", thread);
            createPostFragment.setArguments(args);

            // Use FragmentTransaction to show the create post fragment
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, createPostFragment);
            transaction.addToBackStack(null);  // Add to back stack so the user can return to the previous screen
            transaction.commit();
        }
    }
}
