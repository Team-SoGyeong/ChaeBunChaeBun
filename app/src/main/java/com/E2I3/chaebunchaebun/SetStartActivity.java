package com.E2I3.chaebunchaebun;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SetStartActivity  extends AppCompatActivity {
    String user_id = "86";

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding_start);
//        user_id = getIntent().getStringExtra("userId");


        moveMain(1);    //1초 후 main activity 로 넘어감
    }

    private void moveMain(int sec) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), OnboardingFirstActivity.class);
                intent.putExtra("userId", user_id);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                finish();    //현재 액티비티 종료
            }
        }, 3000 * sec); // sec초 정도 딜레이를 준 후 시작
    }
}