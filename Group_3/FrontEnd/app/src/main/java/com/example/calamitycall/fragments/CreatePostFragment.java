package com.example.calamitycall.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.calamitycall.ForumThread;
import com.example.calamitycall.R;
import com.example.calamitycall.ThreadPost;

public class CreatePostFragment extends Fragment {
    private EditText postTitleEditText, postContentEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_post, container, false);

        postTitleEditText = rootView.findViewById(R.id.post_title);  // Corrected ID
        postContentEditText = rootView.findViewById(R.id.post_content);  // Corrected ID

        // Optionally, get the selected thread if needed for context
        if (getArguments() != null) {
            ForumThread selectedThread = (ForumThread) getArguments().getSerializable("selected_thread");
            // Use the selected thread data as needed
        }

        // Handle post submission
        Button submitButton = rootView.findViewById(R.id.btn_submit_post);
        submitButton.setOnClickListener(v -> submitPost());

        return rootView;
    }

    private void submitPost() {
        // Retrieve the thread from arguments
        ForumThread thread = (ForumThread) getArguments().getSerializable("selected_thread");
        if (thread != null) {
            // Get user input
            String title = postTitleEditText.getText().toString().trim();
            String content = postContentEditText.getText().toString().trim();

            // Validate input
            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(getContext(), "Title and content cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create a new post and add it to the thread
            ThreadPost newPost = new ThreadPost(title, "Current User", "Just now", content); // Replace "Current User" with the logged-in user's name
            thread.getPosts().add(newPost);

            // Navigate back to the thread
            requireActivity().getSupportFragmentManager().popBackStack();
        }
    }

}
