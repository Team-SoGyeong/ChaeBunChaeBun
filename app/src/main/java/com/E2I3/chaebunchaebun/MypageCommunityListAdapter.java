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
//        TextView communityListTitle;
//        TextView communityListNickname;
//        TextView communityListWritingDate;
        TextView communityListContent;
//        ImageView communityListImg1;
//        ImageView communityListImg2;
//        ImageView communityListImg3;
//        ImageView communityListImg4;
//        ImageView communityListImg5;
        TextView communityListLikeCount;
        TextView communityListCommentCount;
        ImageButton communityListLikeBtn;
        public ViewHolder(@NonNull @NotNull View communityView) {
            super(communityView);
/*          categoryListProfile = (ImageView) categoryView.findViewById(R.id.categorylist_profile_img);
            communityListTitle = (TextView) communityView.findViewById(R.id.categorylist_title);
            communityListNickname = (TextView) communityView.findViewById(R.id.categorylist_nickname);
            communityListWritingDate = (TextView) communityView.findViewById(R.id.categorylist_writing_date);
*/
            communityListContent = (TextView) communityView.findViewById(R.id.communitylist_content);
/*          communityListImg1 = (ImageView) communityView.findViewById(R.id.categorylist_content_img_1);
            communityListImg2 = (ImageView) communityView.findViewById(R.id.categorylist_content_img_2);
            communityListImg3 = (ImageView) communityView.findViewById(R.id.categorylist_content_img_3);
            communityListImg4 = (ImageView) communityView.findViewById(R.id.categorylist_content_img_4);
            communityListImg5 = (ImageView) communityView.findViewById(R.id.categorylist_content_img_5);
            communityListBuyingDate = (TextView) communityView.findViewById(R.id.categorylist_buying_date);
            communityListPeople = (TextView) communityView.findViewById(R.id.categorylist_people);
            communityListPrice = (TextView) communityView.findViewById(R.id.categorylist_price);
*/
            communityListLikeCount = (TextView) communityView.findViewById(R.id.communitylist_likecount);
            communityListCommentCount = (TextView) communityView.findViewById(R.id.communitylist_commentcount);
//            communityListModalBtn = (ImageButton) communityView.findViewById(R.id.category_list_modalbtn);
//            communityListReceipt = (ImageView) communityView.findViewById(R.id.categorylist_recipeicon);
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
/*
            categoryListModalBtn.setOnClickListener(new View.OnClickListener() {
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
*/
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
                                    communityListLikeBtn.setImageResource(R.drawable.categorylist_btn_favorite_filled);
                                    int getLikeCount = Integer.parseInt(data.getLikeCount());
                                    communityListLikeCount.setText(String.valueOf(getLikeCount + 1));
                                    likeClickListener.OnLikeClick(view, pos);
                                } else if(data.getIsWish() == 1) {
                                    toastText.setText("찜을 취소했어요!");
                                    toast.show();
                                    communityListLikeBtn.setImageResource(R.drawable.categorylist_btn_favorite_unfilled);
                                    int getLikeCount = Integer.parseInt(data.getLikeCount());
                                    communityListLikeCount.setText(String.valueOf(getLikeCount - 1));
                                    likeClickListener.OnLikeClick(view, pos);
                                }
                            }
                        }
                    }
                }
            });
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

        //Glide.with(holder.itemView.getContext()).load(categoryListItem.getProfile()).into(holder.categoryListProfile);
//        holder.communityListTitle.setText(communityListItem.getTitle());
//        holder.communityListNickname.setText(communityListItem.getNickname());
//        holder.communityListWritingDate.setText((communityListItem.getWritingDate()));
        holder.communityListContent.setText(communityListItem.getContent());
/*
        if(communityListItem.getImg2().isEmpty() || communityListItem.getImg2().equals("null")){
            Glide.with(holder.itemView.getContext()).load(communityListItem.getImg1()).into(holder.communityListImg1);
            holder.communityListImg2.setVisibility(View.GONE);
            holder.communityListImg3.setVisibility(View.GONE);
            holder.communityListImg4.setVisibility(View.GONE);
            holder.communityListImg5.setVisibility(View.GONE);
        } else if(communityListItem.getImg3().isEmpty() || communityListItem.getImg3().equals("null")){
            Glide.with(holder.itemView.getContext()).load(communityListItem.getImg1()).into(holder.communityListImg1);
            Glide.with(holder.itemView.getContext()).load(communityListItem.getImg2()).into(holder.communityListImg2);
            holder.communityListImg3.setVisibility(View.GONE);
            holder.communityListImg4.setVisibility(View.GONE);
            holder.communityListImg5.setVisibility(View.GONE);
        } else if(communityListItem.getImg4().isEmpty() || communityListItem.getImg4().equals("null")){
            Glide.with(holder.itemView.getContext()).load(communityListItem.getImg1()).into(holder.communityListImg1);
            Glide.with(holder.itemView.getContext()).load(communityListItem.getImg2()).into(holder.communityListImg2);
            Glide.with(holder.itemView.getContext()).load(communityListItem.getImg3()).into(holder.communityListImg3);
            holder.communityListImg4.setVisibility(View.GONE);
            holder.communityListImg5.setVisibility(View.GONE);
        } else if(communityListItem.getImg5().isEmpty() || communityListItem.getImg5().equals("null")){
            Glide.with(holder.itemView.getContext()).load(communityListItem.getImg1()).into(holder.communityListImg1);
            Glide.with(holder.itemView.getContext()).load(communityListItem.getImg2()).into(holder.communityListImg2);
            Glide.with(holder.itemView.getContext()).load(communityListItem.getImg3()).into(holder.communityListImg3);
            Glide.with(holder.itemView.getContext()).load(communityListItem.getImg4()).into(holder.communityListImg4);
            holder.communityListImg5.setVisibility(View.GONE);
        } else {
            Glide.with(holder.itemView.getContext()).load(communityListItem.getImg1()).into(holder.communityListImg1);
            Glide.with(holder.itemView.getContext()).load(communityListItem.getImg2()).into(holder.communityListImg2);
            Glide.with(holder.itemView.getContext()).load(communityListItem.getImg3()).into(holder.communityListImg3);
            Glide.with(holder.itemView.getContext()).load(communityListItem.getImg4()).into(holder.communityListImg4);
            Glide.with(holder.itemView.getContext()).load(communityListItem.getImg5()).into(holder.communityListImg5);
        }
*/
        holder.communityListLikeCount.setText(communityListItem.getLikeCount());
        holder.communityListCommentCount.setText(communityListItem.getCommentCount());


        if(communityListItem.getIsWish() == 0) {
            holder.communityListLikeBtn.setImageResource(R.drawable.categorylist_btn_favorite_unfilled);
        } else {
            holder.communityListLikeBtn.setImageResource(R.drawable.categorylist_btn_favorite_filled);
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
