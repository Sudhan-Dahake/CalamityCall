package com.example.calamitycall.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.calamitycall.ForumThread;
import com.example.calamitycall.R;

public class CreateThreadFragment extends Fragment {

    private OnThreadCreatedListener listener;

    private EditText etThreadTitle, etThreadDescription;
    private Button btnSubmit;

    public void setOnThreadCreatedListener(ForumPage forumPage) {
    }

    public interface OnThreadCreatedListener {
        void onThreadCreated(ForumThread newThread);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_create_thread, container, false);

        // Initialize UI elements
        etThreadTitle = rootView.findViewById(R.id.et_thread_title);
        etThreadDescription = rootView.findViewById(R.id.et_thread_description);
        btnSubmit = rootView.findViewById(R.id.btn_submit_thread);

        // Set the listener
        if (getActivity() instanceof OnThreadCreatedListener) {
            listener = (OnThreadCreatedListener) getActivity();
        }

        // Set a listener for the submit button
        btnSubmit.setOnClickListener(v -> {
            // Handle the logic for creating a new thread
            String title = etThreadTitle.getText().toString().trim();
            String description = etThreadDescription.getText().toString().trim();

            if (title.isEmpty() || description.isEmpty()) {
                // Show a Toast if fields are empty
                Toast.makeText(getContext(), "Both title and description are required.", Toast.LENGTH_SHORT).show();
            } else {
                // Logic to create the thread
                ForumThread newThread = new ForumThread(title, description);

                // Notify the listener (ForumPage) with the new thread
                if (listener != null) {
                    listener.onThreadCreated(newThread);
                }

                goBackToForumPage();
            }
        });

        return rootView;
    }

    // Method to handle creating a new thread
    private void createThread(String title, String description) {
        // Create a new ForumThread object
        ForumThread newThread = new ForumThread(title, description);

        // Add the thread to your list of threads or send it to the backend
        // For now, we're just displaying a success message.
        Toast.makeText(getContext(), "Thread created: " + title, Toast.LENGTH_SHORT).show();

        // Optionally, go back to the ForumPage after creating the thread
        goBackToForumPage();
    }

    // Method to navigate back to the ForumPage
    private void goBackToForumPage() {
        // Instead of creating a new ForumPage instance, just pop back the current fragment.
        requireActivity().getSupportFragmentManager().popBackStack();
    }

    // Set the listener method
    public void setOnThreadCreatedListener(OnThreadCreatedListener listener) {
        this.listener = listener;
    }
}
