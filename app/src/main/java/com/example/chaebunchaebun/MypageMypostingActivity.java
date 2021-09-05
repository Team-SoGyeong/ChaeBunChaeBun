package com.example.chaebunchaebun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

public class MypageMypostingActivity extends AppCompatActivity {
    String userId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_myposting);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

        Bundle myPostDetailBundle = new Bundle();
        myPostDetailBundle.putString("userId", userId);
        FragmentTransaction myPostingTransaction = getSupportFragmentManager().beginTransaction();
        MypageMypostingDetailFragment myPageDetailFragment = new MypageMypostingDetailFragment();
        myPageDetailFragment.setArguments(myPostDetailBundle);
        myPostingTransaction.replace(R.id.mypage_posting_frame, myPageDetailFragment);
        myPostingTransaction.commit();
    }
}