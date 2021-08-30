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

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeLikeIngFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeLikeIngFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView home_like_list;
    private ArrayList<HomeListItem> homeListItems;
    private HomeListAdapter homeListAdapter;
    private LinearLayoutManager hLayoutManager;

    TextView home_like_text;

    public HomeLikeIngFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainLikeIngFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeLikeIngFragment newInstance(String param1, String param2) {
        HomeLikeIngFragment fragment = new HomeLikeIngFragment();
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
        View homeLikeView  = inflater.inflate(R.layout.fragment_home_like_ing, container, false);

        home_like_text = homeLikeView.findViewById(R.id.homelike_Ing_text);
        home_like_list = homeLikeView.findViewById(R.id.homelike_Ing_list);
        //home_like_text.setVisibility(View.GONE);

        homeListItems = new ArrayList<HomeListItem>();

        //homeListItems.add(new HomeListItem(R.drawable.vegetables, "예제 1번", "예제 1번", "예제 1번", "예제 1번", "08/02"));
        //homeListItems.add(new HomeListItem(R.drawable.logo, "예제 2번", "예제 2번", "예제 2번", "예제 2번", "07/11"));
        //homeListItems.add(new HomeListItem(R.drawable.logo_2, "제목 긴 게시물 연습 양파양파양파양파양파양파양파양파양파", "양파", "양파", "양파", "07/31"));

        hLayoutManager = new LinearLayoutManager(getContext());
        home_like_list.setLayoutManager(hLayoutManager);
        MainRecyclerDecoration mainRecyclerDecoration = new MainRecyclerDecoration(40);
        home_like_list.addItemDecoration(mainRecyclerDecoration);
        homeListAdapter = new HomeListAdapter(homeListItems);
        homeListAdapter.setOnItemClickListener(new HomeListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                int postId = (int) view.getTag(0);
                Bundle articleBundle = new Bundle();
                articleBundle.putString("postId", String.valueOf(postId));
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
                BottomSheetDialog bottomSheetDialog = BottomSheetDialog.getInstance();
                bottomSheetDialog.show(getChildFragmentManager(), "bottomsheet");
            }
        });
        home_like_list.setAdapter(homeListAdapter);

        return homeLikeView;
    }
}