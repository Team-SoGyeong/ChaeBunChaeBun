package com.example.chaebunchaebun;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SetNicknameActivity extends AppCompatActivity {
    ImageButton btn_next;
    String nickname="";
    EditText set_nickname;
    TextView nickname_invaild;

    String chaebun_nickname = "";
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_nickname);
        set_nickname = (EditText) findViewById(R.id.set_nickname);
        nickname_invaild = (TextView) findViewById(R.id.set_invaild);
        btn_next = (ImageButton) findViewById(R.id.btn_next);

        Intent intent = getIntent();
        String user_id = intent.getStringExtra("user_id");
        String kakao_email = intent.getStringExtra("kakao_email");
        String profile_img = intent.getStringExtra("profile_img");
        String sex = intent.getStringExtra("sex");
        String age_range = intent.getStringExtra("age_range");

        btn_next.setClickable(false);
        set_nickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String getNickname = set_nickname.getText().toString();
                if (getNickname.length() <= 10 && getNickname.length() > 0) {
                    set_nickname.setBackgroundResource(R.drawable.profile_edittext_green);
                    nickname_invaild.setTextColor(Color.rgb(154, 151, 146));
                    btn_next.setImageResource(R.drawable.nickname_signup_btn);
                    btn_next.setClickable(true);

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
                } else {
                    set_nickname.setBackgroundResource(R.drawable.profile_edittext_red);
                    nickname_invaild.setTextColor(Color.rgb(190, 23, 0));
                    btn_next.setImageResource(R.drawable.btn_signup_next);
                    btn_next.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
}
