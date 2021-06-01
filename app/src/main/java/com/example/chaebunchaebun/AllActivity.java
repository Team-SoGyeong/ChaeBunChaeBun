package com.example.chaebunchaebun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class AllActivity extends AppCompatActivity{
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle barDrawerToggle;
    private ListView mListView;
    private FirebaseFirestore mDataBase;
    private static final String TAG = "user";
    private MyAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.allpage));

        mDataBase = FirebaseFirestore.getInstance();

        mListView = (ListView) findViewById(R.id.listView);
//네비게이션 시작
        navigationView=findViewById(R.id.nav);
        drawerLayout=findViewById(R.id.layout_drawer);
        //item icon색조를 적용하지 않도록.. 설정 안하면 회색 색조
        navigationView.setItemIconTintList(null);

        Intent intent = getIntent();
        String id = intent.getStringExtra("ID");

        //네비게이션뷰의 아이템을 클릭했을때
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.menu_home:
                        Intent home = new Intent(getApplicationContext(), HomeActivity.class);
                        home.putExtra("ID", id);
                        startActivity(home);
                        break;
                    case R.id.menu_writing:
                        Intent writing = new Intent(getApplicationContext(), WritingActivity.class);
                        writing.putExtra("ID", id);
                        startActivity(writing);
                        break;
                    case R.id.menu_all:
                        Intent all = new Intent(getApplicationContext(), AllActivity.class);
                        all.putExtra("ID", id);
                        startActivity(all);
                        break;
                    case R.id.menu_partici:
                        Intent partici = new Intent(getApplicationContext(), PartiActivity.class);
                        partici.putExtra("ID", id);
                        startActivity(partici);
                        break;
                    case R.id.menu_lead:
                        Intent lead = new Intent(getApplicationContext(), LeadActivity.class);
                        lead.putExtra("ID", id);
                        startActivity(lead);
                        break;
                    case R.id.menu_membership:
                        Intent membership = new Intent(getApplicationContext(), MembershipActivity.class);
                        membership.putExtra("ID", id);
                        startActivity(membership);
                        break;
                    case R.id.menu_logout:
                        Toast.makeText(getApplicationContext(), "로그아웃 되었습니다.", Toast.LENGTH_LONG).show();
                        Intent logout = new Intent(getApplicationContext(), LogoutActivity.class);
                        startActivity(logout);
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

        myAdapter = new MyAdapter();
        dataSetting();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                int count = ((MyItem)myAdapter.getItem(position)).getCount();
                System.out.println(count);

                Intent content = new Intent(getApplicationContext(), DetailActivity.class);
                content.putExtra("market_count", count);
                content.putExtra("ID", id);
                startActivity(content);
            }
        });
    }//onCreate method..

    //액션바의 메뉴를 클릭하는 이벤트를 듣는
    //메소드를 통해서 클릭 상황을 전달하도록..
    //토글 버튼이 클릭 상황을 인지하도록..
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        barDrawerToggle.onOptionsItemSelected(item);

        int id = item.getItemId();

        if(id == R.id.action_search){
            Intent search = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(search);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
//네비게이션 끝

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        Drawable drawable = menu.getItem(0).getIcon();
        if(drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        }
        return true;
    }

    private void dataSetting() {
        mDataBase.collection("market")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()){
                                String s_count = document.getData().get("count").toString();
                                int count = Integer.parseInt(s_count);
                                String title = document.getData().get("title").toString();
                                String nickname = document.getData().get("nickname").toString();
                                String location = document.getData().get("location").toString();

                                myAdapter.addItem(count, title, nickname, location);
                                mListView.setAdapter(myAdapter);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}
