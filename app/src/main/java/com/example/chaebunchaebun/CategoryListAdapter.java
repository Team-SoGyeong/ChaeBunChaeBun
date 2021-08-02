package com.example.chaebunchaebun;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {
    private ArrayList<CategoryListItem> categoryListItems = null;
    private OnItemClickListener mListener = null;

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout categoryListTop;
        TextView categoryListTitle;
        TextView categoryListNickname;
        TextView categoryListWritingDate;
        TextView categoryListContent;
        TextView categoryListBuyingDate;
        TextView categoryListPeople;
        TextView categoryListPrice;
        TextView categoryListLikeCount;
        TextView categoryListCommentCount;
        public ViewHolder(@NonNull @NotNull View categoryView) {
            super(categoryView);
            categoryListTop = (LinearLayout) categoryView.findViewById(R.id.category_list_top);
            categoryListTitle = (TextView) categoryView.findViewById(R.id.categorylist_title);
            categoryListNickname = (TextView) categoryView.findViewById(R.id.categorylist_nickname);
            categoryListWritingDate = (TextView) categoryView.findViewById(R.id.categorylist_writing_date);
            categoryListContent = (TextView) categoryView.findViewById(R.id.categorylist_content);
            categoryListBuyingDate = (TextView) categoryView.findViewById(R.id.categorylist_buying_date);
            categoryListPeople = (TextView) categoryView.findViewById(R.id.categorylist_people);
            categoryListPrice = (TextView) categoryView.findViewById(R.id.categorylist_price);
            categoryListLikeCount = (TextView) categoryView.findViewById(R.id.categorylist_likecount);
            categoryListCommentCount = (TextView) categoryView.findViewById(R.id.categorylist_commentcount);

            categoryListTop.setOnClickListener(new View.OnClickListener() {
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
        }
    }

    public CategoryListAdapter(ArrayList<CategoryListItem> categoryContent) {
        this.categoryListItems = categoryContent;
    }
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.categorylist_custom, parent, false);
        CategoryListAdapter.ViewHolder cvh = new CategoryListAdapter.ViewHolder(view);

        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        CategoryListItem categoryListItem = categoryListItems.get(position);

        holder.categoryListTitle.setText(categoryListItem.getTitle());
        holder.categoryListNickname.setText(categoryListItem.getNickname());
        holder.categoryListWritingDate.setText((categoryListItem.getWritingDate()));
        holder.categoryListContent.setText(categoryListItem.getContent());
        holder.categoryListBuyingDate.setText(categoryListItem.getBuyingDate());
        holder.categoryListPeople.setText(categoryListItem.getPeople());
        holder.categoryListPrice.setText(categoryListItem.getPrice());
        holder.categoryListLikeCount.setText(categoryListItem.getLikeCount());
        holder.categoryListCommentCount.setText(categoryListItem.getCommentCount());
    }

    @Override
    public int getItemCount() {
        return categoryListItems.size();
    }
}
