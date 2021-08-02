package com.example.chaebunchaebun;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ArticleRecyclerAdapter extends RecyclerView.Adapter<ArticleRecyclerAdapter.ViewHolder> {
    private ArrayList<ArticleRecyclerData> imgContent = null;

    public ArticleRecyclerAdapter(ArrayList<ArticleRecyclerData> imgContent) {
        this.imgContent = imgContent;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.article_recyclerview_item, parent, false);
        ArticleRecyclerAdapter.ViewHolder avh = new ArticleRecyclerAdapter.ViewHolder(view);

        return avh;
    }

    @Override
    public void onBindViewHolder(ArticleRecyclerAdapter.ViewHolder holder, int position) {
        ArticleRecyclerData articleItem = imgContent.get(position);

        holder.imageView.setImageResource(articleItem.getImg());
        holder.textView.setText((position + 1) + "/5");
    }

    @Override
    public int getItemCount() {
        return imgContent.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.article_img_item);
            textView = (TextView) itemView.findViewById(R.id.article_recycler_imgcount);
        }
    }
}
