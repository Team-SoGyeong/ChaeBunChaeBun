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
 * Use the {@link MainSoonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainSoonFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mainSoonList;
    private ArrayList<HomeListItem> homeListItems;
    private HomeListAdapter homeListAdapter;
    private LinearLayoutManager mLayoutManager;

    View mainSoonView;
    TextView mainSoonText;
    String userId = null;
    String locationCode = null;
    int authorId = 0;
    boolean isBottom = true;

    public MainSoonFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainSoonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainSoonFragment newInstance(String param1, String param2) {
        MainSoonFragment fragment = new MainSoonFragment();
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

        getSoon();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainSoonView = inflater.inflate(R.layout.fragment_main_soon, container, false);
        mainSoonText = (TextView) mainSoonView.findViewById(R.id.main_soon_text);
        mainSoonList = (RecyclerView) mainSoonView.findViewById(R.id.main_soon_list);

        if(homeListItems.isEmpty()){
            mainSoonText.setVisibility(View.VISIBLE);
            mainSoonList.setVisibility(View.GONE);
        } else {
            mainSoonText.setVisibility(View.GONE);
            mainSoonList.setVisibility(View.VISIBLE);

            mLayoutManager = new LinearLayoutManager(getContext());
            MainRecyclerDecoration mainRecyclerDecoration = new MainRecyclerDecoration(40);
            mainSoonList.addItemDecoration(mainRecyclerDecoration);
            mainSoonList.setLayoutManager(new LinearLayoutManager(getContext()){
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
                    articleBundle.putBoolean("isBottom", isBottom);
                    FragmentTransaction articleTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    articleTransaction.setCustomAnimations(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left, R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
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
                    String id = String.valueOf(authorId);
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
            mainSoonList.setAdapter(homeListAdapter);
        }

        return mainSoonView;
    }

    public void getSoon() {
        String resultText = "[NULL]";

        try {
            resultText = new GetTask("home/deadline/" + this.locationCode + "/" + userId).execute().get();
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
                int userId = Integer.parseInt(this.userId);
                authorId = subJsonObject.getInt("author_id");
                String img = subJsonObject.getString("url");
                String title = subJsonObject.getString("title");
                String buyDate = subJsonObject.getString("buy_date");
                String members = subJsonObject.getString("post_addr");
                String perPrice = subJsonObject.getString("per_price");
                String writtenBy = subJsonObject.getString("witten_by");
                int isAuth = subJsonObject.getInt("isAuth");
                String content = subJsonObject.getString("contents");

                homeListItems.add(new HomeListItem(img, title, buyDate, members, perPrice, writtenBy, isAuth, postId, userId, categoryId, content));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}