package com.example.chaebunchaebun;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle barDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
//네비게이션 시작
        navigationView=findViewById(R.id.nav);
        drawerLayout=findViewById(R.id.layout_drawer);
        //item icon색조를 적용하지 않도록.. 설정 안하면 회색 색조
        navigationView.setItemIconTintList(null);


        //네비게이션뷰의 아이템을 클릭했을때
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.menu_writing:
                        Toast.makeText(HomeActivity.this, "소분 시작하기", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_all:
                        Toast.makeText(HomeActivity.this, "전체 소분팟", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_partici:
                        Toast.makeText(HomeActivity.this, "참가하고있는 소분팟", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_lead:
                        Toast.makeText(HomeActivity.this, "주최하고있는 소분팟", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_membership:
                        Toast.makeText(HomeActivity.this, "회원정보 수정/탈퇴", Toast.LENGTH_SHORT).show();
                        break;
                }

                //Drawer를 닫기...
                drawerLayout.closeDrawer(navigationView);

                return false;
            }
        });

        //Drawer 조절용 토글 버튼 객체 생성
        barDrawerToggle= new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name,R.string.app_name);
        //ActionBar(제목줄)의 홈or업버튼의 위치에 토글아이콘이 보이게끔...
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //삼선아이콘 모양으로 보이도록
        //토글버튼의 동기를 맞추기
        barDrawerToggle.syncState();

        //삼선 아이콘과 화살표아이콘이 자동으로 변환하도록
        drawerLayout.addDrawerListener(barDrawerToggle);

    }//onCreate method..

    //액션바의 메뉴를 클릭하는 이벤트를 듣는
    //메소드를 통해서 클릭 상황을 전달하도록..
    //토글 버튼이 클릭 상황을 인지하도록..
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        barDrawerToggle.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }
//네비게이션 끝

}

