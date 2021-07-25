package com.example.chaebunchaebun;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.navigation.NavigationBarView;

import org.jetbrains.annotations.NotNull;

public class NavigationActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private HomeFragment homefg;
    private MainLastFragment lastfg;
    private MypageFragment myfg;
    private CategoryFragment catefg;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_navigation);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bottom_home:
                        setFragment(0);
                        break;
                    case R.id.bottom_like:
                        setFragment(1);
                        break;
                    case R.id.bottom_community:
                        setFragment(2);
                        break;
                    case R.id.bottom_mypage:
                        setFragment(3);
                        break;
                }
                return true;
            }
        });

        homefg = new HomeFragment();
        catefg = new CategoryFragment();
        lastfg = new MainLastFragment();
        myfg = new MypageFragment();
        setFragment(0);
    }

    private void setFragment(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n) {
            case 0:
                ft.replace(R.id.bottom_frame, homefg);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.bottom_frame, catefg);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.bottom_frame, lastfg);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.bottom_frame, myfg);
                ft.commit();
                break;
        }
    }
}
