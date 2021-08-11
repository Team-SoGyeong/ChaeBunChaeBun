package com.example.chaebunchaebun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MypageMypostingActivity extends AppCompatActivity {
    ViewPager vp;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_myposting);

        vp = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        MyPostingVPAdapter adapter = new MyPostingVPAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);

        tabLayout.setupWithViewPager(vp);

    }
}