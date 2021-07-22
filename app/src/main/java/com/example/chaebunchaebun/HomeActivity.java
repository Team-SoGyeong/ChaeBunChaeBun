package com.example.chaebunchaebun;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.TabActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity{
    private RecyclerView mRecyclerView;
    private Integer[] vegetableID = {R.drawable.onion, R.drawable.garlic, R.drawable.green_onion,
            R.drawable.carrot, R.drawable.mushroom, R.drawable.green_vege, R.drawable.cabbage,
            R.drawable.radish, R.drawable.potato, R.drawable.sweet_potato};
    private ArrayList<MainRecyclerData> itemList;
    private MainRecyclerAdapter mainRecyclerAdapter;
    private LinearLayoutManager mLayoutManager;
    ActionBar.Tab tabNew, tabSoon, tabLast, tabMy;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

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

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mainRecyclerAdapter = new MainRecyclerAdapter(itemList);
        mRecyclerView.setAdapter(mainRecyclerAdapter);

        ViewPager vp = findViewById(R.id.view_pager);
        MainVPAdapter adapter = new MainVPAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(vp);
    }
}