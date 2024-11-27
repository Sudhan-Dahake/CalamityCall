package com.example.calamitycall.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.calamitycall.ForumThread;
import com.example.calamitycall.R;

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
        // Handle submitting the post
        String title = postTitleEditText.getText().toString();
        String content = postContentEditText.getText().toString();
        // Do something with the title and content (e.g., send to server or save locally)
    }
}
