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
 * Use the {@link HomeLikeFinishFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeLikeFinishFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView homelike_finish_list;
    private ArrayList<HomeListItem> homeListItems;
    private HomeListAdapter homeListAdapter;
    private LinearLayoutManager hLayoutManager;

    TextView homelike_finish_text;

    public HomeLikeFinishFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainLikeFinishFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeLikeFinishFragment newInstance(String param1, String param2) {
        HomeLikeFinishFragment fragment = new HomeLikeFinishFragment();
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
        View homeLikeFinishView = inflater.inflate(R.layout.fragment_home_like_finish, container, false);

        homelike_finish_text = homeLikeFinishView.findViewById(R.id.homelike_finish_text);
        homelike_finish_list = homeLikeFinishView.findViewById(R.id.homelike_finish_list);
        //homelike_finish_text.setVisibility(View.GONE);

        homeListItems = new ArrayList<HomeListItem>();

        //homeListItems.add(new HomeListItem(R.drawable.vegetables, "예제 1번", "예제 1번", "예제 1번", "예제 1번", "08/02"));
        //homeListItems.add(new HomeListItem(R.drawable.logo, "예제 2번", "예제 2번", "예제 2번", "예제 2번", "07/11"));
        //homeListItems.add(new HomeListItem(R.drawable.logo_2, "제목 긴 게시물 연습 양파양파양파양파양파양파양파양파양파", "양파", "양파", "양파", "07/31"));

        hLayoutManager = new LinearLayoutManager(getContext());
        homelike_finish_list.setLayoutManager(hLayoutManager);
        MainRecyclerDecoration mainRecyclerDecoration = new MainRecyclerDecoration(40);
        homelike_finish_list.addItemDecoration(mainRecyclerDecoration);
        homeListAdapter = new HomeListAdapter(homeListItems);
        homeListAdapter.setOnItemClickListener(new CategoryListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                FragmentTransaction articleTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                ArticleFragment articleFragment = new ArticleFragment();
                articleTransaction.replace(R.id.bottom_frame, articleFragment);
                articleTransaction.addToBackStack(null);
                articleTransaction.commit();
            }
        });
        homelike_finish_list.setAdapter(homeListAdapter);

        return homeLikeFinishView;
    }
}