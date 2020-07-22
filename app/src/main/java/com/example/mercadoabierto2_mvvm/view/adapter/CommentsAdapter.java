package com.example.mercadoabierto2_mvvm.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mercadoabierto2_mvvm.R;
import com.example.mercadoabierto2_mvvm.pojo.pojo.Comment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentsAdapter extends RecyclerView.Adapter{

    private List<Comment> commentsList;


    public CommentsAdapter(List<Comment> commentsList) {
        this.commentsList = commentsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.comment_cell, parent, false);
        CommentViewHolder commentViewHolder = new CommentViewHolder(view);
        return commentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Comment comment = commentsList.get(position);
        CommentViewHolder commentViewHolder = (CommentViewHolder) holder;
        commentViewHolder.bind(comment);

    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textViewComentarioCelda)
        TextView textViewComentarioCelda;
        @BindView(R.id.textViewUsernameComentrioCelda)
        TextView textViewUsernameComentrioCelda;


        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


        public void bind(Comment comment) {
            textViewUsernameComentrioCelda.setText(comment.getUsername());
            String aComment = "''" + comment.getComment() + "''";
            textViewComentarioCelda.setText(aComment);

        }

    }
}
