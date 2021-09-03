package com.example.chaebunchaebun;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                //sever에 포스트
/* 서버에 보내는 방법 예시
                PostTask postTask = new PostTask();
                JSONObject jsonCommentTransfer = new JSONObject();

                try {
                    jsonCommentTransfer.put("cmts", articleComment.getText().toString());
                    jsonCommentTransfer.put("post_id", Integer.parseInt(postId));
                    jsonCommentTransfer.put("user_id", Integer.parseInt(userId));
                    String jsonString = jsonCommentTransfer.toString();
                    postTask.execute("posts/comment", jsonString);
                }catch (JSONException e) {
                    e.printStackTrace();
                }
 */


                //user_id를 홈에 넘김
                intent.putExtra("user_id", user_id);
                startActivity(intent);
            }
        });
    }
}