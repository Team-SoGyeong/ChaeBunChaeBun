package com.E2I3.chaebunchaebun;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MypageCommunityListAdapter  extends RecyclerView.Adapter<MypageCommunityListAdapter.ViewHolder> {
    private ArrayList<MypageCommunityListItems> communityListItems = null;
    private MypageCommunityListAdapter.OnItemClickListener mListener = null;
    private MypageCommunityListAdapter.OnModalClickListener modalClickListener = null;
    private MypageCommunityListAdapter.OnLikeClickListener likeClickListener = null;

    private TextView toastText;
    private Toast toast;

    public interface OnItemClickListener {
        void onItemClick(View view, int pos);
    }

    public interface OnModalClickListener {
        void OnModlaClick(View view, int pos);
    }

    public interface OnLikeClickListener {
        void OnLikeClick(View view, int pos);
    }

    public void setOnItemClickListener(MypageCommunityListAdapter.OnItemClickListener listener) {
        this.mListener = listener ;
    }

    public void setModalClickListener(MypageCommunityListAdapter.OnModalClickListener modalClickListener) {
        this.modalClickListener = modalClickListener;
    }

    public void setLikeClickListener(MypageCommunityListAdapter.OnLikeClickListener likeClickListener) {
        this.likeClickListener = likeClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView communityListContent;
        TextView communityListLikeCount;
        TextView communityListCommentCount;
        ImageButton communityListModalBtn;
        ImageButton communityListLikeBtn;
        public ViewHolder(@NonNull @NotNull View communityView) {
            super(communityView);
            communityListContent = (TextView) communityView.findViewById(R.id.communitylist_content);
            communityListLikeCount = (TextView) communityView.findViewById(R.id.communitylist_likecount);
            communityListCommentCount = (TextView) communityView.findViewById(R.id.communitylist_commentcount);
            communityListModalBtn = (ImageButton) communityView.findViewById(R.id.community_modalbtn);
            communityListLikeBtn = (ImageButton) communityView.findViewById(R.id.communitylist_likebtn);

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

            communityListModalBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(modalClickListener != null){
                            modalClickListener.OnModlaClick(view, pos);
                        }
                    }
                }
            });
/*
            communityListLikeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        MypageCommunityListItems data = getItem(pos);
                        if(likeClickListener != null) {
                            if(data.isSame() == true) {
                                toastText.setText("본인의 글은 찜 할 수 없어요!");
                                toast.show();
                            } else {
                                if(data.getIsWish() == 0) {
                                    toastText.setText("찜 했어요!");
                                    toast.show();
                                    communityListLikeBtn.setImageResource(R.drawable.mypagelist_btn_favorite_filled);
                                    int getLikeCount = Integer.parseInt(data.getLikeCount());
                                    communityListLikeCount.setText(String.valueOf(getLikeCount + 1));
                                    likeClickListener.OnLikeClick(view, pos);
                                } else if(data.getIsWish() == 1) {
                                    toastText.setText("찜을 취소했어요!");
                                    toast.show();
                                    communityListLikeBtn.setImageResource(R.drawable.mypagelist_btn_favorite_unfilled);
                                    int getLikeCount = Integer.parseInt(data.getLikeCount());
                                    communityListLikeCount.setText(String.valueOf(getLikeCount - 1));
                                    likeClickListener.OnLikeClick(view, pos);
                                }
                            }
                        }
                    }
                }
            });

            */
        }
    }

    public MypageCommunityListAdapter(ArrayList<MypageCommunityListItems> communityContent) {
        this.communityListItems = communityContent;
    }
    @Override
    public MypageCommunityListAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.custom_mypage_community, parent, false);
        View customToast = inflater.inflate(R.layout.custom_report_toast, (ViewGroup) parent.findViewById(R.id.custom_toast_layout));

        toastText = (TextView) customToast.findViewById(R.id.custom_toast_text);
        toast = new Toast(parent.getContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(customToast);

        MypageCommunityListAdapter.ViewHolder cvh = new MypageCommunityListAdapter.ViewHolder(view);

        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MypageCommunityListAdapter.ViewHolder holder, int position) {
        MypageCommunityListItems communityListItem = communityListItems.get(position);

        holder.communityListContent.setText(communityListItem.getContent());
        holder.communityListLikeCount.setText(communityListItem.getLikeCount());
        holder.communityListCommentCount.setText(communityListItem.getCommentCount());

        if(communityListItem.getIsLike() == 0) {
            holder.communityListLikeBtn.setImageResource(R.drawable.communitylist_btn_like);
        } else {
            holder.communityListLikeBtn.setImageResource(R.drawable.communitylist_btn_like_red);
        }
    }

    @Override
    public int getItemCount() {
        return communityListItems.size();
    }

    public MypageCommunityListItems getItem(int position){
        return communityListItems.get(position);
    }
}
