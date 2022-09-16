package com.E2I3.chaebunchaebun;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommunityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommunityFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String userId;
    private int postId;

    private RecyclerView communityList;
    private TextView communityNoList;
    private ArrayList<CommunityListItem> communityListItems;
    private CommunityListAdapter communityListAdapter;
    private LinearLayoutManager cLayoutManager;
    SwipeRefreshLayout swipeRefreshLayout;
    ImageView communityTooltip;
    LinearLayout communityContent, communityHelp;

    boolean isBottom = true;
    int locationCode = 0;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CommunityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CommunityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CommunityFragment newInstance(String param1, String param2) {
        CommunityFragment fragment = new CommunityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void getUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        communityListItems = new ArrayList<CommunityListItem>();
        getCommunityList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View communityView = inflater.inflate(R.layout.fragment_community, container, false);

        swipeRefreshLayout = communityView.findViewById(R.id.community_refresh);
        communityList = communityView.findViewById(R.id.community_list);
        communityNoList = communityView.findViewById(R.id.community_nolist);
        communityTooltip = communityView.findViewById(R.id.community_tooltip);
        communityContent = communityView.findViewById(R.id.community_all);
        communityHelp = communityView.findViewById(R.id.community_list_help);
        communityHelp.setVisibility(View.GONE);

        swipeRefreshLayout.setOnRefreshListener(this);

        if(communityListItems.isEmpty()) {
            communityNoList.setVisibility(View.VISIBLE);
            communityList.setVisibility(View.GONE);
        } else {
            communityNoList.setVisibility(View.GONE);
            communityList.setVisibility(View.VISIBLE);
            cLayoutManager = new LinearLayoutManager(getContext());
            communityList.setLayoutManager(cLayoutManager);
            communityListAdapter = new CommunityListAdapter(communityListItems);
            communityList.setAdapter(communityListAdapter);

            communityListAdapter.setOnItemClickListener(new CommunityListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int pos) {
                    String postId = String.valueOf(communityListAdapter.getItem(pos).getPostId());
                    Intent intent = new Intent(getActivity(), CommunityArticleActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("postId", postId);
                    getActivity().startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.anim_slide_in_right,R.anim.anim_slide_out_left);
                }
            });

            communityListAdapter.setLikeClickListener(new CommunityListAdapter.OnLikeClickListener() {
                @Override
                public void OnLikeClick(View view, int pos) {
                    int postId = communityListAdapter.getItem(pos).getPostId();
                    PostTask myWishTask = new PostTask();
                    try {
                        String response = myWishTask.execute("community/wishlist/" + postId + " /" + userId, String.valueOf(postId), userId).get();
                        JSONObject jsonObject = new JSONObject(response);
                        int responseCode = jsonObject.getInt("code");
                        String data = jsonObject.getString("data");
                        if(responseCode == 200){
                            JSONArray jsonArray = new JSONArray(data);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject subJsonObject = jsonArray.getJSONObject(i);
                                int likeCount = subJsonObject.getInt("like_cnts");
                                int status = subJsonObject.getInt("status");
                                communityListAdapter.getItem(pos).setLikeCount(likeCount);
                                communityListAdapter.getItem(pos).setIsLike(status);
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

            communityTooltip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    communityHelp.setVisibility(View.VISIBLE);
                }
            });

            communityContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    communityHelp.setVisibility(View.GONE);
                }
            });

            communityHelp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    communityHelp.setVisibility(View.GONE);
                }
            });
        }

        return communityView;
    }

    @Override
    public void onRefresh() {
        FragmentTransaction communityTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        CommunityFragment communityFragment = new CommunityFragment();
        communityFragment.getUserId(userId);
        communityTransaction.replace(R.id.bottom_frame, communityFragment);
        communityTransaction.commit();

        swipeRefreshLayout.setRefreshing(false);
    }

    public void getCommunityList() {
        communityListItems.clear();

        String resultText = "[NULL]";

        try {
            resultText = new GetTask("community/" + userId).execute().get();
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

                int writerId = subJsonObject.getInt("userId");
                postId = subJsonObject.getInt("postId");
                String profile = subJsonObject.getString("profile");
                String nickname = subJsonObject.getString("name");
                String createAt = subJsonObject.getString("createdAt");
                String address = subJsonObject.getString("address");
                String content = subJsonObject.getString("content");

                String img1 = subJsonObject.getString("img1");
                String img2 = subJsonObject.getString("img2");
                String img3 = subJsonObject.getString("img3");
                String img4 = subJsonObject.getString("img4");
                String img5 = subJsonObject.getString("img5");

                int likeCount = subJsonObject.getInt("likeCount");
                int commCount = subJsonObject.getInt("commCount");
                int isLike = subJsonObject.getInt("isLike");
                boolean isSame = userId.equals(String.valueOf(writerId));

                communityListItems.add(new CommunityListItem(profile, nickname, createAt, content,
                        img1, img2, img3, img4, img5,
                        address, likeCount, postId, commCount, isLike, isSame));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}