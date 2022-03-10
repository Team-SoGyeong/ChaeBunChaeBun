package com.E2I3.chaebunchaebun;

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
 * Use the {@link MainNewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainNewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView main_new_list;
    private ArrayList<HomeListItem> homeListItems;
    private HomeListAdapter homeListAdapter;
    private LinearLayoutManager hLayoutManager;

    TextView main_new_text;
    String locationCode = "";
    String userId = "";
    boolean isMyPage = false;

    public MainNewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainNewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainNewFragment newInstance(String param1, String param2) {
        MainNewFragment fragment = new MainNewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void getLocationCode(String locationCode, String userId){
        this.locationCode = locationCode;
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
        homeListItems.clear();

        getNew();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainNew = inflater.inflate(R.layout.fragment_main_new, container, false);

        main_new_text = mainNew.findViewById(R.id.main_new_text);
        main_new_list = mainNew.findViewById(R.id.main_new_list);

        if(homeListItems.isEmpty()) {
            main_new_text.setVisibility(View.VISIBLE);
            main_new_list.setVisibility(View.GONE);
        } else {
            main_new_text.setVisibility(View.GONE);
            main_new_list.setVisibility(View.VISIBLE);

            hLayoutManager = new LinearLayoutManager(getContext());
            main_new_list.setLayoutManager(hLayoutManager);
            MainRecyclerDecoration mainRecyclerDecoration = new MainRecyclerDecoration(40);
            main_new_list.addItemDecoration(mainRecyclerDecoration);
            main_new_list.setLayoutManager(new LinearLayoutManager(getContext()) {
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
                    int categoryId = homeListAdapter.getItem(pos).getCategoryId();
                    Bundle articleBundle = new Bundle();
                    articleBundle.putString("userId", userId);
                    articleBundle.putString("postId", postId);
                    articleBundle.putInt("categoryId", categoryId);
                    articleBundle.putBoolean("isMyPage", isMyPage);
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
                    String id = String.valueOf(homeListAdapter.getItem(pos).getUserId());
                    String postId = String.valueOf(homeListAdapter.getItem(pos).getPostId());
                    int categoryId = homeListAdapter.getItem(pos).getCategoryId();
                    if (id.equals(userId)) {
                        Bundle args = new Bundle();
                        args.putString("userId", userId);
                        args.putString("postId", postId);
                        args.putInt("categoryId", categoryId);
                        MyBottomSheetDialog myBottomSheetDialog = MyBottomSheetDialog.getInstance();
                        myBottomSheetDialog.setArguments(args);
                        myBottomSheetDialog.show(getChildFragmentManager(), "mybottomsheet");
                    } else {
                        Bundle args = new Bundle();
                        args.putString("userId", userId);
                        args.putString("postId", postId);
                        BottomSheetDialog bottomSheetDialog = BottomSheetDialog.getInstance();
                        bottomSheetDialog.setArguments(args);
                        bottomSheetDialog.show(getChildFragmentManager(), "bottomsheet");
                    }
                }
            });
            main_new_list.setAdapter(homeListAdapter);
        }

        return mainNew;
    }

    public void getNew() {
        String resultText = "[NULL]";

        try {
            resultText = new GetTask("home/new/" + this.locationCode + "/" + userId).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObject = new JSONObject(resultText);
            String data = jsonObject.getString("data");
            JSONArray jsonArray = new JSONArray(data);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject subJsonObject = jsonArray.getJSONObject(i);
                int categoryId = subJsonObject.getInt("category_id");
                int postId = subJsonObject.getInt("post_id");
                int userId = subJsonObject.getInt("author_id");
                String img = subJsonObject.getString("url");
                String title = subJsonObject.getString("title");
                String buyDate = subJsonObject.getString("buy_date");
                String member = subJsonObject.getString("members");
                String perPrice = subJsonObject.getString("per_price");
                String writtenBy = subJsonObject.getString("witten_by");
                int isAuth = subJsonObject.getInt("isAuth");
                String content = subJsonObject.getString("contents");

                homeListItems.add(new HomeListItem(img, title, buyDate, member, perPrice, writtenBy, isAuth, postId, userId, categoryId, content));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}