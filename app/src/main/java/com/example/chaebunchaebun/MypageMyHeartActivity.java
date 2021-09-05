package com.example.chaebunchaebun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class MypageMyHeartActivity extends AppCompatActivity {
    String userId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_myheart);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

        Bundle myPostDetailBundle = new Bundle();
        myPostDetailBundle.putString("userId", userId);
        FragmentTransaction myPostingTransaction = getSupportFragmentManager().beginTransaction();
        MypageMyheartDetailFragment mypageMyheartDetailFragment = new MypageMyheartDetailFragment();
        mypageMyheartDetailFragment.setArguments(myPostDetailBundle);
        myPostingTransaction.replace(R.id.mypage_myheart_frame, mypageMyheartDetailFragment);
        myPostingTransaction.commit();
    }
}