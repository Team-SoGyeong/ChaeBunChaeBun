package com.example.chaebunchaebun;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter {
    private ArrayList<CommentItem> cItems = new ArrayList<CommentItem>();
    @Override
    public int getCount() {
        return cItems.size();
    }

    @Override
    public CommentItem getItem(int position) {
        return cItems.get(position);
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

        TextView tv_nickname = (TextView) convertView.findViewById(R.id.search_list_price);
        TextView tv_comment = (TextView) convertView.findViewById(R.id.search_list_date);
        TextView tv_time = (TextView) convertView.findViewById(R.id.search_list_people);

        CommentItem commentItem = getItem(position);

        tv_comment.setText(commentItem.getComment());
        tv_nickname.setText(commentItem.getNickname());
        tv_time.setText(commentItem.getTime());

        return convertView;
    }

    public void addItem(String comment, String nickname, String time) {
        CommentItem cItem = new CommentItem();

        cItem.setComment(comment);
        cItem.setNickname(nickname);
        cItem.setTime(time);

        cItems.add(cItem);
    }
}
