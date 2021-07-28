package com.example.chaebunchaebun;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeListAdapter extends BaseAdapter {
    private ArrayList<HomeListItem> homeListItems = new ArrayList<HomeListItem>();
    @Override
    public int getCount() {
        return homeListItems.size();
    }

    @Override
    public HomeListItem getItem(int position) {
        return homeListItems.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View homeView, ViewGroup parent) {
        Context context = parent.getContext();

        if(homeView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            homeView = inflater.inflate(R.layout.homelist_custom, parent, false);
        }

        ImageView home_list_img = (ImageView) homeView.findViewById(R.id.homelist_img);
        TextView home_list_title = (TextView) homeView.findViewById(R.id.homelist_title);
        TextView home_list_date = (TextView) homeView.findViewById(R.id.homelist_date);
        TextView home_list_people = (TextView) homeView.findViewById(R.id.homelist_people);
        TextView home_list_price = (TextView) homeView.findViewById(R.id.homelist_price);

        HomeListItem homeListItem = getItem(position);

        home_list_img.setImageDrawable(homeListItem.getImg());
        home_list_title.setText(homeListItem.getTitle());
        home_list_date.setText(homeListItem.getDate());
        home_list_people.setText(homeListItem.getPeople());
        home_list_price.setText(homeListItem.getPrice());

        return homeView;
    }

    public void addItem(Drawable img, String title, String date, String people, String price) {
        HomeListItem homeItem = new HomeListItem();

        homeItem.setImg(img);
        homeItem.setTitle(title);
        homeItem.setDate(date);
        homeItem.setPeople(people);
        homeItem.setPrice(price);

        homeListItems.add(homeItem);
    }
}
