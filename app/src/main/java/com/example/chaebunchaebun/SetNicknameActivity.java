package com.example.chaebunchaebun;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SetNicknameActivity extends AppCompatActivity {
    ImageButton btn_next;
    String nickname="";
    EditText set_nickname;

    String chaebun_nickname = "";
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_nickname);
        set_nickname = (EditText) findViewById(R.id.set_nickname);
        btn_next = (ImageButton) findViewById(R.id.btn_next);

        Intent intent = getIntent();
        String user_id = intent.getStringExtra("user_id");
        String kakao_email = intent.getStringExtra("kakao_email");
        String profile_img = intent.getStringExtra("profile_img");
        String sex = intent.getStringExtra("sex");
        String age_range = intent.getStringExtra("age_range");


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nickname = set_nickname.getText().toString();

                Intent intent = new Intent(getApplicationContext(), SetLocationActivity.class);
                intent.putExtra("user_id", user_id);
                intent.putExtra("kakao_email", kakao_email);
                intent.putExtra("profile_img", profile_img);
                intent.putExtra("nickname", nickname);
                intent.putExtra("sex", sex);
                intent.putExtra("age_range", age_range);
                startActivity(intent);
            }
        });
    }
}
