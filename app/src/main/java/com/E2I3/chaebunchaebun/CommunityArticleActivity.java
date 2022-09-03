package com.E2I3.chaebunchaebun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

public class CommunityArticleActivity extends AppCompatActivity {
    String userId = null;
    String postId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_article);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        postId = intent.getStringExtra("postId");

        Bundle myPostDetailBundle = new Bundle();
        myPostDetailBundle.putString("userId", userId);
        myPostDetailBundle.putString("postId", postId);
        FragmentTransaction myPostingTransaction = getSupportFragmentManager().beginTransaction();
        CommunityArticleFragment communityArticleFragment = new CommunityArticleFragment();
        communityArticleFragment.setArguments(myPostDetailBundle);
        myPostingTransaction.replace(R.id.community_article_frame, communityArticleFragment);
        myPostingTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
}