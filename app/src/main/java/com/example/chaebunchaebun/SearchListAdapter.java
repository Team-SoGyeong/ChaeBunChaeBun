package com.example.chaebunchaebun;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class SearchListAdapter extends BaseAdapter {
    private ArrayList<SearchListItem> mItems = new ArrayList<SearchListItem>();
    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public SearchListItem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_listview, parent, false);
        }

        TextView search_list_title = (TextView) convertView.findViewById(R.id.search_list_title);
        TextView search_list_date = (TextView) convertView.findViewById(R.id.search_list_date);
        TextView search_list_people = (TextView) convertView.findViewById(R.id.search_list_people);
        TextView search_list_price = (TextView) convertView.findViewById(R.id.search_list_price);

        SearchListItem searchListItem = getItem(position);

        search_list_title.setText(searchListItem.getTitle());
        search_list_date.setText(searchListItem.getDate());
        search_list_people.setText(searchListItem.getPeople());
        search_list_price.setText(searchListItem.getPrice());

        return convertView;
    }

    public void addItem(String title, String date, String people, String price) {
        SearchListItem mItem = new SearchListItem();

        mItem.setTitle(title);
        mItem.setDate(date);
        mItem.setPeople(people);
        mItem.setPrice(price);

        mItems.add(mItem);
    }
}
