package com.E2I3.chaebunchaebun;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CommunityListAdapter extends RecyclerView.Adapter<CommunityListAdapter.ViewHolder> {
    private ArrayList<CommunityListItem> communityListItems = null;
    private CommunityListAdapter.OnItemClickListener mListener = null;
    private CommunityListAdapter.OnLikeClickListener likeClickListener = null;

    private TextView toastText;
    private Toast toast;

    public interface OnItemClickListener {
        void onItemClick(View view, int pos);
    }

    public interface OnLikeClickListener {
        void OnLikeClick(View view, int pos);
    }

    public void setOnItemClickListener(CommunityListAdapter.OnItemClickListener listener) {
        this.mListener = listener ;
    }

    public void setLikeClickListener(CommunityListAdapter.OnLikeClickListener likeClickListener) {
        this.likeClickListener = likeClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout categoryListTop;
        ImageView communityProfileImg;
        TextView communityNickname;
        TextView communityWritingDate;
        TextView communityLocation;
        TextView communityContent;
        TextView communityLikeCount;
        TextView communityCommentCount;
        ImageView communityLikeBtn;
        HorizontalScrollView communityScrollView;
        ImageView communityImg1;
        ImageView communityImg2;
        ImageView communityImg3;
        ImageView communityImg4;
        ImageView communityImg5;
        public ViewHolder(@NonNull View communityView) {
            super(communityView);
            communityProfileImg = (ImageView) communityView.findViewById(R.id.community_profileimg);
            communityNickname = (TextView) communityView.findViewById(R.id.community_nickname);
            communityWritingDate = (TextView) communityView.findViewById(R.id.community_date);
            communityLocation = (TextView) communityView.findViewById(R.id.community_location_txt);
            communityContent = (TextView) communityView.findViewById(R.id.community_content);
            communityLikeCount = (TextView) communityView.findViewById(R.id.community_like_cnt);
            communityCommentCount = (TextView) communityView.findViewById(R.id.community_comment_cnt);
            communityLikeBtn = (ImageView) communityView.findViewById(R.id.community_like_btn);
            communityScrollView = (HorizontalScrollView) communityView.findViewById(R.id.community_scrollview);
            communityImg1 = (ImageView) communityView.findViewById(R.id.community_img1);
            communityImg2 = (ImageView) communityView.findViewById(R.id.community_img2);
            communityImg3 = (ImageView) communityView.findViewById(R.id.community_img3);
            communityImg4 = (ImageView) communityView.findViewById(R.id.community_img4);
            communityImg5 = (ImageView) communityView.findViewById(R.id.community_img5);

            communityView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(mListener != null) {
                            mListener.onItemClick(view, pos);
                        }
                    }
                }
            });

            communityLikeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        CommunityListItem data = getItem(pos);
                        if(likeClickListener != null) {
                            if(data.isSame() == true) {
                                toastText.setText("본인의 글은 좋아요 할 수 없어요!");
                                toast.show();
                            } else {
                                if(data.isLike() == 0) {
                                    toastText.setText("좋아요를 눌렀어요!");
                                    toast.show();
                                    communityLikeBtn.setImageResource(R.drawable.communitylist_btn_like_red);
                                    int getLikeCount = data.getLikeCount();
                                    communityLikeCount.setText(String.valueOf(getLikeCount + 1));
                                    communityLikeCount.setTextColor(Color.parseColor("#f47a69"));
                                    likeClickListener.OnLikeClick(view, pos);
                                } else if(data.isLike() == 1) {
                                    toastText.setText("좋아요를 취소했어요!");
                                    toast.show();
                                    communityLikeBtn.setImageResource(R.drawable.communitylist_btn_like);
                                    int getLikeCount = data.getLikeCount();
                                    communityLikeCount.setText(String.valueOf(getLikeCount - 1));
                                    communityLikeCount.setTextColor(Color.parseColor("#9a9792"));
                                    likeClickListener.OnLikeClick(view, pos);
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    public CommunityListAdapter(ArrayList<CommunityListItem> communityContent) {
        this.communityListItems = communityContent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.custom_communitylist, parent, false);
        View customToast = inflater.inflate(R.layout.custom_report_toast, (ViewGroup) parent.findViewById(R.id.custom_toast_layout));

        toastText = (TextView) customToast.findViewById(R.id.custom_toast_text);
        toast = new Toast(parent.getContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(customToast);

        CommunityListAdapter.ViewHolder cvh = new CommunityListAdapter.ViewHolder(view);

        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommunityListItem communityListItem = communityListItems.get(position);

        Glide.with(holder.itemView.getContext()).load(communityListItem.getProfile()).into(holder.communityProfileImg);
        holder.communityNickname.setText(communityListItem.getNickname());
        holder.communityWritingDate.setText(communityListItem.getCreateAt());
        holder.communityLocation.setText(communityListItem.getAddress());
        holder.communityContent.setText(communityListItem.getContent());
        if(communityListItem.getImg1().isEmpty() || communityListItem.getImg2().equals("null")){
            holder.communityScrollView.setVisibility(View.GONE);
        } else if(communityListItem.getImg2().isEmpty() || communityListItem.getImg2().equals("null")){
            Glide.with(holder.itemView.getContext()).load(communityListItem.getImg1()).into(holder.communityImg1);
            holder.communityImg2.setVisibility(View.GONE);
            holder.communityImg3.setVisibility(View.GONE);
            holder.communityImg4.setVisibility(View.GONE);
            holder.communityImg5.setVisibility(View.GONE);
        } else if(communityListItem.getImg3().isEmpty() || communityListItem.getImg3().equals("null")){
            Glide.with(holder.itemView.getContext()).load(communityListItem.getImg1()).into(holder.communityImg1);
            Glide.with(holder.itemView.getContext()).load(communityListItem.getImg2()).into(holder.communityImg2);
            holder.communityImg3.setVisibility(View.GONE);
            holder.communityImg4.setVisibility(View.GONE);
            holder.communityImg5.setVisibility(View.GONE);
        } else if(communityListItem.getImg4().isEmpty() || communityListItem.getImg4().equals("null")){
            Glide.with(holder.itemView.getContext()).load(communityListItem.getImg1()).into(holder.communityImg1);
            Glide.with(holder.itemView.getContext()).load(communityListItem.getImg2()).into(holder.communityImg2);
            Glide.with(holder.itemView.getContext()).load(communityListItem.getImg3()).into(holder.communityImg3);
            holder.communityImg4.setVisibility(View.GONE);
            holder.communityImg5.setVisibility(View.GONE);
        } else if(communityListItem.getImg5().isEmpty() || communityListItem.getImg5().equals("null")){
            Glide.with(holder.itemView.getContext()).load(communityListItem.getImg1()).into(holder.communityImg1);
            Glide.with(holder.itemView.getContext()).load(communityListItem.getImg2()).into(holder.communityImg2);
            Glide.with(holder.itemView.getContext()).load(communityListItem.getImg3()).into(holder.communityImg3);
            Glide.with(holder.itemView.getContext()).load(communityListItem.getImg4()).into(holder.communityImg4);
            holder.communityImg5.setVisibility(View.GONE);
        } else {
            Glide.with(holder.itemView.getContext()).load(communityListItem.getImg1()).into(holder.communityImg1);
            Glide.with(holder.itemView.getContext()).load(communityListItem.getImg2()).into(holder.communityImg2);
            Glide.with(holder.itemView.getContext()).load(communityListItem.getImg3()).into(holder.communityImg3);
            Glide.with(holder.itemView.getContext()).load(communityListItem.getImg4()).into(holder.communityImg4);
            Glide.with(holder.itemView.getContext()).load(communityListItem.getImg5()).into(holder.communityImg5);
        }

        if(communityListItem.isLike() == 0) {
            holder.communityLikeBtn.setImageResource(R.drawable.communitylist_btn_like);
            holder.communityLikeCount.setTextColor(Color.parseColor("#9a9792"));
        } else {
            holder.communityLikeBtn.setImageResource(R.drawable.communitylist_btn_like_red);
            holder.communityLikeCount.setTextColor(Color.parseColor("#f47a69"));
        }
        holder.communityLikeCount.setText(String.valueOf(communityListItem.getLikeCount()));
        holder.communityCommentCount.setText(String.valueOf(communityListItem.getCommCount()));
    }

    @Override
    public int getItemCount() {
        return communityListItems.size();
    }

    public CommunityListItem getItem(int position){
        return communityListItems.get(position);
    }
}
