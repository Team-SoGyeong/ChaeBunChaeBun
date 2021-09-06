package com.example.chaebunchaebun;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainWriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainWriteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mainPostingList;
    private ArrayList<HomeListItem> homeListItems;
    private HomeListAdapter homeListAdapter;
    private LinearLayoutManager hLayoutManager;

    TextView mainPostingNolist;
    String state = "0";
    String platform = "0";
    String userId = null;

    public MainWriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainLastFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainWriteFragment newInstance(String param1, String param2) {
        MainWriteFragment fragment = new MainWriteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void getUserId(String userId){
        this.userId = userId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        homeListItems = new ArrayList<HomeListItem>();
        getMyPostingList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainWrite = inflater.inflate(R.layout.fragment_main_write, container, false);

        mainPostingList = mainWrite.findViewById(R.id.main_write_list);
        mainPostingNolist = mainWrite.findViewById(R.id.write_text);

        if(homeListItems.isEmpty()) {
            mainPostingNolist.setVisibility(View.VISIBLE);
            mainPostingList.setVisibility(View.GONE);
        } else {
            mainPostingNolist.setVisibility(View.GONE);

            hLayoutManager = new LinearLayoutManager(getContext());
            mainPostingList.setLayoutManager(hLayoutManager);
            MainRecyclerDecoration mainRecyclerDecoration = new MainRecyclerDecoration(40);
            mainPostingList.addItemDecoration(mainRecyclerDecoration);
            mainPostingList.setLayoutManager(new LinearLayoutManager(getContext()){
                @Override
                public boolean canScrollHorizontally() {
                    return false;
                }

                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            homeListAdapter = new HomeListAdapter(homeListItems);
            homeListAdapter.setOnItemClickListener(new HomeListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int pos) {
                    String postId = String.valueOf(homeListAdapter.getItem(pos).getPostId());
                    Bundle articleBundle = new Bundle();
                    articleBundle.putString("userId", userId);
                    articleBundle.putString("postId", postId);
                    FragmentTransaction articleTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    ArticleFragment articleFragment = new ArticleFragment();
                    articleFragment.setArguments(articleBundle);
                    articleTransaction.replace(R.id.bottom_frame, articleFragment);
                    articleTransaction.addToBackStack(null);
                    articleTransaction.commit();
                }
            });
            homeListAdapter.setModalClickListener(new HomeListAdapter.OnModalClickListener() {
                @Override
                public void OnModlaClick(View view, int pos) {
                    String postId = String.valueOf(homeListAdapter.getItem(pos).getPostId());
                    int categoryId = homeListAdapter.getItem(pos).getCategoryId();
                    Bundle args = new Bundle();
                    args.putString("userId", userId);
                    args.putString("postId", postId);
                    args.putInt("categoryId", categoryId);
                    MyBottomSheetDialog myBottomSheetDialog = MyBottomSheetDialog.getInstance();
                    myBottomSheetDialog.setArguments(args);
                    myBottomSheetDialog.show(getChildFragmentManager(), "mybottomsheet");
                }
            });
            mainPostingList.setAdapter(homeListAdapter);
        }

        return mainWrite;
    }

    public void getMyPostingList(){
        homeListItems.clear();
        String resultText = "[NULL]";

        try {
            resultText = new GetTask("mypage/mypost/" + this.userId + "/" + this.platform +"/" + this.state).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObject = new JSONObject(resultText);
            String data = jsonObject.getString("data");
            JSONArray jsonArray = new JSONArray(data);
            for(int i = 0; i < 3; i++){
                JSONObject subJsonObject = jsonArray.getJSONObject(i);
                int categoryId = subJsonObject.getInt("category_id");
                int postId = subJsonObject.getInt("post_id");
                int userId = subJsonObject.getInt("wish_id");
                String img = subJsonObject.getString("url");
                String title = subJsonObject.getString("title");
                String buyDate = subJsonObject.getString("buy_date");
                String member = subJsonObject.getString("members");
                String perPrice = subJsonObject.getString("per_price");
                String writtenBy = subJsonObject.getString("witten_by");
                int isAuth = subJsonObject.getInt("isAuth");

                homeListItems.add(new HomeListItem(img, title, buyDate, member, perPrice, writtenBy, isAuth, postId, userId, categoryId));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}