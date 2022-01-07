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

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.ViewHolder> {
    private ArrayList<HomeListItem> homeListItems = new ArrayList<HomeListItem>();

    private HomeListAdapter.OnItemClickListener mListener = null;
    private HomeListAdapter.OnModalClickListener modalClickListener = null;

    public interface OnItemClickListener {
        void onItemClick(View view, int pos);
    }

    public interface OnModalClickListener {
        void OnModlaClick(View view, int pos);
    }

    public void setOnItemClickListener(HomeListAdapter.OnItemClickListener listener) {
        this.mListener = listener ;
    }

    public void setModalClickListener(HomeListAdapter.OnModalClickListener modalClickListener) {
        this.modalClickListener = modalClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView home_list_img;
        TextView home_list_title;
        TextView home_list_date;
        TextView home_list_people;
        TextView home_list_price;
        TextView home_list_writing_date;
        ImageButton home_list_modalbtn;
        ImageView home_list_isauth;
        TextView home_list_postId;
        TextView home_list_userId;
        public ViewHolder(@NonNull @NotNull View homeView) {
            super(homeView);
            home_list_img = (ImageView) homeView.findViewById(R.id.homelist_img);
            home_list_title = (TextView) homeView.findViewById(R.id.homelist_title);
            home_list_date = (TextView) homeView.findViewById(R.id.homelist_date);
            home_list_people = (TextView) homeView.findViewById(R.id.homelist_people);
            home_list_price = (TextView) homeView.findViewById(R.id.homelist_price);
            home_list_writing_date = (TextView) homeView.findViewById(R.id.homelist_writing_date);
            home_list_modalbtn = (ImageButton) homeView.findViewById(R.id.homelist_modalbtn);
            home_list_isauth = (ImageView) homeView.findViewById(R.id.homelist_isauth);
            home_list_postId = (TextView) homeView.findViewById(R.id.homelist_postId);
            home_list_userId = (TextView) homeView.findViewById(R.id.homelist_userId);

            homeView.setOnClickListener(new View.OnClickListener() {
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

            home_list_modalbtn.setOnClickListener(new View.OnClickListener() {
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

    public HomeListAdapter(ArrayList<HomeListItem> homeContent) {
        this.homeListItems = homeContent;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.custom_homelist, parent, false);
        HomeListAdapter.ViewHolder hvh = new HomeListAdapter.ViewHolder(view);

        return hvh;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        HomeListItem homeListItem = homeListItems.get(position);

        holder.home_list_postId.setText(String.valueOf(homeListItem.getPostId()));
        holder.home_list_postId.setVisibility(View.GONE);
        holder.home_list_userId.setText(String.valueOf(homeListItem.getUserId()));
        holder.home_list_userId.setVisibility(View.GONE);
        Glide.with(holder.itemView.getContext()).load(homeListItem.getImg()).into(holder.home_list_img);
        holder.home_list_title.setText(homeListItem.getTitle());
        holder.home_list_date.setText(homeListItem.getDate());
        holder.home_list_people.setText(homeListItem.getPeople());
        holder.home_list_price.setText(homeListItem.getPrice());
        holder.home_list_writing_date.setText(homeListItem.getWritingDate());
        if(homeListItem.getIsAuth() == 0) {
            holder.home_list_isauth.setVisibility(View.GONE);
        } else {
            holder.home_list_isauth.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return homeListItems.size();
    }

    public HomeListItem getItem(int position){
        return homeListItems.get(position);
    }
}
