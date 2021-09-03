package com.example.chaebunchaebun;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CommentRecyclerAdapter extends RecyclerView.Adapter<CommentRecyclerAdapter.ViewHolder> {
    private ArrayList<CommentRecyclerItem> commentRecyclerItems = null;
    private CommentRecyclerAdapter.OnModalClickListener modalClickListener = null;

    public interface OnModalClickListener {
        void OnModlaClick(View view, int pos);
    }

    public void setModalClickListener(CommentRecyclerAdapter.OnModalClickListener modalClickListener) {
        this.modalClickListener = modalClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView commentProfileImg;
        TextView commentNickname;
        TextView commentContent;
        TextView commentTime;
        ImageButton commentModalBtn;
        public ViewHolder(View commentView) {
            super(commentView);

            commentProfileImg = (ImageView) commentView.findViewById(R.id.comment_profileimg);
            commentNickname = (TextView) commentView.findViewById(R.id.comment_nickname);
            commentContent = (TextView) commentView.findViewById(R.id.comment_content);
            commentTime = (TextView) commentView.findViewById(R.id.comment_time);
            commentModalBtn = (ImageButton) commentView.findViewById(R.id.comment_modalbtn);

            commentModalBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(modalClickListener != null){
                            modalClickListener.OnModlaClick(view, pos);
                        }
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

        Glide.with(holder.itemView.getContext()).load(commentRecyclerItem.getProfile()).into(holder.commentProfileImg);
        holder.commentNickname.setText(commentRecyclerItem.getNickname());
        holder.commentContent.setText(commentRecyclerItem.getComment());
        holder.commentTime.setText(commentRecyclerItem.getTime());
    }

    @Override
    public int getItemCount() {
        return commentRecyclerItems.size();
    }

    public CommentRecyclerItem getItem(int position){
        return commentRecyclerItems.get(position);
    }
}
