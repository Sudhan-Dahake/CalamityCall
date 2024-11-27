package com.example.calamitycall;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ForumViewHolder> {
    private List<ForumThread> threads;
    private OnThreadClickListener listener;

    public interface OnThreadClickListener {
        void onThreadClick(ForumThread thread);
    }

    public ForumAdapter(List<ForumThread> threads, OnThreadClickListener listener) {
        this.threads = threads;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ForumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forum_thread, parent, false);
        return new ForumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForumViewHolder holder, int position) {
        ForumThread thread = threads.get(position);
        holder.titleTextView.setText(thread.getTitle());
        holder.descriptionTextView.setText(thread.getDescription());
        holder.itemView.setOnClickListener(v -> listener.onThreadClick(thread));
    }

    @Override
    public int getItemCount() {
        return threads.size();
    }

    public static class ForumViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, descriptionTextView;

        public ForumViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.thread_title);
            descriptionTextView = itemView.findViewById(R.id.thread_description);
        }
    }
}
