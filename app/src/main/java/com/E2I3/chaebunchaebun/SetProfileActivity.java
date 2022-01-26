package com.E2I3.chaebunchaebun;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class SetProfileActivity extends AppCompatActivity {
    ImageView potato, carrot, onion, corn, tomato;
    ImageButton btn_next;
    String set_profileImage;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_profileimg);

        potato = (ImageView) findViewById(R.id.potato);
        carrot = (ImageView) findViewById(R.id.carrot);
        onion = (ImageView) findViewById(R.id.onion);
        corn = (ImageView) findViewById(R.id.corn);
        tomato = (ImageView) findViewById(R.id.tomato);
        btn_next = (ImageButton) findViewById(R.id.next);

        Intent intent = getIntent();
        String user_id = intent.getStringExtra("user_id");
        String kakao_email = intent.getStringExtra("kakao_email");
        String profile_img = intent.getStringExtra("profile_img");
        String sex = intent.getStringExtra("sex");
        String age_range = intent.getStringExtra("age_range");

        potato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_profileImage = "potato";
            }
        });
        carrot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_profileImage = "carrot";
            }
        });
        onion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_profileImage = "onion";
            }
        });
        corn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_profileImage = "corn";
            }
        });
        tomato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_profileImage = "tomato";
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String result = " ";
                try {
                    System.out.println("btn때 porfile: " + set_profileImage);
                    result = new GetTask("auth2/signin/kakao/" + set_profileImage).execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String data = jsonObject.getString("data");
                    JSONObject subJsonObject = new JSONObject(data);
                    Boolean usable = subJsonObject.getBoolean("isUsable");

                    System.out.println("Usable?: " + usable);
                    if(usable == true){
                        Intent intent = new Intent(getApplicationContext(), SetNicknameActivity.class);
                        intent.putExtra("user_id", user_id);
                        intent.putExtra("kakao_email", kakao_email);
                        intent.putExtra("profile_img", profile_img);
                        intent.putExtra("set_profileImage", set_profileImage);
                        intent.putExtra("sex", sex);
                        intent.putExtra("age_range", age_range);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
