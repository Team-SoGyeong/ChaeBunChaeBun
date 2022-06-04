package com.E2I3.chaebunchaebun;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int vegetable;

    SwipeRefreshLayout swipeRefreshLayout;
    TabLayout categoryTabLayout;
    View category;
    ImageView imgBack, icHelp;
    LinearLayout contenthelp;
    TextView category_name;

    String userId;
    int locationCode;

    public CategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
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

        if(getArguments() != null){
            userId = getArguments().getString("userId");
            Log.i("userId", userId);
            locationCode = getArguments().getInt("locationCode");
            vegetable = getArguments().getInt("vegetable");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        category = inflater.inflate(R.layout.fragment_category, container, false);
        ViewPager categoryvp = category.findViewById(R.id.category_view_pager);
        imgBack = category.findViewById(R.id.category_back);
        category_name = category.findViewById(R.id.tv_categoryName);
        icHelp = category.findViewById(R.id.category_ic_help);
        contenthelp = category.findViewById(R.id.category_content_help);
        swipeRefreshLayout = category.findViewById(R.id.category_refresh);

        contenthelp.setVisibility(View.GONE);

        CategoryVPAdapter categoryVPAdapter = new CategoryVPAdapter(getChildFragmentManager(), userId, locationCode);
        categoryvp.setAdapter(categoryVPAdapter);

        categoryTabLayout = category.findViewById(R.id.tab_category);
        categoryTabLayout.setupWithViewPager(categoryvp);

        swipeRefreshLayout.setOnRefreshListener(this);

        categoryvp.setCurrentItem(vegetable);
        category_name.setText(categoryVPAdapter.getPageTitle(categoryTabLayout.getSelectedTabPosition()));

        categoryTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                category_name.setText(categoryVPAdapter.getPageTitle(tab.getPosition()));
                vegetable = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                category_name.setText(categoryVPAdapter.getPageTitle(tab.getPosition()));
                vegetable = tab.getPosition();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right).replace(R.id.category_layout, new CategoryFragment()).commit();
                fragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left, R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                fragmentTransaction.remove(CategoryFragment.this).commit();
                fragmentManager.popBackStack();
            }
        });

        icHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contenthelp.setVisibility(View.VISIBLE);
            }
        });

        contenthelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contenthelp.setVisibility(View.GONE);
            }
        });

        return category;
    }

    @Override
    public void onRefresh() {
        Bundle categoryBundle = new Bundle();
        categoryBundle.putInt("vegetable", vegetable);
        categoryBundle.putString("userId", userId);
        categoryBundle.putInt("locationCode", locationCode);
        FragmentTransaction categoryTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        CategoryFragment categoryFragment = new CategoryFragment();
        categoryFragment.setArguments(categoryBundle);
        categoryTransaction.replace(R.id.bottom_frame, categoryFragment);
        categoryTransaction.commit();

        swipeRefreshLayout.setRefreshing(false);
    }
}