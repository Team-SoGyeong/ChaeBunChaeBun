package com.E2I3.chaebunchaebun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

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