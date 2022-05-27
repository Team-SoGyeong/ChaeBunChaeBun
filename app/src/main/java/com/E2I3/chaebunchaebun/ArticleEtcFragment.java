package com.E2I3.chaebunchaebun;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
 * Use the {@link ArticleEtcFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticleEtcFragment extends Fragment {

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

    private TextView toastText;
    private Toast toast;

    private RecyclerView cRecyclerView;
    private ArrayList<CommentRecyclerItem> commentRecyclerItems;
    private CommentRecyclerAdapter commentRecyclerAdapter;
    private LinearLayoutManager cLayoutManager;

    View articleEtcView;
    ImageView articleBack, articleRecipt, articleComplete, articleProfile, articleModalbtn, articleLikebtn;
    TextView categoryName, articleWishcnt, articleTitle, articleNickname, articleWritingDate,
            articleBuyingDate, articleTotal, articlePeople, articlePrice, articleContent, articleContact,
            articleAmountUnit, articleAmountPerUnit, articleInputPer;
    EditText articleComment, articleAmount, articleTotalPrice, articlePer;
    ImageButton articleCommentbtn, articleCalculateBtn;
    LinearLayout articleReciptHelp, articleAll;
    RelativeLayout articleCalculateContent;

    int recyclerPosition = -1;
    String postId = "";
    String userId = "";
    int categoryId = 0;
    boolean isMyPage = false;
    boolean calculateContent = false;
    String categoryNameString, title, nickname, content, buyDate, perPrice, writtenBy,
            profile, amount, totalPrice, contact, amountUnit, totalPriceNum;
    int isAuth, wishcount, userIdnum, status, isMyWish = 0, amountNum;
    long locationCode = 0;

    public ArticleEtcFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ArticleEtcFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ArticleEtcFragment newInstance(String param1, String param2) {
        ArticleEtcFragment fragment = new ArticleEtcFragment();
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

        this.postId = getArguments().getString("postId");
        this.userId = getArguments().getString("userId");
        this.categoryId = getArguments().getInt("categoryId");
        this.isMyPage = getArguments().getBoolean("isMyPage");

        System.out.println(isMyPage);

        getPostList();
        getCommentList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        articleEtcView = inflater.inflate(R.layout.fragment_article_etc, container, false);

        View customToast = inflater.inflate(R.layout.custom_report_toast, (ViewGroup) articleEtcView.findViewById(R.id.custom_toast_layout));

        articleAll = (LinearLayout) articleEtcView.findViewById(R.id.article_etc_all);
        categoryName = (TextView) articleEtcView.findViewById(R.id.article_etc_category_name);
        articleWishcnt = (TextView) articleEtcView.findViewById(R.id.article_etc_wishcount);
        articleLikebtn = (ImageView) articleEtcView.findViewById(R.id.article_etc_wishicon);
        articleTitle = (TextView) articleEtcView.findViewById(R.id.article_etc_title);
        articleNickname = (TextView) articleEtcView.findViewById(R.id.article_etc_nickname);
        articleWritingDate = (TextView) articleEtcView.findViewById(R.id.article_etc_writing_date);
        articleBuyingDate = (TextView) articleEtcView.findViewById(R.id.article_etc_buying_date);
        articleTotal = (TextView) articleEtcView.findViewById(R.id.article_etc_total);
        //articlePeople = (TextView) articleEtcView.findViewById(R.id.article_people);
        articlePrice = (TextView) articleEtcView.findViewById(R.id.article_etc_price);
        articleContent = (TextView) articleEtcView.findViewById(R.id.article_etc_content);
        articleContact = (TextView) articleEtcView.findViewById(R.id.article_etc_contact);
        articleRecipt = (ImageView) articleEtcView.findViewById(R.id.article_etc_receipt);
        articleProfile = (ImageView) articleEtcView.findViewById(R.id.article_etc_proile_img);

        articleCalculateBtn = (ImageButton) articleEtcView.findViewById(R.id.article_etc_calculate);
        articleCalculateContent= (RelativeLayout) articleEtcView.findViewById(R.id.article_calculate_content);
        articleAmount = (EditText) articleEtcView.findViewById(R.id.article_amount);
        articleTotalPrice = (EditText) articleEtcView.findViewById(R.id.article_getPrice);
        articlePer = (EditText) articleEtcView.findViewById(R.id.article_per);
        articleAmountUnit = (TextView) articleEtcView.findViewById(R.id.article_amount_unit);
        articleAmountPerUnit = (TextView) articleEtcView.findViewById(R.id.article_per_unit);
        articleInputPer = (TextView) articleEtcView.findViewById(R.id.article_input_per_price);

        aRecyclerView = (RecyclerView) articleEtcView.findViewById(R.id.article_etc_recycler_img);
        cRecyclerView = (RecyclerView) articleEtcView.findViewById(R.id.article_comment_list);

        articleComment = (EditText) articleEtcView.findViewById(R.id.article_etc_comment);
        articleCommentbtn = (ImageButton) articleEtcView.findViewById(R.id.article_etc_comment_btn);
        articleBack = (ImageView) articleEtcView.findViewById(R.id.article_etc_back);
        articleComplete = (ImageView) articleEtcView.findViewById(R.id.ic_etc_complete);
        articleReciptHelp = (LinearLayout) articleEtcView.findViewById(R.id.article_etc_receipt_help);
        articleReciptHelp.setVisibility(View.GONE);
        articleModalbtn = (ImageView) articleEtcView.findViewById(R.id.article_etc_modalbtn);

        toastText = (TextView) customToast.findViewById(R.id.custom_toast_text);
        toast = new Toast(getContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(customToast);

        aLayoutManager = new LinearLayoutManager(getContext());
        aLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        aRecyclerView.setLayoutManager(aLayoutManager);
        articleRecyclerAdapter = new ArticleRecyclerAdapter(articleItemList);
        aRecyclerView.setAdapter(articleRecyclerAdapter);

        articleTitle.setText(this.title);
        articleNickname.setText(this.nickname);
        articleContent.setText(this.content);
        articleContact.setText(this.contact);
        articleBuyingDate.setText(this.buyDate);
        articleTotal.setText(this.amount);
        //articlePeople.setText(this.members);
        articlePrice.setText(this.totalPrice);
        articleWritingDate.setText(this.writtenBy);

        if(categoryId < 11) {
            categoryName.setText(this.categoryNameString);
        } else {
            categoryName.setText("기타");
        }

        if(isAuth == 0){
            articleRecipt.setVisibility(View.GONE);
        } else {
            articleRecipt.setVisibility(View.VISIBLE);
        }

        if(status == 0) {
            articleComplete.setImageResource(R.drawable.article_btn_uncomplete);
        } else {
            articleComplete.setImageResource(R.drawable.article_btn_complete);
            articleComplete.setClickable(false);
            articleComment.setClickable(false);
            articleCommentbtn.setClickable(false);
            articleModalbtn.setClickable(false);
        }

        if(isMyWish == 0) {
            articleLikebtn.setImageResource(R.drawable.article_btn_favorite_gray);
        } else {
            articleLikebtn.setImageResource(R.drawable.categorylist_btn_favorite_filled);
        }

        articleWishcnt.setText(String.valueOf(this.wishcount));
        Glide.with(getContext()).load(this.profile).into(articleProfile);

        String amountStr = String.valueOf(this.amountNum);
        articleAmount.setText(amountStr);
        articleAmountUnit.setText(this.amountUnit);
        articleAmountPerUnit.setText(this.amountUnit);
        articleTotalPrice.setText(this.totalPriceNum);
        articlePer.setText("0");
        articleAmount.setEnabled(false);
        articleTotalPrice.setEnabled(false);
        articleCalculateContent.setVisibility(View.GONE);

        articleLikebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(status == 1) {
                    toastText.setText("완료된 글은 찜 할 수 없어요!");
                    toast.show();
                } else {
                    if(String.valueOf(userIdnum).equals(userId)){
                        toastText.setText("본인의 글은 찜 할 수 없어요!");
                        toast.show();
                    } else {
                        PostTask myWishTask = new PostTask();
                        try {
                            String response = myWishTask.execute("common/wishlist/" + postId + " /" + userId, String.valueOf(postId), userId).get();
                            JSONObject jsonObject = new JSONObject(response);
                            int responseCode = jsonObject.getInt("code");
                            if(responseCode == 200) {
                                String data = jsonObject.getString("data");
                                JSONArray jsonArray = new JSONArray(data);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject subJsonObject = jsonArray.getJSONObject(i);
                                    int wishcount = subJsonObject.getInt("wish_cnts");
                                    int status = subJsonObject.getInt("status");
                                    articleWishcnt.setText(String.valueOf(wishcount));
                                    if (status == 0) {
                                        articleLikebtn.setImageResource(R.drawable.article_btn_favorite_gray);
                                        articleWishcnt.setTextColor(getResources().getColor(R.color.dark));
                                        toastText.setText("찜을 취소했어요!");
                                        toast.show();
                                    } else {
                                        articleLikebtn.setImageResource(R.drawable.categorylist_btn_favorite_filled);
                                        articleWishcnt.setTextColor(getResources().getColor(R.color.heart));
                                        toastText.setText("찜 했어요!");
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
            }
        });

        articleModalbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(status == 1) {
                    toastText.setText("완료된 글은 선택할 수 없어요");
                    toast.show();
                } else {
                    if (String.valueOf(userIdnum).equals(userId)) {
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
            }
        });

        articleAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                articleCalculateContent.setVisibility(View.GONE);
            }
        });

        articleCalculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(calculateContent == false) {
                    articleCalculateContent.setVisibility(View.VISIBLE);
                    articlePer.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            if(articlePer.getText().toString().equals("") || articlePer.getText().toString().equals(null)) {
                                int perAmount = 0;
                                int totalPrice = Integer.valueOf(articleTotalPrice.getText().toString());
                                int amount = Integer.valueOf(articleAmount.getText().toString());

                                if(perAmount == 0 || perAmount < 0 || perAmount > amount) {
                                    articleInputPer.setText("0");
                                } else {
                                    int calculate = (totalPrice / amount) * perAmount;
                                    articleInputPer.setText(String.valueOf(calculate));
                                }
                            } else {
                                int perAmount = Integer.valueOf(articlePer.getText().toString());
                                int totalPrice = Integer.valueOf(articleTotalPrice.getText().toString());
                                int amount = Integer.valueOf(articleAmount.getText().toString());

                                if(perAmount == 0 || perAmount < 0 || perAmount > amount) {
                                    articleInputPer.setText("0");
                                } else {
                                    int calculate = (totalPrice / amount) * perAmount;
                                    articleInputPer.setText(String.valueOf(calculate));
                                }
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                    calculateContent = true;
                } else {
                    articleCalculateContent.setVisibility(View.GONE);
                    calculateContent = false;
                }
            }
        });

        cLayoutManager = new LinearLayoutManager(getContext());
        cRecyclerView.setLayoutManager(cLayoutManager);
        commentRecyclerAdapter = new CommentRecyclerAdapter(commentRecyclerItems);
        commentRecyclerAdapter.notifyDataSetChanged();
        commentRecyclerAdapter.setModalClickListener(new CommentRecyclerAdapter.OnModalClickListener() {
            @Override
            public void OnModlaClick(View view, int pos) {
                if(status == 1) {
                    toastText.setText("완료된 글의 댓글을 선택할 수 없어요");
                    toast.show();
                } else {
                    String id = String.valueOf(commentRecyclerAdapter.getItem(pos).getUserId());
                    String commentId = String.valueOf(commentRecyclerAdapter.getItem(pos).getCommentId());
                    if (id.equals(userId)) {
                        Bundle args = new Bundle();
                        args.putString("userId", userId);
                        args.putString("commentId", commentId);
                        args.putString("postId", postId);
                        MyCommentBottomSheetDialog myCommentBottomSheetDialog = MyCommentBottomSheetDialog.getInstance();
                        myCommentBottomSheetDialog.setArguments(args);
                        myCommentBottomSheetDialog.show(getChildFragmentManager(), "mybottomsheet");
                    } else {
                        Bundle reportArgs = new Bundle();
                        reportArgs.putString("userId", userId);
                        reportArgs.putString("commentId", commentId);
                        reportArgs.putString("postId", postId);
                        CommentBottomSheetDialog commentBottomSheetDialog = CommentBottomSheetDialog.getInstance();
                        commentBottomSheetDialog.setArguments(reportArgs);
                        commentBottomSheetDialog.show(getChildFragmentManager(), "bottomsheet");
                    }
                }
            }
        });
        cRecyclerView.setAdapter(commentRecyclerAdapter);

        articleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("뒤로가기 애니메이션");
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                getActivity().overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                fragmentManager.beginTransaction().remove(ArticleEtcFragment.this).commit();
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
                if(status == 1) {
                    toastText.setText("이미 완료된 글이에요!");
                    toast.show();
                } else {
                    if(String.valueOf(userIdnum).equals(userId)){
                        Bundle args = new Bundle();
                        args.putString("userId", userId);
                        args.putString("postId", postId);
                        CompleteDialogFragment completeDialogFragment = CompleteDialogFragment.getInstance();
                        completeDialogFragment.setArguments(args);
                        completeDialogFragment.show(getChildFragmentManager(), CompleteDialogFragment.TAG_EVENT_DIALOG);
                    } else {
                        toastText.setText("본인 글만 완료할 수 있어요!");
                        toast.show();
                    }
                }
            }
        });

        articleCommentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(status == 1) {
                    toastText.setText("완료된 글에는 댓글을 달 수 없어요");
                    toast.show();
                } else if(articleComment.getText().toString().equals("")) {
                    toastText.setText("내용이 작성되지 않았어요!");
                    toast.show();
                } else {
                    System.out.println("댓글 내용" + articleComment.getText().toString());
                    PostTask postTask = new PostTask();
                    JSONObject jsonCommentTransfer = new JSONObject();

                    try {
                        jsonCommentTransfer.put("cmts", articleComment.getText().toString());
                        jsonCommentTransfer.put("post_id", Integer.parseInt(postId));
                        jsonCommentTransfer.put("user_id", Integer.parseInt(userId));
                        String jsonString = jsonCommentTransfer.toString();
                        postTask.execute("posts/comment", jsonString);
                        Bundle articleBundle = new Bundle();
                        articleBundle.putString("userId", userId);
                        articleBundle.putString("postId", postId);

                        if(isMyPage == true) {
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            fragmentManager.popBackStack();
                            FragmentTransaction articleTransaction = getActivity().getSupportFragmentManager().beginTransaction();

                            ArticleEtcFragment articleEtcFragment = new ArticleEtcFragment();
                            articleEtcFragment.setArguments(articleBundle);
                            articleTransaction.replace(R.id.mypage_posting_frame, articleEtcFragment);
                            articleTransaction.addToBackStack(null);
                            articleTransaction.commit();
                        } else {
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            fragmentManager.popBackStack();
                            FragmentTransaction articleTransaction = getActivity().getSupportFragmentManager().beginTransaction();

                            ArticleEtcFragment articleEtcFragment = new ArticleEtcFragment();
                            articleEtcFragment.setArguments(articleBundle);
                            articleTransaction.replace(R.id.mypage_myheart_frame, articleEtcFragment);
                            articleTransaction.addToBackStack(null);
                            articleTransaction.commit();
                        }
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return articleEtcView;
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

                    String profile = subJsonObject2.getString("profile");
                    String commentNickname = subJsonObject2.getString("nickname");
                    String comments = subJsonObject2.getString("comments");
                    String commentWrittenBy = subJsonObject2.getString("witten_by");
                    int commentUserId = subJsonObject2.getInt("user_id");
                    int commentId = subJsonObject2.getInt("comment_id");

                    commentRecyclerItems.add(new CommentRecyclerItem(profile, commentNickname, comments, commentWrittenBy, commentUserId, commentId));
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

                    this.userIdnum = subJsonObject2.getInt("user_id");
                    this.title = subJsonObject2.getString("title");
                    this.nickname = subJsonObject2.getString("nickname");
                    this.content = subJsonObject2.getString("contents");
                    this.buyDate = subJsonObject2.getString("buy_date");
                    this.locationCode = subJsonObject2.getLong("post_addr");
                    this.amount = subJsonObject2.getString("amount");
                    this.amountNum = subJsonObject2.getInt("amount_num");
                    this.amountUnit = subJsonObject2.getString("amount_type");
                    this.totalPrice = subJsonObject2.getString("total_price");
                    this.totalPriceNum = subJsonObject2.getString("total_price_num");
                    this.perPrice = subJsonObject2.getString("per_price");
                    this.writtenBy = subJsonObject2.getString("written_by");
                    this.isAuth = subJsonObject2.getInt("isAuth");
                    this.wishcount = subJsonObject2.getInt("wish_cnts");
                    this.profile = subJsonObject2.getString("profile");
                    this.contact = subJsonObject2.getString("contact");
                    this.status = subJsonObject2.getInt("status");
                    this.isMyWish = subJsonObject2.getInt("isMyWish");

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

                    if(img2.isEmpty() || img2 == null || img2.equals("null")){
                        articleItemList.add(new ArticleRecyclerData(img1));
                    } else if(img3.isEmpty() || img3 == null || img3.equals("null")){
                        articleItemList.add(new ArticleRecyclerData(img1));
                        articleItemList.add(new ArticleRecyclerData(img2));
                    } else if(img4.isEmpty() || img4 == null || img4.equals("null")){
                        articleItemList.add(new ArticleRecyclerData(img1));
                        articleItemList.add(new ArticleRecyclerData(img2));
                        articleItemList.add(new ArticleRecyclerData(img3));
                    } else if(img5.isEmpty() || img5 == null || img5.equals("null")){
                        articleItemList.add(new ArticleRecyclerData(img1));
                        articleItemList.add(new ArticleRecyclerData(img2));
                        articleItemList.add(new ArticleRecyclerData(img3));
                        articleItemList.add(new ArticleRecyclerData(img4));
                    } else {
                        articleItemList.add(new ArticleRecyclerData(img1));
                        articleItemList.add(new ArticleRecyclerData(img2));
                        articleItemList.add(new ArticleRecyclerData(img3));
                        articleItemList.add(new ArticleRecyclerData(img4));
                        articleItemList.add(new ArticleRecyclerData(img5));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}