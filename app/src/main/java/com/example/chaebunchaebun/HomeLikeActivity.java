package com.example.chaebunchaebun;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class HomeLikeActivity extends AppCompatActivity {
    ViewPager vp;
    TabLayout tabLayout;
    ImageView homeLikeBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_like);

        tabLayout = findViewById(R.id.home_like_tablayout);
        vp = findViewById(R.id.home_like_viewpager);
        homeLikeBack = findViewById(R.id.home_like_back);

        HomeLikeVPAdapter adapter = new HomeLikeVPAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);

        tabLayout.setupWithViewPager(vp);

        homeLikeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
