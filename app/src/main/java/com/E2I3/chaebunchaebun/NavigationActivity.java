package com.E2I3.chaebunchaebun;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class NavigationActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private HomeFragment homefg;
    private LikeListFragment likefg;
    private long backKeyPressedTime = 0;

    private TextView toastText;
    private Toast toast;

    String userId = null;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_navigation);
        LayoutInflater inflater = getLayoutInflater();
        View customToast = inflater.inflate(R.layout.custom_report_toast, (ViewGroup) findViewById(R.id.custom_toast_layout));

        //user_id 받기
        Intent intent = getIntent();
        this.userId = intent.getStringExtra("userId");
//        this.userId = "60";

        toastText = (TextView) customToast.findViewById(R.id.custom_toast_text);
        toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(customToast);

        Bundle homeBundle = new Bundle();
        //homeBundle.putString("userId", userId);
        FragmentTransaction homeTransaction = getSupportFragmentManager().beginTransaction();
        homeTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.getUserId(userId);
        homeFragment.setArguments(homeBundle);
        homeTransaction.add(R.id.bottom_frame, homeFragment);
        homeTransaction.commit();

        /*bottomNavigationView = findViewById(R.id.bottom_navigation);
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
        likefg = new LikeListFragment();
        communityfg = new CommunityFragment();
        myfg = new MypageFragment();
        setFragment(0);*/
    }

    /*private void setFragment(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n) {
            case 0:
                homefg.getUserId(userId);
                ft.replace(R.id.bottom_frame, homefg).commitAllowingStateLoss();
                //ft.commit();
                break;
            case 1:
                likefg.getUserId(userId);
                ft.replace(R.id.bottom_frame, likefg).commitAllowingStateLoss();
                //ft.commit();
                break;
            case 2:
                communityfg.getUserId(userId);
                ft.replace(R.id.bottom_frame, communityfg).commitAllowingStateLoss();
                //ft.commit();
                break;
            case 3:
                myfg.getUserId(userId);
                ft.replace(R.id.bottom_frame, myfg).commitAllowingStateLoss();
                //ft.commit();
                break;
        }
    }*/

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
//        if(System.currentTimeMillis() > backKeyPressedTime + 2000){
//            backKeyPressedTime = System.currentTimeMillis();
//            toastText.setText("한 번 더 누르면 앱이 종료돼요!");
//            toast.show();
//        }
//
//        if(System.currentTimeMillis() <= backKeyPressedTime + 2000) {
//            //moveTaskToBack(true);
//            finish();
//            //android.os.Process.killProcess(android.os.Process.myPid());
//        }
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backKeyPressedTime;

        if (0 <= intervalTime && 2000 >= intervalTime) {
            moveTaskToBack(true);
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        } else {
            backKeyPressedTime = tempTime;
            toastText.setText("한 번 더 누르면 앱이 종료돼요!");
            toast.show();
        }
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
}
