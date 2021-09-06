package com.example.chaebunchaebun;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class SetStartActivity  extends AppCompatActivity {
    ImageButton btn_next;
    String user_id="";
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_start);
        btn_next = (ImageButton) findViewById(R.id.btn_next);

        Intent intent = getIntent();
        String kakao_email = intent.getStringExtra("kakao_email");
        String profile_img = intent.getStringExtra("profile_img");
        String nickname = intent.getStringExtra("nickname");
        int location_seq = intent.getIntExtra("locationCode",0);
        String sex = intent.getStringExtra("sex");
        String age_range = intent.getStringExtra("age_range");

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
                    jsonCommentTransfer.put("sex", sex);
                    jsonCommentTransfer.put("age_range", age_range);

                    System.out.println("결과: " + nickname + " " + location_seq + " " + profile_img + " " + kakao_email + " " + sex + " " + age_range);

                    String jsonString = jsonCommentTransfer.toString();
                    String response = postTask.execute("auth2/signin/kakao", jsonString).get();

                    JSONObject jsonObject = new JSONObject(response);
                    String data = jsonObject.getString("data");
                    //data가 가진 값이 대괄호로 감싸여 있으니까 array
                    JSONArray jsonArray = new JSONArray(data);
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject subJsonObject = jsonArray.getJSONObject(i);
                        //subJsonObject는 data만 추출
                        user_id = String.valueOf(subJsonObject.getInt("user_id"));
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                intent.putExtra("userId", user_id);
                startActivity(intent);
            }
        });
    }
}