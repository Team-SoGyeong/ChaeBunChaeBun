package com.example.chaebunchaebun;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class SetStartActivity  extends AppCompatActivity {
    ImageButton btn_next;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_start);
        btn_next = (ImageButton) findViewById(R.id.btn_next);

        Intent intent = getIntent();
        String user_id = intent.getStringExtra("user_id");
        String kakao_email = intent.getStringExtra("kakao_email");
        String profile_img = intent.getStringExtra("profile_img");
        String nickname = intent.getStringExtra("nickname");
        String location = intent.getStringExtra("location");
        int location_seq = intent.getIntExtra("locationCode",0);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                //sever에 포스트
                PostTask postTask = new PostTask();
                JSONObject jsonCommentTransfer = new JSONObject();

                try {
                    jsonCommentTransfer.put("login_type", "k");
                    jsonCommentTransfer.put("nickname", nickname);
                    jsonCommentTransfer.put("address_seq", location_seq);
                    jsonCommentTransfer.put("profile", profile_img);
                    jsonCommentTransfer.put("email", kakao_email);
                    jsonCommentTransfer.put("sex", "");
                    jsonCommentTransfer.put("age_range", "");

                    String jsonString = jsonCommentTransfer.toString();
                    postTask.execute("auth2/signin/kakao", jsonString);
                }catch (JSONException e) {
                    e.printStackTrace();
                }


                //user_id를 홈에 넘김
                intent.putExtra("user_id", user_id);
                startActivity(intent);
            }
        });
    }
}