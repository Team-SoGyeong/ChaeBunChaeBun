package com.example.chaebunchaebun;

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

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {
    private ArrayList<CategoryListItem> categoryListItems = null;
    private OnItemClickListener mListener = null;
    private OnModalClickListener modalClickListener = null;
    private OnLikeClickListener likeClickListener = null;

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

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }

    public void setModalClickListener(OnModalClickListener modalClickListener) {
        this.modalClickListener = modalClickListener;
    }

    public void setLikeClickListener(OnLikeClickListener likeClickListener) {
        this.likeClickListener = likeClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout categoryListTop;
        ImageView categoryListProfile;
        TextView categoryListTitle;
        TextView categoryListNickname;
        TextView categoryListWritingDate;
        TextView categoryListContent;
        ImageView categoryListImg1;
        ImageView categoryListImg2;
        ImageView categoryListImg3;
        ImageView categoryListImg4;
        ImageView categoryListImg5;
        TextView categoryListBuyingDate;
        TextView categoryListPeople;
        TextView categoryListPrice;
        TextView categoryListLikeCount;
        TextView categoryListCommentCount;
        ImageButton categoryListModalBtn;
        ImageView categoryListReceipt;
        ImageView categoryListLikeBtn;
        public ViewHolder(@NonNull @NotNull View categoryView) {
            super(categoryView);
            categoryListProfile = (ImageView) categoryView.findViewById(R.id.categorylist_profile_img);
            categoryListTitle = (TextView) categoryView.findViewById(R.id.categorylist_title);
            categoryListNickname = (TextView) categoryView.findViewById(R.id.categorylist_nickname);
            categoryListWritingDate = (TextView) categoryView.findViewById(R.id.categorylist_writing_date);
            categoryListContent = (TextView) categoryView.findViewById(R.id.categorylist_content);
            categoryListImg1 = (ImageView) categoryView.findViewById(R.id.categorylist_content_img_1);
            categoryListImg2 = (ImageView) categoryView.findViewById(R.id.categorylist_content_img_2);
            categoryListImg3 = (ImageView) categoryView.findViewById(R.id.categorylist_content_img_3);
            categoryListImg4 = (ImageView) categoryView.findViewById(R.id.categorylist_content_img_4);
            categoryListImg5 = (ImageView) categoryView.findViewById(R.id.categorylist_content_img_5);
            categoryListBuyingDate = (TextView) categoryView.findViewById(R.id.categorylist_buying_date);
            categoryListPeople = (TextView) categoryView.findViewById(R.id.categorylist_people);
            categoryListPrice = (TextView) categoryView.findViewById(R.id.categorylist_price);
            categoryListLikeCount = (TextView) categoryView.findViewById(R.id.categorylist_likecount);
            categoryListCommentCount = (TextView) categoryView.findViewById(R.id.categorylist_commentcount);
            categoryListModalBtn = (ImageButton) categoryView.findViewById(R.id.category_list_modalbtn);
            categoryListReceipt = (ImageView) categoryView.findViewById(R.id.categorylist_recipeicon);
            categoryListLikeBtn = (ImageView) categoryView.findViewById(R.id.categorylist_likebtn);

            categoryView.setOnClickListener(new View.OnClickListener() {
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

            categoryListLikeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        CategoryListItem data = getItem(pos);
                        if(likeClickListener != null) {
                            if(data.isSame() == true) {
                                toastText.setText("본인의 글은 찜 할 수 없어요!");
                                toast.show();
                            } else {
                                if(data.getIsWish() == 0) {
                                    toastText.setText("찜 했어요!");
                                    toast.show();
                                    categoryListLikeBtn.setImageResource(R.drawable.type_filled_icon_favorite);
                                    likeClickListener.OnLikeClick(view, pos);
                                } else if(data.getIsWish() == 1) {
                                    toastText.setText("찜을 취소했어요!");
                                    toast.show();
                                    categoryListLikeBtn.setImageResource(R.drawable.type_filled_icon_favorite_border);
                                    likeClickListener.OnLikeClick(view, pos);
                                }
                            }
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

        View view = inflater.inflate(R.layout.custom_categorylist, parent, false);
        View customToast = inflater.inflate(R.layout.custom_report_toast, (ViewGroup) parent.findViewById(R.id.custom_toast_layout));

        toastText = (TextView) customToast.findViewById(R.id.custom_toast_text);
        toast = new Toast(parent.getContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(customToast);

        CategoryListAdapter.ViewHolder cvh = new CategoryListAdapter.ViewHolder(view);

        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        CategoryListItem categoryListItem = categoryListItems.get(position);

        Glide.with(holder.itemView.getContext()).load(categoryListItem.getProfile()).into(holder.categoryListProfile);
        holder.categoryListTitle.setText(categoryListItem.getTitle());
        holder.categoryListNickname.setText(categoryListItem.getNickname());
        holder.categoryListWritingDate.setText((categoryListItem.getWritingDate()));
        holder.categoryListContent.setText(categoryListItem.getContent());
        if(categoryListItem.getImg2().isEmpty()){
            Glide.with(holder.itemView.getContext()).load(categoryListItem.getImg1()).into(holder.categoryListImg1);
            holder.categoryListImg2.setVisibility(View.GONE);
            holder.categoryListImg3.setVisibility(View.GONE);
            holder.categoryListImg4.setVisibility(View.GONE);
            holder.categoryListImg5.setVisibility(View.GONE);
        } else if(categoryListItem.getImg3().isEmpty()){
            Glide.with(holder.itemView.getContext()).load(categoryListItem.getImg1()).into(holder.categoryListImg1);
            Glide.with(holder.itemView.getContext()).load(categoryListItem.getImg2()).into(holder.categoryListImg2);
            holder.categoryListImg3.setVisibility(View.GONE);
            holder.categoryListImg4.setVisibility(View.GONE);
            holder.categoryListImg5.setVisibility(View.GONE);
        } else if(categoryListItem.getImg4().isEmpty()){
            Glide.with(holder.itemView.getContext()).load(categoryListItem.getImg1()).into(holder.categoryListImg1);
            Glide.with(holder.itemView.getContext()).load(categoryListItem.getImg2()).into(holder.categoryListImg2);
            Glide.with(holder.itemView.getContext()).load(categoryListItem.getImg3()).into(holder.categoryListImg3);
            holder.categoryListImg4.setVisibility(View.GONE);
            holder.categoryListImg5.setVisibility(View.GONE);
        } else if(categoryListItem.getImg5().isEmpty()){
            Glide.with(holder.itemView.getContext()).load(categoryListItem.getImg1()).into(holder.categoryListImg1);
            Glide.with(holder.itemView.getContext()).load(categoryListItem.getImg2()).into(holder.categoryListImg2);
            Glide.with(holder.itemView.getContext()).load(categoryListItem.getImg3()).into(holder.categoryListImg3);
            Glide.with(holder.itemView.getContext()).load(categoryListItem.getImg4()).into(holder.categoryListImg4);
            holder.categoryListImg5.setVisibility(View.GONE);
        } else {
            Glide.with(holder.itemView.getContext()).load(categoryListItem.getImg1()).into(holder.categoryListImg1);
            Glide.with(holder.itemView.getContext()).load(categoryListItem.getImg2()).into(holder.categoryListImg2);
            Glide.with(holder.itemView.getContext()).load(categoryListItem.getImg3()).into(holder.categoryListImg3);
            Glide.with(holder.itemView.getContext()).load(categoryListItem.getImg4()).into(holder.categoryListImg4);
            Glide.with(holder.itemView.getContext()).load(categoryListItem.getImg5()).into(holder.categoryListImg5);
        }
        holder.categoryListBuyingDate.setText(categoryListItem.getBuyingDate());
        holder.categoryListPeople.setText(categoryListItem.getPeople());
        holder.categoryListPrice.setText(categoryListItem.getPrice());
        holder.categoryListLikeCount.setText(categoryListItem.getLikeCount());
        holder.categoryListCommentCount.setText(categoryListItem.getCommentCount());
        if(categoryListItem.getIsAuth() == 0) {
            holder.categoryListReceipt.setVisibility(View.GONE);
        } else {
            holder.categoryListReceipt.setVisibility(View.VISIBLE);
        }

        if(categoryListItem.getIsWish() == 0) {
            holder.categoryListLikeBtn.setImageResource(R.drawable.type_filled_icon_favorite_border);
        } else {
            holder.categoryListLikeBtn.setImageResource(R.drawable.type_filled_icon_favorite);
        }
    }

    @Override
    public int getItemCount() {
        return categoryListItems.size();
    }

    public CategoryListItem getItem(int position){
        return categoryListItems.get(position);
    }
}
