package com.example.chaebunchaebun;

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

import java.util.ArrayList;

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

    View view;
    ImageView articleBack;
    int recyclerPosition = -1;

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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_article, container, false);
        aRecyclerView = (RecyclerView) view.findViewById(R.id.article_recycler_img);
        cRecyclerView = (RecyclerView) view.findViewById(R.id.article_comment_list);

        articleBack = (ImageView) view.findViewById(R.id.article_back);

        articleItemList = new ArrayList<ArticleRecyclerData>();

        articleItemList.add(new ArticleRecyclerData(R.drawable.vegetables));
        articleItemList.add(new ArticleRecyclerData(R.drawable.onion));
        articleItemList.add(new ArticleRecyclerData(R.drawable.group_624));
        articleItemList.add(new ArticleRecyclerData(R.drawable.logo_2));
        articleItemList.add(new ArticleRecyclerData(R.drawable.logo));

        aLayoutManager = new LinearLayoutManager(getContext());
        aLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        aRecyclerView.setLayoutManager(aLayoutManager);
        articleRecyclerAdapter = new ArticleRecyclerAdapter(articleItemList);
        aRecyclerView.setAdapter(articleRecyclerAdapter);

        commentRecyclerItems = new ArrayList<CommentRecyclerItem>();

        commentRecyclerItems.add(new CommentRecyclerItem("심장이 분분대", "꺄오 참여하고 싶어요 양파를 파파파팟", "07/22 03:25"));
        commentRecyclerItems.add(new CommentRecyclerItem("심장이 분분대", "꺄오 참여하고 싶어요 양파를 파파파팟", "07/22 03:25"));
        commentRecyclerItems.add(new CommentRecyclerItem("심장이 분분대", "꺄오 참여하고 싶어요 양파를 파파파팟", "07/22 03:25"));

        cLayoutManager = new LinearLayoutManager(getContext());
        cRecyclerView.setLayoutManager(cLayoutManager);
        commentRecyclerAdapter = new CommentRecyclerAdapter(commentRecyclerItems);
        cRecyclerView.setAdapter(commentRecyclerAdapter);

        articleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(ArticleFragment.this).commit();
                fragmentManager.popBackStack();
            }
        });

        return view;
    }
}