package com.E2I3.chaebunchaebun;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MypageCommunityPostingActivity extends AppCompatActivity {
    String userId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_community_myposting);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        System.out.println("내가 쓴 글" + userId);

        Bundle myPostDetailBundle = new Bundle();
        myPostDetailBundle.putString("userId", userId);
        myPostDetailBundle.putInt("tabPosition", 0);
        FragmentTransaction myPostingTransaction = getSupportFragmentManager().beginTransaction();
        MypageCommunityPostingDetailFragment myPageDetailFragment = new MypageCommunityPostingDetailFragment();
        myPageDetailFragment.setArguments(myPostDetailBundle);
        myPostingTransaction.replace(R.id.mypage_posting_frame, myPageDetailFragment);
        myPostingTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);

    }
}