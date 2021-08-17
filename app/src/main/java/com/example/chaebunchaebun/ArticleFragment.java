package com.example.chaebunchaebun;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArticleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticleFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView aRecyclerView;
    private ArrayList<ArticleRecyclerData> articleItemList;
    private ArticleRecyclerAdapter articleRecyclerAdapter;
    private LinearLayoutManager aLayoutManager;

    private RecyclerView cRecyclerView;
    private ArrayList<CommentRecyclerItem> commentRecyclerItems;
    private CommentRecyclerAdapter commentRecyclerAdapter;
    private LinearLayoutManager cLayoutManager;

    View articleView;
    ImageView articleBack, articleRecipt, articleComplete, articleProfile;
    TextView categoryName, articleWishcnt, articleTitle, articleNickname, articleWritingDate,
            articleBuyingDate, articleTotal, articlePeople, articlePrice, articleContent;
    EditText articleComment;
    LinearLayout articleReciptHelp;
    int recyclerPosition = -1;
    String postId = "";
    String userId = "1";
    String categoryNameString, title, nickname, content, buyDate, members, perPrice, writtenBy, profile;
    int isAuth, wishcount;

    public ArticleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ArticleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ArticleFragment newInstance(String param1, String param2) {
        ArticleFragment fragment = new ArticleFragment();
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

            this.postId = getArguments().getString("postId");
            getPostList();
            getCommentList();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        articleView = inflater.inflate(R.layout.fragment_article, container, false);

        categoryName = (TextView) articleView.findViewById(R.id.article_category_name);
        articleWishcnt = (TextView) articleView.findViewById(R.id.article_wishcount);
        articleTitle = (TextView) articleView.findViewById(R.id.article_title);
        articleNickname = (TextView) articleView.findViewById(R.id.article_nickname);
        articleWritingDate = (TextView) articleView.findViewById(R.id.article_writing_date);
        articleBuyingDate = (TextView) articleView.findViewById(R.id.article_buying_date);
        articleTotal = (TextView) articleView.findViewById(R.id.article_total);
        articlePeople = (TextView) articleView.findViewById(R.id.article_people);
        articlePrice = (TextView) articleView.findViewById(R.id.article_price);
        articleContent = (TextView) articleView.findViewById(R.id.article_content);
        articleRecipt = (ImageView) articleView.findViewById(R.id.article_receipt);
        articleProfile = (ImageView) articleView.findViewById(R.id.article_proile_img);

        aRecyclerView = (RecyclerView) articleView.findViewById(R.id.article_recycler_img);
        cRecyclerView = (RecyclerView) articleView.findViewById(R.id.article_comment_list);

        articleComment = (EditText) articleView.findViewById(R.id.article_comment);
        articleBack = (ImageView) articleView.findViewById(R.id.article_back);
        articleComplete = (ImageView) articleView.findViewById(R.id.ic_complete);
        articleReciptHelp = (LinearLayout) articleView.findViewById(R.id.article_receipt_help);
        articleReciptHelp.setVisibility(View.GONE);

        aLayoutManager = new LinearLayoutManager(getContext());
        aLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        aRecyclerView.setLayoutManager(aLayoutManager);
        articleRecyclerAdapter = new ArticleRecyclerAdapter(articleItemList);
        aRecyclerView.setAdapter(articleRecyclerAdapter);

        categoryName.setText(this.categoryNameString);
        articleTitle.setText(this.title);
        articleNickname.setText(this.nickname);
        articleContent.setText(this.content);
        articleBuyingDate.setText(this.buyDate);
        articlePeople.setText(this.members);
        articlePrice.setText(this.perPrice);
        articleWritingDate.setText(this.writtenBy);
        if(isAuth == 0){
            articleRecipt.setVisibility(View.GONE);
        } else {
            articleRecipt.setVisibility(View.VISIBLE);
        }
        articleWishcnt.setText(String.valueOf(this.wishcount));
        Glide.with(getContext()).load(this.profile).into(articleProfile);

        cLayoutManager = new LinearLayoutManager(getContext());
        cRecyclerView.setLayoutManager(cLayoutManager);
        cRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        commentRecyclerAdapter = new CommentRecyclerAdapter(commentRecyclerItems);
        commentRecyclerAdapter.notifyDataSetChanged();
        cRecyclerView.setAdapter(commentRecyclerAdapter);

        articleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(ArticleFragment.this).commit();
                fragmentManager.popBackStack();
            }
        });

        articleRecipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                articleReciptHelp.setVisibility(View.VISIBLE);
            }
        });

        articleReciptHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                articleReciptHelp.setVisibility(View.GONE);
            }
        });

        articleComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompleteDialogFragment e = CompleteDialogFragment.getInstance();
                e.show(getChildFragmentManager(), CompleteDialogFragment.TAG_EVENT_DIALOG);
            }
        });

        articleComment.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if((keyCode == KeyEvent.KEYCODE_ENTER)) {
                    PostTask postTask = new PostTask();
                    JSONObject jsonCommentTransfer = new JSONObject();

                    try {
                        jsonCommentTransfer.put("cmts", articleComment.getText().toString());
                        jsonCommentTransfer.put("post_id", Integer.parseInt(postId));
                        jsonCommentTransfer.put("user_id", Integer.parseInt(userId));
                        String jsonString = jsonCommentTransfer.toString();
                        postTask.execute("posts/comment", jsonString);
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return true;
                }
                return false;
            }
        });

        return articleView;
    }

    public void getCommentList() {
        commentRecyclerItems = new ArrayList<CommentRecyclerItem>();
        commentRecyclerItems.clear();

        String commentResult = "[NULL]";

        try {
            commentResult = new GetTask("posts/comment/" + this.postId).execute().get();
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

                String commentList = subJsonObject.getString("cmts");
                JSONArray jsonLastListArray = new JSONArray(commentList);
                for(int j = 0; j < jsonLastListArray.length(); j++){
                    JSONObject subJsonObject2 = jsonLastListArray.getJSONObject(j);

                    //String profile = subJsonObject2.getString("profile");
                    String commentNickname = subJsonObject2.getString("nickname");
                    String comments = subJsonObject2.getString("comments");
                    String commentWrittenBy = subJsonObject2.getString("witten_by");
                    //this.profile = subJsonObject2.getString("profile");

                    commentRecyclerItems.add(new CommentRecyclerItem(commentNickname, comments, commentWrittenBy));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getPostList() {
        articleItemList = new ArrayList<ArticleRecyclerData>();
        articleItemList.clear();

        String resultText = "[NULL]";

        try {
            resultText = new GetTask("posts/" + this.postId + "/" + this.userId).execute().get();
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

                this.categoryNameString = subJsonObject.getString("category_name");

                String posts = subJsonObject.getString("posts");
                JSONArray jsonLastListArray = new JSONArray(posts);
                for(int j = 0; j < jsonLastListArray.length(); j++){
                    JSONObject subJsonObject2 = jsonLastListArray.getJSONObject(j);

                    //String profile = subJsonObject2.getString("profile");
                    this.title = subJsonObject2.getString("title");
                    this.nickname = subJsonObject2.getString("nickname");
                    this.content = subJsonObject2.getString("contents");
                    this.buyDate = subJsonObject2.getString("buy_date");
                    this.members = subJsonObject2.getString("headcounts");
                    this.perPrice = subJsonObject2.getString("per_price");
                    this.writtenBy = subJsonObject2.getString("witten_by");
                    this.isAuth = subJsonObject2.getInt("isAuth");
                    this.wishcount = subJsonObject2.getInt("wish_cnts");
                    this.profile = subJsonObject2.getString("profile");

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

                    articleItemList.add(new ArticleRecyclerData(img1));
                    articleItemList.add(new ArticleRecyclerData(img2));
                    articleItemList.add(new ArticleRecyclerData(img3));
                    articleItemList.add(new ArticleRecyclerData(img4));
                    articleItemList.add(new ArticleRecyclerData(img5));
                    //this.postId = subJsonObject2.getString("post_id");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}