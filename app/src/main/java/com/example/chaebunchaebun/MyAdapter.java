package com.example.chaebunchaebun;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;


public class MyAdapter extends BaseAdapter {
    private ArrayList<MyItem> mItems = new ArrayList<MyItem>();
    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public MyItem getItem(int position) {
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
            convertView = inflater.inflate(R.layout.listview_custom, parent, false);
        }

        TextView tv_title = (TextView) convertView.findViewById(R.id.tv_title);
        TextView tv_nickname = (TextView) convertView.findViewById(R.id.tv_nickname);
        TextView tv_location = (TextView) convertView.findViewById(R.id.tv_location);

        MyItem myItem = getItem(position);

        tv_title.setText(myItem.getTitle());
        tv_nickname.setText(myItem.getNickname());
        tv_location.setText(myItem.getLocation());

        return convertView;
    }

    public void addItem(int count, String title, String nickname, String location) {
        MyItem mItem = new MyItem();

        mItem.setCount(count);
        mItem.setTitle(title);
        mItem.setNickname(nickname);
        mItem.setLocation(location);

        mItems.add(mItem);
    }
}
