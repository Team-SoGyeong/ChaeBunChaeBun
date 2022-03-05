package com.E2I3.chaebunchaebun;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeNoticeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeNoticeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View homeNoticeView;

    private RecyclerView homeNoticeList;
    private ArrayList<NoticeListItem> noticeListItems;
    private NoticeListAdapter noticeListAdapter;
    private LinearLayoutManager nLayoutManager;

    TextView homeNoticeNolist;
    ImageView homeNoticeBack;

    String userId = "";
    String caseBy = "";
    int noticeId = 0;

    public HomeNoticeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeNoticeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeNoticeFragment newInstance(String param1, String param2) {
        HomeNoticeFragment fragment = new HomeNoticeFragment();
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

        noticeListItems = new ArrayList<NoticeListItem>();
        this.userId = getArguments().getString("userId");
        getMyNoticeList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeNoticeView = inflater.inflate(R.layout.fragment_home_notice, container, false);

        homeNoticeBack = (ImageView) homeNoticeView.findViewById(R.id.home_notice_back);
        homeNoticeList = homeNoticeView.findViewById(R.id.home_notice_list);
        homeNoticeNolist = homeNoticeView.findViewById(R.id.home_notice_nolist);

        if(noticeListItems.isEmpty()) {
            homeNoticeNolist.setVisibility(View.VISIBLE);
            homeNoticeList.setVisibility(View.GONE);
        } else {
            homeNoticeNolist.setVisibility(View.GONE);
            homeNoticeList.setVisibility(View.VISIBLE);

            nLayoutManager = new LinearLayoutManager(getContext());
            homeNoticeList.setLayoutManager(nLayoutManager);
            MainRecyclerDecoration mainRecyclerDecoration = new MainRecyclerDecoration(40);
            homeNoticeList.addItemDecoration(mainRecyclerDecoration);
            /*homeNoticeList.setLayoutManager(new LinearLayoutManager(getContext()) {
                @Override
                public boolean canScrollHorizontally() {
                    return false;
                }

                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });*/
            noticeListAdapter = new NoticeListAdapter(noticeListItems);

            homeNoticeList.setAdapter(noticeListAdapter);

            noticeListAdapter.setOnItemClickListener(new NoticeListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int pos) {
                    PutTask locationPutTask = new PutTask();
                    try {
                        String response = locationPutTask.execute("common/notice/" + caseBy + "/" + noticeId, caseBy, String.valueOf(noticeId)).get();
                        JSONObject jsonObject = new JSONObject(response);
                        int responseCode = jsonObject.getInt("code");
                        if(responseCode == 200){
                            String postId = String.valueOf(noticeListAdapter.getItem(pos).getPostId());
                            int categoryId = noticeListAdapter.getItem(pos).getCategoryId();
                            Bundle articleBundle = new Bundle();
                            articleBundle.putString("userId", userId);
                            articleBundle.putString("postId", postId);
                            articleBundle.putInt("categoryId", categoryId);
                            articleBundle.putBoolean("isMyPage", false);
                            FragmentTransaction articleTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            ArticleFragment articleFragment = new ArticleFragment();
                            articleFragment.setArguments(articleBundle);
                            articleTransaction.replace(R.id.bottom_frame, articleFragment);
                            articleTransaction.addToBackStack(null);
                            articleTransaction.commit();
                        } else {
                            Toast.makeText(getContext(),"잘 못 된 접근입니다.",Toast.LENGTH_SHORT).show();
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
            homeNoticeBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("뒤로가기");
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                    fragmentTransaction.remove(HomeNoticeFragment.this).commit();
                    fragmentManager.popBackStack();
                }
            });
        }

        return homeNoticeView;
    }

    public void getMyNoticeList() {
        noticeListItems.clear();
        String resultText = "[NULL]";

        try {
            resultText = new GetTask("common/notice/" + this.userId).execute().get();
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
                noticeId = subJsonObject.getInt("notice_id");
                caseBy = subJsonObject.getString("caseBy");
                String nickname = subJsonObject.getString("nickname");
                String img = subJsonObject.getString("img1");
                String title = subJsonObject.getString("title");
                String content = subJsonObject.getString("contents");
                String writtenBy = subJsonObject.getString("dates");
                int isAuth = subJsonObject.getInt("isAuth");
                int postId = subJsonObject.getInt("post_id");
                int categoryId = subJsonObject.getInt("category_id");
                String isClick = subJsonObject.getString("isClick");

                noticeListItems.add(new NoticeListItem(caseBy, nickname, img, title, content, writtenBy,
                        isAuth, postId, categoryId, this.userId, isClick));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}