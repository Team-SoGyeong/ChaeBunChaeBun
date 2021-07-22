package com.example.chaebunchaebun;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder> {
    public class ViewHolder  extends RecyclerView.ViewHolder{
        ImageView imgView_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgView_item = (ImageView) itemView.findViewById(R.id.imgView_item);
        }
    }

    private ArrayList<MainRecyclerData> imgList = null;

    public MainRecyclerAdapter(ArrayList<MainRecyclerData> imgList){
        this.imgList = imgList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.main_recylerview_item, parent, false);
        MainRecyclerAdapter.ViewHolder vh = new MainRecyclerAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MainRecyclerAdapter.ViewHolder holder, int position) {
        MainRecyclerData item = imgList.get(position);

        holder.imgView_item.setImageResource(item.getImg());
    }

    @Override
    public int getItemCount() {
        return imgList.size();
    }
}
