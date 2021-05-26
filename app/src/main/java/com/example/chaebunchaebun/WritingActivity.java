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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WritingActivity extends AppCompatActivity{
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle barDrawerToggle;
    private FirebaseFirestore mDataBase;
    private static final String TAG = "user";
    String nickname = "";
    EditText edt_title, edt_location, edt_vegetable, edt_people, edt_date, edt_other;
    Button btn_save;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.writingpage));

        Intent intent = getIntent();
        String id = intent.getStringExtra("ID");

        edt_title = findViewById(R.id.edt_title);
        edt_location = findViewById(R.id.edt_location);
        edt_vegetable = findViewById(R.id.edt_vegetable);
        edt_people = findViewById(R.id.edt_people);
        edt_date = findViewById(R.id.edt_date);
        edt_other = findViewById(R.id.edt_other);
        btn_save = findViewById(R.id.btn_save);

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
                    case R.id.menu_home:
                        Intent home = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(home);
                        break;
                    case R.id.menu_writing:
                        Intent writing = new Intent(getApplicationContext(), WritingActivity.class);
                        startActivity(writing);
                        break;
                    case R.id.menu_all:
                        Intent all = new Intent(getApplicationContext(), AllActivity.class);
                        startActivity(all);
                        break;
                    case R.id.menu_partici:
                        Intent partici = new Intent(getApplicationContext(), PartiActivity.class);
                        startActivity(partici);
                        break;
                    case R.id.menu_lead:
                        Intent lead = new Intent(getApplicationContext(), LeadActivity.class);
                        startActivity(lead);
                        break;
                    case R.id.menu_membership:
                        Intent membership = new Intent(getApplicationContext(), MembershipActivity.class);
                        startActivity(membership);
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

    private void setContent(String id, String title, String location, String vegetable, int people, Date date, String other) {
        getNickname(id);

        Map<String, Object> result = new HashMap<>();
        result.put("title", title);
        result.put("location", location);
        result.put("vegetable", vegetable);
        result.put("people", people);
        result.put("date", date);
        result.put("other", other);
        result.put("nickname", nickname);

        mDataBase.collection("market")
                .document(title)
                .set(result)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(WritingActivity.this, "저장되었습니다", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(WritingActivity.this, "저장에 실패했습니다", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getNickname(String getUserId) {
        mDataBase.collection("users")
                .whereEqualTo("userId", getUserId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Log.d(TAG, document.getId() + " => " + document.getData().get("userPw"));
                                String userNickname = document.getData().get("userNickname").toString();
                                Log.d(TAG, userNickname);
                                setNickname(userNickname);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
