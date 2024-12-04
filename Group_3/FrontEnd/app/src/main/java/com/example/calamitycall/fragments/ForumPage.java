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
import com.example.calamitycall.viewmodel.ForumViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModelProvider;

public class ForumPage extends Fragment implements CreateThreadFragment.OnThreadCreatedListener {

    private ForumViewModel viewModel;
    private ForumAdapter adapter;
    private ForumThread selectedThread;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.forum_page, container, false);

        // Get the ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(ForumViewModel.class);

        // Set up RecyclerView
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView_threads);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Use the ViewModel data
        adapter = new ForumAdapter(viewModel.getThreadList(), thread -> {
            selectedThread = thread;
            openThreadPosts(thread);
        });
        recyclerView.setAdapter(adapter);

        // Button to create new thread
        Button btnStartNewThread = rootView.findViewById(R.id.btn_start_new_thread);
        btnStartNewThread.setOnClickListener(v -> startNewThread());

        return rootView;
    }

    @Override
    public void onThreadCreated(ForumThread newThread) {
        // Add the newly created thread to the ViewModel data
        viewModel.addThread(newThread);
        adapter.notifyItemInserted(viewModel.getThreadList().size() - 1);  // Refresh RecyclerView
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

    private void startNewThread() {
        CreateThreadFragment createThreadFragment = new CreateThreadFragment();
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, createThreadFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
