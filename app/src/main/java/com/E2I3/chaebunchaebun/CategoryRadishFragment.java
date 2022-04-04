package com.E2I3.chaebunchaebun;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryRadishFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryRadishFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView categoryRadishList;
    private ArrayList<CategoryListItem> categoryListItems;
    private CategoryListAdapter categoryListAdapter;
    private LinearLayoutManager cLayoutManager;

    TextView categoryNoList;
    String id = null;
    String category = "8";
    boolean isMyPage = false;
    int locationCode = 0;

    public CategoryRadishFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryRadishFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryRadishFragment newInstance(String param1, String param2) {
        CategoryRadishFragment fragment = new CategoryRadishFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void getUserId(String userId){
        this.id = userId;
    }

    public void getLocationCode(int locationCode) {
        this.locationCode = locationCode;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        categoryListItems = new ArrayList<CategoryListItem>();
        getCategory();
    }

    ImageButton writing;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View categoryRadish = inflater.inflate(R.layout.fragment_category_radish, container, false);

        categoryRadishList = categoryRadish.findViewById(R.id.radish_list);
        categoryNoList = categoryRadish.findViewById(R.id.radish_nolist);

        if(categoryListItems.isEmpty()) {
            categoryNoList.setVisibility(View.VISIBLE);
        } else {
            categoryNoList.setVisibility(View.GONE);
            cLayoutManager = new LinearLayoutManager(getContext());
            categoryRadishList.setLayoutManager(cLayoutManager);
            categoryListAdapter = new CategoryListAdapter(categoryListItems);
            categoryRadishList.setAdapter(categoryListAdapter);

            categoryListAdapter.setOnItemClickListener(new CategoryListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int pos) {
                    String postId = String.valueOf(categoryListAdapter.getItem(pos).getPostId());
                    int categoryId = categoryListAdapter.getItem(pos).getCategoryId();
                    Bundle articleBundle = new Bundle();
                    articleBundle.putString("userId", id);
                    articleBundle.putString("postId", postId);
                    articleBundle.putInt("categoryId", categoryId);
                    articleBundle.putBoolean("isMyPAge", isMyPage);
                    FragmentTransaction articleTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    ArticleFragment articleFragment = new ArticleFragment();
                    articleFragment.setArguments(articleBundle);
                    articleTransaction.replace(R.id.bottom_frame, articleFragment);
                    articleTransaction.addToBackStack(null);
                    articleTransaction.commit();
                }
            });

            categoryListAdapter.setModalClickListener(new CategoryListAdapter.OnModalClickListener() {
                @Override
                public void OnModlaClick(View view, int pos) {
                    String userId = String.valueOf(categoryListAdapter.getItem(pos).getUserId());
                    String postId = String.valueOf(categoryListAdapter.getItem(pos).getPostId());
                    int categoryId = categoryListAdapter.getItem(pos).getCategoryId();
                    if (userId.equals(id)) {
                        Bundle args = new Bundle();
                        args.putString("userId", id);
                        args.putString("postId", postId);
                        args.putInt("categoryId", categoryId);
                        MyBottomSheetDialog myBottomSheetDialog = MyBottomSheetDialog.getInstance();
                        myBottomSheetDialog.setArguments(args);
                        myBottomSheetDialog.show(getChildFragmentManager(), "mybottomsheet");
                    } else {
                        Bundle args = new Bundle();
                        args.putString("userId", id);
                        args.putString("postId", postId);
                        BottomSheetDialog bottomSheetDialog = BottomSheetDialog.getInstance();
                        bottomSheetDialog.setArguments(args);
                        bottomSheetDialog.show(getChildFragmentManager(), "bottomsheet");
                    }
                }
            });

            categoryListAdapter.setLikeClickListener(new CategoryListAdapter.OnLikeClickListener() {
                @Override
                public void OnLikeClick(View view, int pos) {
                    int postId = categoryListAdapter.getItem(pos).getPostId();
                    int userId = categoryListAdapter.getItem(pos).getUserId();
                    int isWish = categoryListAdapter.getItem(pos).getIsWish();
                    PostTask myWishTask = new PostTask();
                    try {
                        String response = myWishTask.execute("common/wishlist/" + postId + " /" + id, String.valueOf(postId), id).get();
                        JSONObject jsonObject = new JSONObject(response);
                        int responseCode = jsonObject.getInt("code");
                        String data = jsonObject.getString("data");
                        if(responseCode == 200){
                            JSONArray jsonArray = new JSONArray(data);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject subJsonObject = jsonArray.getJSONObject(i);
                                int wishcount = subJsonObject.getInt("wish_cnts");
                                int status = subJsonObject.getInt("status");
                                categoryListAdapter.getItem(pos).setLikeCount(String.valueOf(wishcount));
                                categoryListAdapter.getItem(pos).setIsWish(status);
                            }
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        writing = (ImageButton) categoryRadish.findViewById(R.id.radish_newbtn);
        writing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("userId", id);
                args.putInt("locationCode", locationCode);
                WarningDialogFragment e = WarningDialogFragment.getInstance();
                e.setArguments(args);
                e.show(getChildFragmentManager(), WarningDialogFragment.TAG_EVENT_DIALOG);
            }
        });
        // Inflate the layout for this fragment
        return categoryRadish;
    }

    public void getCategory() {
        categoryListItems.clear();

        String resultText = "[NULL]";

        try {
            resultText = new GetTask("posts/category/" + category + "/" + id).execute().get();
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
                int categoryId = subJsonObject.getInt("category_id1");
                String post = subJsonObject.getString("posts");
                JSONArray jsonPostArray = new JSONArray(post);
                for(int j = 0; j < jsonPostArray.length(); j++){
                    JSONObject subJsonObject2 = jsonPostArray.getJSONObject(j);
                    int postId = subJsonObject2.getInt("post_id");
                    int userId = subJsonObject2.getInt("user_id");
                    String profile = subJsonObject2.getString("profile");
                    String title = subJsonObject2.getString("title");
                    String nickname = subJsonObject2.getString("nickname");
                    String writtenBy = subJsonObject2.getString("witten_by");
                    String content = subJsonObject2.getString("contents");

                    String img1 = null;
                    String img2 = null;
                    String img3 = null;
                    String img4 = null;
                    String img5 = null;

                    String imgs = subJsonObject2.getString("imgs");
                    JSONObject subJsonObject3 = new JSONObject(imgs);
                    img1 = subJsonObject3.getString("img1");
                    img2 = subJsonObject3.getString("img2");
                    img3 = subJsonObject3.getString("img3");
                    img4 = subJsonObject3.getString("img4");
                    img5 = subJsonObject3.getString("img5");

                    String buyDate = subJsonObject2.getString("buy_date");
                    String member = subJsonObject2.getString("total_amount");
                    String perPrice = subJsonObject2.getString("total_price");
                    int myWishInt = subJsonObject2.getInt("wish_cnts");
                    String myWish = String.valueOf(myWishInt);
                    int commentInt = subJsonObject2.getInt("comment_cnts");
                    String comment = String.valueOf(commentInt);
                    int isAuth = subJsonObject2.getInt("isAuth");
                    int isWish = subJsonObject2.getInt("isMyWish");
                    boolean isSame = id.equals(String.valueOf(userId));

                    categoryListItems.add(new CategoryListItem(profile, title, nickname, writtenBy, content,
                            img1, img2, img3, img4, img5, buyDate, member, perPrice, myWish, comment, isAuth,
                            isWish, postId, userId, isSame, categoryId));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}