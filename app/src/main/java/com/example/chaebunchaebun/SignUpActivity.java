package com.example.chaebunchaebun;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    EditText email, certifyNum, pw, match_pw;
    TextView certifyInfo;
    ImageButton btn_check, btn_join;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.set_signup));

        email = (EditText) findViewById(R.id.set_email);
        certifyNum = (EditText) findViewById(R.id.certifyNum);
        certifyInfo = (TextView) findViewById(R.id.certifyInfo);
        pw = (EditText) findViewById(R.id.set_pw);
        match_pw = (EditText) findViewById(R.id.match_pw);

        btn_check = (ImageButton) findViewById(R.id.btn_certify);
        btn_join = (ImageButton) findViewById(R.id.btn_next);

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                certifyNum.setVisibility(View.VISIBLE);
                certifyInfo.setVisibility(View.VISIBLE);
            }
        });
        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SetNicknameActivity.class);
                startActivity(intent);
            }
        });

    }
}