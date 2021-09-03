package com.example.chaebunchaebun;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SetLocationActivity extends AppCompatActivity {
    ImageButton search, btn_next;
    String set_location;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_location);

        search = (ImageButton) findViewById(R.id.ic_search);
        btn_next = (ImageButton) findViewById(R.id.btn_next);

        Intent intent = getIntent();
        String user_id = intent.getStringExtra("user_id");
        String kakao_email = intent.getStringExtra("kakao_email");
        String profile_img = intent.getStringExtra("profile_img");
        String nickname = intent.getStringExtra("nickname");


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchLocationActivity.class);
                startActivity(intent);
                //set_location 값 저장하기
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SetStartActivity.class);
                intent.putExtra("user_id", user_id);
                intent.putExtra("kakao_email", kakao_email);
                intent.putExtra("profile_img", profile_img);
                intent.putExtra("nickname", nickname);
                intent.putExtra("location", set_location);
                startActivity(intent);
            }
        });
    }
}