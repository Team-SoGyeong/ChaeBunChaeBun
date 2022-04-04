package com.E2I3.chaebunchaebun;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class SearchActivity extends AppCompatActivity {
    int locationCode = 0;
    String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);

        Intent intent = getIntent();
        locationCode = intent.getIntExtra("locationCode", 0);
        userId = intent.getStringExtra("userId");

        Bundle myPostDetailBundle = new Bundle();
        myPostDetailBundle.putInt("locationCode", locationCode);
        myPostDetailBundle.putString("userId", userId);
        FragmentTransaction searchPostTransaction = getSupportFragmentManager().beginTransaction();
        SearchDetailFragment searchDetailFragment = new SearchDetailFragment();
        searchDetailFragment.setArguments(myPostDetailBundle);
        searchPostTransaction.replace(R.id.search_frame, searchDetailFragment);
        searchPostTransaction.commit();
    }
}
