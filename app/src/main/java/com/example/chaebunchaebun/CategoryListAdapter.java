package com.example.chaebunchaebun;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoryListAdapter extends BaseAdapter {
    private ArrayList<CategoryListItem> categoryListItems = new ArrayList<CategoryListItem>();
    @Override
    public int getCount() {
        return categoryListItems.size();
    }

    @Override
    public CategoryListItem getItem(int position) {
        return categoryListItems.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View categoryView, ViewGroup parent) {
        Context context = parent.getContext();

        if(categoryView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            categoryView = inflater.inflate(R.layout.categorylist_custom, parent, false);
        }

        TextView categoryListTitle = (TextView) categoryView.findViewById(R.id.categorylist_title);
        TextView categoryListNickname = (TextView) categoryView.findViewById(R.id.categorylist_nickname);
        TextView categoryListWritingDate = (TextView) categoryView.findViewById(R.id.categorylist_writing_date);
        TextView categoryListContent = (TextView) categoryView.findViewById(R.id.categorylist_content);
        TextView categoryListBuyingDate = (TextView) categoryView.findViewById(R.id.categorylist_buying_date);
        TextView categoryListPeople = (TextView) categoryView.findViewById(R.id.categorylist_people);
        TextView categoryListPrice = (TextView) categoryView.findViewById(R.id.categorylist_price);
        TextView categoryListLikeCount = (TextView) categoryView.findViewById(R.id.categorylist_likecount);
        TextView categoryListCommentCount = (TextView) categoryView.findViewById(R.id.categorylist_commentcount);

        CategoryListItem categoryListItem = getItem(position);

        categoryListTitle.setText(categoryListItem.getTitle());
        categoryListNickname.setText(categoryListItem.getNickname());
        categoryListWritingDate.setText((categoryListItem.getWritingDate()));
        categoryListContent.setText(categoryListItem.getContent());
        categoryListBuyingDate.setText(categoryListItem.getBuyingDate());
        categoryListPeople.setText(categoryListItem.getPeople());
        categoryListPrice.setText(categoryListItem.getPrice());
        categoryListLikeCount.setText(categoryListItem.getLikeCount());
        categoryListCommentCount.setText(categoryListItem.getCommentCount());

        return categoryView;
    }

    public void addItem(String categoryTitle, String categoryNickname, String categoryWritingDate, String categoryContent,
                        String categoryBuyingDate, String categoryPeople, String categoryPrice, String categoryLikeCount, String categoryCommentcount) {
        CategoryListItem categoryListItem = new CategoryListItem();

        categoryListItem.setTitle(categoryTitle);
        categoryListItem.setNickname(categoryNickname);
        categoryListItem.setWritingDate(categoryWritingDate);
        categoryListItem.setContent(categoryContent);
        categoryListItem.setBuyingDate(categoryBuyingDate);
        categoryListItem.setPeople(categoryPeople);
        categoryListItem.setPrice(categoryPrice);
        categoryListItem.setLikeCount(categoryLikeCount);
        categoryListItem.setCommentCount(categoryCommentcount);

        categoryListItems.add(categoryListItem);
    }
}
