package com.example.chaebunchaebun;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;
    private ArrayList<MainRecyclerData> itemList;
    private MainRecyclerAdapter mainRecyclerAdapter;
    private LinearLayoutManager mLayoutManager;

    View view;
    ActionBar.Tab tabNew, tabSoon, tabLast, tabMy;
    ViewGroup viewGroup;
    ViewPager vp;
    TabLayout tabLayout;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        view = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        vp = view.findViewById(R.id.view_pager);
        tabLayout = view.findViewById(R.id.tab_layout);

        itemList = new ArrayList<MainRecyclerData>();

        itemList.add(new MainRecyclerData(R.drawable.onion));
        itemList.add(new MainRecyclerData(R.drawable.garlic));
        itemList.add(new MainRecyclerData(R.drawable.green_onion));
        itemList.add(new MainRecyclerData(R.drawable.carrot));
        itemList.add(new MainRecyclerData(R.drawable.mushroom));
        itemList.add(new MainRecyclerData(R.drawable.green_vege));
        itemList.add(new MainRecyclerData(R.drawable.cabbage));
        itemList.add(new MainRecyclerData(R.drawable.radish));
        itemList.add(new MainRecyclerData(R.drawable.potato));
        itemList.add(new MainRecyclerData(R.drawable.sweet_potato));

        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mainRecyclerAdapter = new MainRecyclerAdapter(itemList);
        mRecyclerView.setAdapter(mainRecyclerAdapter);

        MainVPAdapter adapter = new MainVPAdapter(getChildFragmentManager());
        vp.setAdapter(adapter);

        tabLayout.setupWithViewPager(vp);

        // Inflate the layout for this fragment
        return view;
    }
}