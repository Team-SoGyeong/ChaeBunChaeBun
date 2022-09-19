package com.E2I3.chaebunchaebun;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommunityArticleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommunityArticleFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView toastText;
    private Toast toast;

    private RecyclerView cRecyclerView;
    private ArrayList<CommentRecyclerItem> commentRecyclerItems;
    private CommentRecyclerAdapter commentRecyclerAdapter;
    private LinearLayoutManager cLayoutManager;
    SwipeRefreshLayout swipeRefreshLayout;

    View communityArticleView;
    ImageView communityArticleBack, communityArticleProfile, communityArticleModalbtn, communityArticleLikebtn,
            communityArticleImg1, communityArticleImg2, communityArticleImg3, communityArticleImg4, communityArticleImg5;
    TextView communityArticleNickname, communityArticleWritingDate, communityArticleContent, communityArticleLocation,
            communityArticleLikeCount, communityArticleCommentCount;
    EditText communityArticleComment;
    ImageButton communityArticleCommentBtn;
    HorizontalScrollView communityArticleScrollView;

    String postId = "";
    String userId = "";
    String nickname, content, profile, location, writingDate, img1, img2, img3, img4, img5;
    int isLike, writerId, likeCount, commCount;
    boolean isSame;

    public CommunityArticleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CommunityArticleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CommunityArticleFragment newInstance(String param1, String param2) {
        CommunityArticleFragment fragment = new CommunityArticleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        userId = getArguments().getString("userId");
        postId = getArguments().getString("postId");

        getCommunityArticle();
        getCommunityCommentList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        communityArticleView = inflater.inflate(R.layout.fragment_community_article, container, false);

        View customToast = inflater.inflate(R.layout.custom_report_toast, (ViewGroup) communityArticleView.findViewById(R.id.custom_toast_layout));

        communityArticleBack = (ImageView) communityArticleView.findViewById(R.id.community_article_back);
        communityArticleProfile = (ImageView) communityArticleView.findViewById(R.id.community_article_proile_img);
        communityArticleModalbtn = (ImageView) communityArticleView.findViewById(R.id.community_article_modalbtn);

        communityArticleNickname = (TextView) communityArticleView.findViewById(R.id.community_article_nickname);
        communityArticleWritingDate = (TextView) communityArticleView.findViewById(R.id.community_article_writing_date);
        communityArticleLocation = (TextView) communityArticleView.findViewById(R.id.community_article_location_txt);
        communityArticleContent = (TextView) communityArticleView.findViewById(R.id.community_article_content);

        communityArticleLikebtn = (ImageView) communityArticleView.findViewById(R.id.community_article_like_btn);
        communityArticleLikeCount = (TextView) communityArticleView.findViewById(R.id.community_article_like_cnt);
        communityArticleCommentCount = (TextView) communityArticleView.findViewById(R.id.community_article_comment_cnt);

        communityArticleScrollView = (HorizontalScrollView) communityArticleView.findViewById(R.id.community_article_scrollview);
        communityArticleImg1 = (ImageView) communityArticleView.findViewById(R.id.community_article_img1);
        communityArticleImg2 = (ImageView) communityArticleView.findViewById(R.id.community_article_img2);
        communityArticleImg3 = (ImageView) communityArticleView.findViewById(R.id.community_article_img3);
        communityArticleImg4 = (ImageView) communityArticleView.findViewById(R.id.community_article_img4);
        communityArticleImg5 = (ImageView) communityArticleView.findViewById(R.id.community_article_img5);

        communityArticleComment = (EditText) communityArticleView.findViewById(R.id.community_article_comment);
        communityArticleCommentBtn = (ImageButton) communityArticleView.findViewById(R.id.community_article_comment_btn);

        cRecyclerView = (RecyclerView) communityArticleView.findViewById(R.id.article_comment_list);

        swipeRefreshLayout = communityArticleView.findViewById(R.id.community_article_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        toastText = (TextView) customToast.findViewById(R.id.custom_toast_text);
        toast = new Toast(getContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(customToast);

        Glide.with(getContext()).load(this.profile).into(communityArticleProfile);
        communityArticleNickname.setText(this.nickname);
        communityArticleContent.setText(this.content);
        communityArticleWritingDate.setText(this.writingDate);
        communityArticleLocation.setText(this.location);

        if (img1.equals(null) || img1.equals("null") || img1 == null || img1.isEmpty()) {
            communityArticleScrollView.setVisibility(View.GONE);
        } else if (img2.equals(null) || img2.equals("null") || img2 == null || img2.isEmpty()) {
            Glide.with(getContext()).load(this.img1).into(communityArticleImg1);
            communityArticleImg2.setVisibility(View.GONE);
            communityArticleImg3.setVisibility(View.GONE);
            communityArticleImg4.setVisibility(View.GONE);
            communityArticleImg5.setVisibility(View.GONE);
        } else if (img3.equals(null) || img3.equals("null") || img3 == null || img3.isEmpty()) {
            Glide.with(getContext()).load(this.img1).into(communityArticleImg1);
            Glide.with(getContext()).load(this.img2).into(communityArticleImg2);
            communityArticleImg3.setVisibility(View.GONE);
            communityArticleImg4.setVisibility(View.GONE);
            communityArticleImg5.setVisibility(View.GONE);
        } else if (img4.equals(null) || img4.equals("null") || img4 == null || img4.isEmpty()) {
            Glide.with(getContext()).load(this.img1).into(communityArticleImg1);
            Glide.with(getContext()).load(this.img2).into(communityArticleImg2);
            Glide.with(getContext()).load(this.img3).into(communityArticleImg3);
            communityArticleImg4.setVisibility(View.GONE);
            communityArticleImg5.setVisibility(View.GONE);
        } else if (img5.equals(null) || img5.equals("null") || img5 == null || img5.isEmpty()) {
            Glide.with(getContext()).load(this.img1).into(communityArticleImg1);
            Glide.with(getContext()).load(this.img2).into(communityArticleImg2);
            Glide.with(getContext()).load(this.img3).into(communityArticleImg3);
            Glide.with(getContext()).load(this.img4).into(communityArticleImg4);
            communityArticleImg5.setVisibility(View.GONE);
        } else {
            Glide.with(getContext()).load(this.img1).into(communityArticleImg1);
            Glide.with(getContext()).load(this.img2).into(communityArticleImg2);
            Glide.with(getContext()).load(this.img3).into(communityArticleImg3);
            Glide.with(getContext()).load(this.img4).into(communityArticleImg4);
            Glide.with(getContext()).load(this.img5).into(communityArticleImg5);
        }

        if(isLike == 0) {
            communityArticleLikebtn.setImageResource(R.drawable.communitylist_btn_like);
            communityArticleLikeCount.setTextColor(getResources().getColor(R.color.dark_gray));
        } else {
            communityArticleLikebtn.setImageResource(R.drawable.communitylist_btn_like_red);
            communityArticleLikeCount.setTextColor(getResources().getColor(R.color.heart));
        }

        communityArticleLikebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSame == true){
                    toastText.setText("본인의 글은 좋아요 할 수 없어요!");
                    toast.show();
                } else {
                    PostTask myWishTask = new PostTask();
                    try {
                        String response = myWishTask.execute("community/wishlist/" + postId + " /" + userId, String.valueOf(postId), userId).get();
                        JSONObject jsonObject = new JSONObject(response);
                        int responseCode = jsonObject.getInt("code");
                        if(responseCode == 200) {
                            String data = jsonObject.getString("data");
                            JSONArray jsonArray = new JSONArray(data);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject subJsonObject = jsonArray.getJSONObject(i);
                                int likeCnts = subJsonObject.getInt("like_cnts");
                                int status = subJsonObject.getInt("status");
                                communityArticleLikeCount.setText(String.valueOf(likeCnts));
                                if (status == 0) {
                                    communityArticleLikebtn.setImageResource(R.drawable.communitylist_btn_like);
                                    communityArticleLikeCount.setTextColor(getResources().getColor(R.color.dark));
                                    toastText.setText("좋아요를 취소했어요!");
                                    toast.show();
                                } else {
                                    communityArticleLikebtn.setImageResource(R.drawable.communitylist_btn_like_red);
                                    communityArticleLikeCount.setTextColor(getResources().getColor(R.color.heart));
                                    toastText.setText("좋아요를 늘렀어요!");
                                    toast.show();
                                }
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
            }
        });

        communityArticleModalbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSame == true) {
                    Bundle args = new Bundle();
                    args.putString("userId", userId);
                    args.putString("postId", postId);
                    MyCommunityBottomSheetDialog myCommunityBottomSheetDialog = MyCommunityBottomSheetDialog.getInstance();
                    myCommunityBottomSheetDialog.setArguments(args);
                    myCommunityBottomSheetDialog.show(getChildFragmentManager(), "mybottomsheet");
                } else {
                    Bundle args = new Bundle();
                    args.putString("userId", userId);
                    args.putString("postId", postId);
                    CommunityBottomSheetDialog communityBottomSheetDialog = CommunityBottomSheetDialog.getInstance();
                    communityBottomSheetDialog.setArguments(args);
                    communityBottomSheetDialog.show(getChildFragmentManager(), "bottomsheet");
                }
            }
        });

        communityArticleLikeCount.setText(String.valueOf(this.likeCount));
        communityArticleCommentCount.setText(String.valueOf(this.commCount));

        cLayoutManager = new LinearLayoutManager(getContext());
        cRecyclerView.setLayoutManager(cLayoutManager);
        commentRecyclerAdapter = new CommentRecyclerAdapter(commentRecyclerItems);
        commentRecyclerAdapter.notifyDataSetChanged();
        commentRecyclerAdapter.setModalClickListener(new CommentRecyclerAdapter.OnModalClickListener() {
            @Override
            public void OnModlaClick(View view, int pos) {
                String id = String.valueOf(commentRecyclerAdapter.getItem(pos).getUserId());
                String commentId = String.valueOf(commentRecyclerAdapter.getItem(pos).getCommentId());
                if (id.equals(userId)) {
                    Bundle args = new Bundle();
                    args.putString("userId", userId);
                    args.putString("commentId", commentId);
                    args.putString("postId", postId);
                    MyCommunityCmtBottomSheetDialog myCommunityCmtBottomSheetDialog = MyCommunityCmtBottomSheetDialog.getInstance();
                    myCommunityCmtBottomSheetDialog.setArguments(args);
                    myCommunityCmtBottomSheetDialog.show(getChildFragmentManager(), "mybottomsheet");
                } else {
                    Bundle reportArgs = new Bundle();
                    reportArgs.putString("userId", userId);
                    reportArgs.putString("commentId", commentId);
                    reportArgs.putString("postId", postId);
                    CommunityCmtBottomSheetDialog communityCmtBottomSheetDialog = CommunityCmtBottomSheetDialog.getInstance();
                    communityCmtBottomSheetDialog.setArguments(reportArgs);
                    communityCmtBottomSheetDialog.show(getChildFragmentManager(), "bottomsheet");
                }
            }
        });

        communityArticleCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(communityArticleComment.getText().toString().equals("")) {
                    toastText.setText("내용이 작성되지 않았어요!");
                    toast.show();
                } else {
                    System.out.println("댓글 내용" + communityArticleComment.getText().toString());
                    PostTask postTask = new PostTask();
                    JSONObject jsonCommentTransfer = new JSONObject();

                    try {
                        jsonCommentTransfer.put("content", communityArticleComment.getText().toString());
                        jsonCommentTransfer.put("postId", Integer.parseInt(postId));
                        jsonCommentTransfer.put("userId", Integer.parseInt(userId));
                        String jsonString = jsonCommentTransfer.toString();
                        postTask.execute("community/comment", jsonString);

                        Bundle articleBundle = new Bundle();
                        articleBundle.putString("userId", userId);
                        articleBundle.putString("postId", postId);

                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.popBackStack();
                        FragmentTransaction articleTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        CommunityArticleFragment communityArticleFragment = new CommunityArticleFragment();
                        communityArticleFragment.setArguments(articleBundle);
                        articleTransaction.replace(R.id.community_article_frame, communityArticleFragment);
                        articleTransaction.addToBackStack(null);
                        articleTransaction.commit();
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        cRecyclerView.setAdapter(commentRecyclerAdapter);

        return communityArticleView;
    }

    public void getCommunityCommentList() {
        commentRecyclerItems = new ArrayList<CommentRecyclerItem>();
        commentRecyclerItems.clear();

        String commentResult = "[NULL]";

        try {
            commentResult = new GetTask("community/comment/" + this.postId).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObject = new JSONObject(commentResult);
            String data = jsonObject.getString("data");
            JSONArray jsonArray = new JSONArray(data);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject subJsonObject = jsonArray.getJSONObject(i);

                String profile = subJsonObject.getString("profile");
                String commentNickname = subJsonObject.getString("name");
                String comments = subJsonObject.getString("content");
                String commentWrittenBy = subJsonObject.getString("createdAt");
                int commentUserId = subJsonObject.getInt("postId");
                int commentId = subJsonObject.getInt("commId");

                commentRecyclerItems.add(new CommentRecyclerItem(profile, commentNickname, comments, commentWrittenBy, commentUserId, commentId));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getCommunityArticle() {
        String resultText = "[NULL]";

        try {
            resultText = new GetTask("community/detail/" + this.postId + "/" + this.userId).execute().get();
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

                this.profile = subJsonObject.getString("profile");
                this.writerId = subJsonObject.getInt("userId");
                this.nickname = subJsonObject.getString("name");
                this.content = subJsonObject.getString("content");
                this.location = subJsonObject.getString("address");
                this.writingDate = subJsonObject.getString("createdAt");

                this.img1 = subJsonObject.getString("img1");
                this.img2 = subJsonObject.getString("img2");
                this.img3 = subJsonObject.getString("img3");
                this.img4 = subJsonObject.getString("img4");
                this.img5 = subJsonObject.getString("img5");

                this.isLike = subJsonObject.getInt("isLike");
                this.likeCount = subJsonObject.getInt("likeCount");
                this.commCount = subJsonObject.getInt("commCount");
                this.isSame = userId.equals(String.valueOf(this.writerId));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        Bundle categoryBundle = new Bundle();
        categoryBundle.putString("postId", postId);
        categoryBundle.putString("userId", userId);
        FragmentTransaction articleTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        CommunityArticleFragment communityArticleFragment = new CommunityArticleFragment();
        communityArticleFragment.setArguments(categoryBundle);
        articleTransaction.replace(R.id.community_article_frame, communityArticleFragment);
        articleTransaction.commit();

        swipeRefreshLayout.setRefreshing(false);
    }
}