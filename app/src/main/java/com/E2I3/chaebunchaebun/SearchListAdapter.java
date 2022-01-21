package com.E2I3.chaebunchaebun;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder> {
    private ArrayList<SearchListItem> searchListItems = null;
    private SearchListAdapter.OnItemClickListener mListener = null;

    public interface OnItemClickListener {
        void onItemClick(View view, int pos);
    }

    public void setOnItemClickListener(SearchListAdapter.OnItemClickListener listener) {
        this.mListener = listener ;
    }

    public SearchListAdapter(ArrayList<SearchListItem> searchListItems) {
        this.searchListItems = searchListItems;
    }

    @NonNull
    @Override
    public SearchListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.custom_listview, parent, false);
        SearchListAdapter.ViewHolder svh = new SearchListAdapter.ViewHolder(view);

        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchListAdapter.ViewHolder holder, int position) {
        SearchListItem searchListItem = searchListItems.get(position);

        holder.search_list_top.setTag(searchListItem.getPostId());
        holder.search_list_title.setText(searchListItem.getTitle());
       /* holder.search_list_date.setText(searchListItem.getDate());
        holder.search_list_people.setText(searchListItem.getPeople());
        holder.search_list_price.setText(searchListItem.getPrice());*/
        holder.search_list_write_date.setText(searchListItem.getWriteDate());
        holder.search_list_content.setText(searchListItem.getContent());
        if(searchListItem.getIsAuth() == 0){
            holder.search_list_receipt.setVisibility(View.GONE);
        } else {
            holder.search_list_receipt.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return searchListItems.size();
    }

    public SearchListItem getItem(int position){
        return searchListItems.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout search_list_top;
        TextView search_list_title;
        TextView search_list_date;
        TextView search_list_people;
        TextView search_list_price;
        ImageView search_list_receipt;
        TextView search_list_write_date;
        TextView search_list_content;
        public ViewHolder(@NonNull View searchView) {
            super(searchView);
            search_list_top = (LinearLayout) searchView.findViewById(R.id.search_list_top);
            search_list_title = (TextView) searchView.findViewById(R.id.search_list_title);
            /*search_list_date = (TextView) searchView.findViewById(R.id.search_list_date);
            search_list_people = (TextView) searchView.findViewById(R.id.search_list_people);
            search_list_price = (TextView) searchView.findViewById(R.id.search_list_price);*/
            search_list_content = (TextView) searchView.findViewById(R.id.searchlist_content);
            search_list_receipt = (ImageView) searchView.findViewById(R.id.search_list_receipt);
            search_list_write_date = (TextView) searchView.findViewById(R.id.search_list_postdate);

            search_list_top.setOnClickListener(new View.OnClickListener() {
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
        }
    }
}
