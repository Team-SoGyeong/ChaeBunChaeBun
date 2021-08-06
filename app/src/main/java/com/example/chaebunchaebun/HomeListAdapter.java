package com.example.chaebunchaebun;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.ViewHolder> {
    private ArrayList<HomeListItem> homeListItems = new ArrayList<HomeListItem>();

    private CategoryListAdapter.OnItemClickListener mListener = null;
    private CategoryListAdapter.OnModalClickListener modalClickListener = null;

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    public interface OnModalClickListener {
        void OnModlaClick();
    }

    public void setOnItemClickListener(CategoryListAdapter.OnItemClickListener listener) {
        this.mListener = listener ;
    }

    public void setModalClickListener(CategoryListAdapter.OnModalClickListener modalClickListener) {
        this.modalClickListener = modalClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout home_list_top;
        ImageView home_list_img;
        TextView home_list_title;
        TextView home_list_date;
        TextView home_list_people;
        TextView home_list_price;
        TextView home_list_writing_date;
        ImageButton home_list_modalbtn;
        public ViewHolder(@NonNull @NotNull View homeView) {
            super(homeView);
            home_list_top = (LinearLayout) homeView.findViewById(R.id.homelist_top);
            home_list_img = (ImageView) homeView.findViewById(R.id.homelist_img);
            home_list_title = (TextView) homeView.findViewById(R.id.homelist_title);
            home_list_date = (TextView) homeView.findViewById(R.id.homelist_date);
            home_list_people = (TextView) homeView.findViewById(R.id.homelist_people);
            home_list_price = (TextView) homeView.findViewById(R.id.homelist_price);
            home_list_writing_date = (TextView) homeView.findViewById(R.id.homelist_writing_date);
            home_list_modalbtn = (ImageButton) homeView.findViewById(R.id.homelist_modalbtn);

            home_list_top.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(mListener != null) {
                            mListener.onItemClick(pos);
                        }
                    }
                }
            });

            home_list_modalbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(modalClickListener != null){
                        modalClickListener.OnModlaClick();
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

        View view = inflater.inflate(R.layout.homelist_custom, parent, false);
        HomeListAdapter.ViewHolder hvh = new HomeListAdapter.ViewHolder(view);

        return hvh;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        HomeListItem homeListItem = homeListItems.get(position);

        holder.home_list_img.setImageResource(homeListItem.getImg());
        holder.home_list_title.setText(homeListItem.getTitle());
        holder.home_list_date.setText(homeListItem.getDate());
        holder.home_list_people.setText(homeListItem.getPeople());
        holder.home_list_price.setText(homeListItem.getPrice());
        holder.home_list_writing_date.setText(homeListItem.getWritingDate());
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return homeListItems.size();
    }
}
