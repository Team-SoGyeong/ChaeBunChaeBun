package com.example.chaebunchaebun;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CommentRecyclerAdapter extends RecyclerView.Adapter<CommentRecyclerAdapter.ViewHolder> {
    private ArrayList<CommentRecyclerItem> commentRecyclerItems = null;
    private CategoryListAdapter.OnModalClickListener modalClickListener = null;

    public interface OnModalClickListener {
        void OnModlaClick();
    }

    public void setModalClickListener(CategoryListAdapter.OnModalClickListener modalClickListener) {
        this.modalClickListener = modalClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView commentNickname;
        TextView commentContent;
        TextView commentTime;
        ImageButton commentModalBtn;
        public ViewHolder(View commentView) {
            super(commentView);

            commentNickname = (TextView) commentView.findViewById(R.id.comment_nickname);
            commentContent = (TextView) commentView.findViewById(R.id.comment_content);
            commentTime = (TextView) commentView.findViewById(R.id.comment_time);
            commentModalBtn = (ImageButton) commentView.findViewById(R.id.comment_modalbtn);

            commentModalBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(modalClickListener != null){
                        modalClickListener.OnModlaClick();
                    }
                }
            });
        }
    }

    public CommentRecyclerAdapter(ArrayList<CommentRecyclerItem> commentRecyclerItems){
        this.commentRecyclerItems = commentRecyclerItems;
    }

    @Override
    public CommentRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.custom_commentlist, parent, false);
        CommentRecyclerAdapter.ViewHolder cvh = new CommentRecyclerAdapter.ViewHolder(view);

        return cvh;
    }

    @Override
    public void onBindViewHolder(CommentRecyclerAdapter.ViewHolder holder, int position) {
        CommentRecyclerItem commentRecyclerItem = commentRecyclerItems.get(position);

        holder.commentNickname.setText(commentRecyclerItem.getNickname());
        holder.commentContent.setText(commentRecyclerItem.getComment());
        holder.commentTime.setText(commentRecyclerItem.getTime());
    }

    @Override
    public int getItemCount() {
        return commentRecyclerItems.size();
    }
}
