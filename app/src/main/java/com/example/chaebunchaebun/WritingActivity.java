package com.example.chaebunchaebun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
    EditText edt_title, edt_location, edt_vegetable, edt_people, edt_other;
    TextView txt_date;
    Button btn_save;
    ImageView img_calender;
    public int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.writingpage));

        Intent intent = getIntent();
        String id = intent.getStringExtra("ID");

        mDataBase = FirebaseFirestore.getInstance();
        count = 0;

        edt_title = (EditText) findViewById(R.id.edt_title);
        edt_location = (EditText) findViewById(R.id.edt_location);
        edt_vegetable = (EditText) findViewById(R.id.edt_vegetable);
        edt_people = (EditText) findViewById(R.id.edt_people);
        txt_date = (TextView) findViewById(R.id.txt_date);
        edt_other = (EditText) findViewById(R.id.edt_other);

        btn_save = findViewById(R.id.btn_save);

        img_calender = (ImageView) findViewById(R.id.img_calender);

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

        getCount();
        getNickname(id);

        img_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(WritingActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayofMonth) {
                        txt_date.setText(year + "-" + (month + 1) + "-" + dayofMonth);
                    }
                }, 2020, 1, 1);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edt_title.getText().toString();
                String location = edt_location.getText().toString();
                String vegetable = edt_vegetable.getText().toString();
                String s_people = edt_people.getText().toString();
                String date = txt_date.getText().toString();
                String other = edt_other.getText().toString();

                System.out.println(count);

                if(title.equals("")){
                    Toast.makeText(WritingActivity.this, "입력되지 않은 칸이 있습니다.", Toast.LENGTH_SHORT).show();
                    edt_title.requestFocus();
                } else if(location.equals("")){
                    Toast.makeText(WritingActivity.this, "입력되지 않은 칸이 있습니다.", Toast.LENGTH_SHORT).show();
                    edt_location.requestFocus();
                } else if(vegetable.equals("")){
                    Toast.makeText(WritingActivity.this, "입력되지 않은 칸이 있습니다.", Toast.LENGTH_SHORT).show();
                    edt_vegetable.requestFocus();
                } else if(s_people.equals("")){
                    Toast.makeText(WritingActivity.this, "입력되지 않은 칸이 있습니다.", Toast.LENGTH_SHORT).show();
                    edt_people.requestFocus();
                } else if(date.equals("")){
                    Toast.makeText(WritingActivity.this, "입력되지 않은 칸이 있습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    int people = Integer.parseInt(s_people);
                    setContent(id, title, location, vegetable, people, date, other);
                }
            }
        });
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

    private synchronized void getNickname(String getUserId) {
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

    void setNickname(String nickname) {
        this.nickname = nickname;
    }

    private synchronized void setContent(String id, String title, String location, String vegetable, int people, String date, String other) {
        this.count++;

        Map<String, Object> result = new HashMap<>();
        result.put("market count", this.count);
        result.put("title", title);
        result.put("location", location);
        result.put("vegetable", vegetable);
        result.put("people", people);
        result.put("date", date);
        result.put("other", other);
        result.put("nickname", this.nickname);
        System.out.println("dbcount: "+ this.count);

        mDataBase.collection("market")
                .document(Integer.toString(this.count))
                .set(result)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(WritingActivity.this, "저장되었습니다", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("ID", id);
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

    private synchronized void getCount(){
        mDataBase.collection("market")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()){
                                int docCount = 1;
                                setCount(docCount);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

    }

    private void setCount(int count){
        this.count += count;
    }
}