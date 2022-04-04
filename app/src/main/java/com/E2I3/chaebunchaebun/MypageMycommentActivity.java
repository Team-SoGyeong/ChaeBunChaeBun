package com.E2I3.chaebunchaebun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

public class MypageMycommentActivity extends AppCompatActivity {
    String userId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_mycomment);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

        Bundle myPostDetailBundle = new Bundle();
        myPostDetailBundle.putString("userId", userId);
        FragmentTransaction myCommentTransaction = getSupportFragmentManager().beginTransaction();
        MypageMycommentDetailFragment myCommentDetailFragment = new MypageMycommentDetailFragment();
        myCommentDetailFragment.setArguments(myPostDetailBundle);
        myCommentTransaction.replace(R.id.mypage_comment_frame, myCommentDetailFragment);
        myCommentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);

    }
}