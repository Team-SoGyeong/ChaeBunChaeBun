package com.E2I3.chaebunchaebun;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MypageMypostingDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MypageMypostingDetailFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    SwipeRefreshLayout swipeRefreshLayout;

    ViewPager vp;
    TabLayout tabLayout;
    ImageView myPostingBack;
    String userId;
    int tabPosition = 0;

    public MypageMypostingDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyPageMyPostingDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MypageMypostingDetailFragment newInstance(String param1, String param2) {
        MypageMypostingDetailFragment fragment = new MypageMypostingDetailFragment();
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
        View myPostDetail = inflater.inflate(R.layout.fragment_mypage_myposting_detail, container, false);
        vp = (ViewPager) myPostDetail.findViewById(R.id.view_pager);
        tabLayout = (TabLayout) myPostDetail.findViewById(R.id.tab_layout);
        myPostingBack = (ImageView) myPostDetail.findViewById(R.id.id_back);
        swipeRefreshLayout = myPostDetail.findViewById(R.id.mypage_refresh);

        swipeRefreshLayout.setOnRefreshListener(this);

        userId = getArguments().getString("userId");
        tabPosition = getArguments().getInt("tabPosition");

        MyPostingVPAdapter adapter = new MyPostingVPAdapter(getChildFragmentManager(), userId);
        vp.setAdapter(adapter);
        vp.setCurrentItem(tabPosition);

        tabLayout.setupWithViewPager(vp);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabPosition = tab.getPosition();
                Log.i("tabPosition", String.valueOf(tabPosition));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                tabPosition = tab.getPosition();
                Log.i("tabPosition", String.valueOf(tabPosition));
            }
        });

        myPostingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });

        return myPostDetail;
    }


    @Override
    public void onRefresh() {
        Bundle myPageBundle = new Bundle();
        myPageBundle.putString("userId", userId);
        myPageBundle.putInt("tabPosition", tabPosition);
        FragmentTransaction myPageTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        MypageMypostingDetailFragment mypageMypostingDetailFragment = new MypageMypostingDetailFragment();
        mypageMypostingDetailFragment.setArguments(myPageBundle);
        myPageTransaction.replace(R.id.mypage_posting_frame, mypageMypostingDetailFragment);
        myPageTransaction.commit();

        swipeRefreshLayout.setRefreshing(false);
    }
}