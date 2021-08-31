package com.example.chaebunchaebun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class MypageMyHeartActivity extends AppCompatActivity {
    ViewPager vp;
    TabLayout tabLayout;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_myheart);

        vp = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        MyHeartVPAdapter adapter = new MyHeartVPAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);

        tabLayout.setupWithViewPager(vp);
    }
}