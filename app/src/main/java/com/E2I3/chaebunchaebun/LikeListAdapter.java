package com.E2I3.chaebunchaebun;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LikeListAdapter extends RecyclerView.Adapter<LikeListAdapter.ViewHolder> {
    private ArrayList<LikeListItem> likeListItems = new ArrayList<LikeListItem>();

    private LikeListAdapter.OnItemClickListener mListener = null;

    public LikeListAdapter(ArrayList<LikeListItem> likeContent) {
        this.likeListItems = likeContent;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int pos);
    }

    public void setOnItemClickListener(LikeListAdapter.OnItemClickListener listener) {
        this.mListener = listener ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.custom_likelist, parent, false);
        LikeListAdapter.ViewHolder lvh = new LikeListAdapter.ViewHolder(view);

        return lvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LikeListItem likeListItem = likeListItems.get(position);

        /*holder.home_list_postId.setText(String.valueOf(likeListItem.getPostId()));
        holder.home_list_postId.setVisibility(View.GONE);
        holder.home_list_userId.setText(String.valueOf(likeListItem.getUserId()));
        holder.home_list_userId.setVisibility(View.GONE);*/
        Glide.with(holder.itemView.getContext()).load(likeListItem.getImg()).into(holder.like_list_img);
        holder.like_list_title.setText(likeListItem.getTitle());
        /*holder.home_list_date.setText(homeListItem.getDate());
        holder.home_list_people.setText(homeListItem.getPeople());
        holder.home_list_price.setText(homeListItem.getPrice());*/
        holder.like_list_writing_date.setText(likeListItem.getWritingDate());
        holder.like_list_content.setText(likeListItem.getContent());
        if(likeListItem.getIsAuth() == 0) {
            holder.like_list_isauth.setVisibility(View.GONE);
        } else {
            holder.like_list_isauth.setVisibility(View.VISIBLE);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView like_list_img;
        TextView like_list_title;
        /*TextView home_list_date;
        TextView home_list_people;
        TextView home_list_price;*/
        TextView like_list_writing_date;
        ImageView like_list_isauth;
        /*ImageButton like_list_modalbtn;
        TextView home_list_postId;
        TextView home_list_userId;*/
        TextView like_list_content;
        public ViewHolder(@NonNull @NotNull View likeView) {
            super(likeView);
            like_list_img = (ImageView) likeView.findViewById(R.id.likelist_img);
            like_list_title = (TextView) likeView.findViewById(R.id.likelist_title);
            /*home_list_date = (TextView) homeView.findViewById(R.id.likelist_date);
            home_list_people = (TextView) homeView.findViewById(R.id.likelist_people);
            home_list_price = (TextView) homeView.findViewById(R.id.likelist_price);*/
            like_list_writing_date = (TextView) likeView.findViewById(R.id.likelist_writing_date);
            like_list_isauth = (ImageView) likeView.findViewById(R.id.likelist_isauth);
            /*like_list_modalbtn = (ImageButton) likeView.findViewById(R.id.likelist_modalbtn);
            home_list_postId = (TextView) likeView.findViewById(R.id.homelist_postId);
            home_list_userId = (TextView) likeView.findViewById(R.id.homelist_userId);*/
            like_list_content = (TextView) likeView.findViewById(R.id.likelist_content);

            likeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(mListener != null) {
                            mListener.onItemClick(view, pos);
                        }
                    }
                }
            });
        }
    }

    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return likeListItems.size();
    }

    public LikeListItem getItem(int position){
        return likeListItems.get(position);
    }
}
