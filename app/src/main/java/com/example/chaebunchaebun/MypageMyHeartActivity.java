package com.example.chaebunchaebun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class MypageMyHeartActivity extends AppCompatActivity {
    ViewPager vp;
    TabLayout tabLayout;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_myheart);

        vp = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        btn = (Button) findViewById(R.id.btn1);

        MyHeartVPAdapter adapter = new MyHeartVPAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);

        tabLayout.setupWithViewPager(vp);

        registerForContextMenu(btn);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater mi = getMenuInflater();

        if (v == btn) {
            System.out.println("push");
            Toast.makeText(MypageMyHeartActivity.this, "버튼 눌림?", Toast.LENGTH_SHORT);
            mi.inflate(R.menu.mypage_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_ing:
                Toast.makeText(MypageMyHeartActivity.this, "진행중인 채분", Toast.LENGTH_SHORT);
                // 항목1을 선택했을때 실행할 코드
                return true;
            case R.id.item_fin:
                Toast.makeText(MypageMyHeartActivity.this, "완료된 채분", Toast.LENGTH_SHORT);
                // 항목2을 선택했을때 실행할 코드
                return true;
        }
        return false;
    }

}