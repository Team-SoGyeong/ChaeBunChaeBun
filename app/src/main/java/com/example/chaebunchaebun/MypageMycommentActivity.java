package com.example.chaebunchaebun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

public class MypageMycommentActivity extends AppCompatActivity {
    ViewPager vp;
    TabLayout tabLayout;
    ImageView myCommentBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_mycomment);

        vp = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        myCommentBack = (ImageView) findViewById(R.id.id_back);

        MyCommentVPAdapter adapter = new MyCommentVPAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);

        tabLayout.setupWithViewPager(vp);

        myCommentBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}