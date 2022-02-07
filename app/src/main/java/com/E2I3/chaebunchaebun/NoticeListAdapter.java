package com.E2I3.chaebunchaebun;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NoticeListAdapter extends RecyclerView.Adapter<NoticeListAdapter.ViewHolder> {
    private ArrayList<NoticeListItem> noticeListItems = new ArrayList<NoticeListItem>();

    private NoticeListAdapter.OnItemClickListener nListener = null;

    public interface OnItemClickListener {
        void onItemClick(View view, int pos);
    }

    public void setOnItemClickListener(NoticeListAdapter.OnItemClickListener listener) {
        this.nListener = listener ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout notice_list_top;
        ImageView notice_list_like;
        ImageView notice_list_comment;
        ImageView notice_list_img;
        TextView notice_list_title;
        TextView notice_list_writing_date;
        ImageView notice_list_isauth;
        TextView notice_list_content;
        TextView notice_list_nickname;
        TextView notice_list_ment;
        public ViewHolder(@NonNull View noticeView) {
            super(noticeView);
            notice_list_top = (LinearLayout) noticeView.findViewById(R.id.notice_list_top);
            notice_list_like = (ImageView) noticeView.findViewById(R.id.notice_list_like);
            notice_list_comment = (ImageView) noticeView.findViewById(R.id.notice_list_comment);
            notice_list_img = (ImageView) noticeView.findViewById(R.id.notice_list_img);
            notice_list_title = (TextView) noticeView.findViewById(R.id.notice_list_title);
            notice_list_writing_date = (TextView) noticeView.findViewById(R.id.notice_list_writing_date);
            notice_list_isauth = (ImageView) noticeView.findViewById(R.id.notice_list_isauth);
            notice_list_content = (TextView) noticeView.findViewById(R.id.notice_list_content);
            notice_list_nickname = (TextView) noticeView.findViewById(R.id.notice_list_nickname);
            notice_list_ment = (TextView) noticeView.findViewById(R.id.notice_list_ment);

            noticeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(nListener != null) {
                            nListener.onItemClick(view, pos);
                        }
                    }
                }
            });
        }
    }

    public NoticeListAdapter(ArrayList<NoticeListItem> noticeContent) {
        this.noticeListItems = noticeContent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.custom_noticelist, parent, false);
        NoticeListAdapter.ViewHolder nvh = new NoticeListAdapter.ViewHolder(view);

        return nvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NoticeListItem noticeListItem = noticeListItems.get(position);

        holder.notice_list_nickname.setText(String.valueOf(noticeListItem.getNickname()));
        if(noticeListItem.getIsClick() == "N") {
            holder.notice_list_top.setBackgroundResource(R.drawable.custom_notice_noread);
        } else {
            holder.notice_list_top.setBackgroundResource(R.drawable.custom_homelist_background);
        }
        if(noticeListItem.getCaseType() == "scrap"){
            holder.notice_list_ment.setText("좋아요를 눌렀어요!");
            holder.notice_list_like.setVisibility(View.VISIBLE);
            holder.notice_list_comment.setVisibility(View.GONE);
        } else {
            holder.notice_list_ment.setText("댓글을 달았어요!");
            holder.notice_list_like.setVisibility(View.GONE);
            holder.notice_list_comment.setVisibility(View.VISIBLE);
        }
        Glide.with(holder.itemView.getContext()).load(noticeListItem.getImg()).into(holder.notice_list_img);
        holder.notice_list_title.setText(noticeListItem.getTitle());
        holder.notice_list_writing_date.setText(noticeListItem.getDate());
        holder.notice_list_content.setText(noticeListItem.getContent());
        if(noticeListItem.getIsAuth() == 0) {
            holder.notice_list_isauth.setVisibility(View.GONE);
        } else {
            holder.notice_list_isauth.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return noticeListItems.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public NoticeListItem getItem(int position){
        return noticeListItems.get(position);
    }
}
