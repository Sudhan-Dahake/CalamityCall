package com.example.calamitycall;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ThreadPostAdapter extends RecyclerView.Adapter<ThreadPostAdapter.ThreadPostViewHolder> {
    private List<ThreadPost> postList;

    public ThreadPostAdapter(List<ThreadPost> postList) {
        this.postList = postList;
    }

    @NonNull
    @Override
    public ThreadPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);
        return new ThreadPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThreadPostViewHolder holder, int position) {
        ThreadPost post = postList.get(position);
        holder.postTitle.setText(post.getTitle());
        holder.postAuthor.setText(post.getAuthor());
        holder.postTimestamp.setText(post.getTimestamp());
        holder.postContent.setText(post.getContent());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class ThreadPostViewHolder extends RecyclerView.ViewHolder {
        TextView postTitle, postAuthor, postTimestamp, postContent;

        public ThreadPostViewHolder(View itemView) {
            super(itemView);
            postTitle = itemView.findViewById(R.id.post_title);
            postAuthor = itemView.findViewById(R.id.post_author);
            postTimestamp = itemView.findViewById(R.id.post_timestamp);
            postContent = itemView.findViewById(R.id.post_content);
        }
    }
}
