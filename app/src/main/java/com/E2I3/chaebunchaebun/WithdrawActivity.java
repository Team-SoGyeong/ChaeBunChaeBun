package com.E2I3.chaebunchaebun;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WithdrawActivity extends AppCompatActivity {
    ImageView back;
    LinearLayout little, unknown, manner, etc;
    ImageButton withdraw;

    ImageButton check_little, check_unknown, check_no_manner, check_etc;
    TextView text_little, text_unknown, text_no_manner, text_etc, service;

    String userId, nickname;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_withdraw);

        Intent intent = getIntent();
        this.userId = intent.getStringExtra("userId");
        this.nickname = intent.getStringExtra("nickname");

        back = (ImageView) findViewById(R.id.id_back);
        little = (LinearLayout) findViewById(R.id.little);
        unknown = (LinearLayout) findViewById(R.id.unknown);
        manner = (LinearLayout) findViewById(R.id.no_manner);
        etc = (LinearLayout) findViewById(R.id.etc);
        withdraw = (ImageButton) findViewById(R.id.btn_withdraw);

        check_little = (ImageButton) findViewById(R.id.check_little);
        text_little = (TextView) findViewById(R.id.text_little);
        check_unknown = (ImageButton) findViewById(R.id.check_unknown);
        text_unknown = (TextView) findViewById(R.id.text_unknown);
        check_no_manner = (ImageButton) findViewById(R.id.check_no_manner);
        text_no_manner = (TextView) findViewById(R.id.text_no_manner);
        check_etc = (ImageButton) findViewById(R.id.check_etc);
        text_etc = (TextView) findViewById(R.id.text_etc);
        service = (TextView) findViewById(R.id.service);

        service.setText(nickname + "님.. 탈퇴하시려구요?");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        little.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_little.setImageResource(R.drawable.checkbox_on);
                text_little.setTextColor(Color.parseColor("#036635"));
                check_unknown.setImageResource(R.drawable.checkbox_off);
                text_unknown.setTextColor(Color.parseColor("#999999"));
                check_no_manner.setImageResource(R.drawable.checkbox_off);
                text_no_manner.setTextColor(Color.parseColor("#999999"));
                check_etc.setImageResource(R.drawable.checkbox_off);
                text_etc.setTextColor(Color.parseColor("#999999"));
                Intent intentLittle = new Intent(getApplicationContext(), LittleActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intentLittle);
            }
        });
        unknown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_little.setImageResource(R.drawable.checkbox_off);
                text_little.setTextColor(Color.parseColor("#999999"));
                check_unknown.setImageResource(R.drawable.checkbox_on);
                text_unknown.setTextColor(Color.parseColor("#036635"));
                check_no_manner.setImageResource(R.drawable.checkbox_off);
                text_no_manner.setTextColor(Color.parseColor("#999999"));
                check_etc.setImageResource(R.drawable.checkbox_off);
                text_etc.setTextColor(Color.parseColor("#999999"));
                Intent intentUnknown = new Intent(getApplicationContext(), UnknownActivity.class);
                startActivity(intentUnknown);
            }
        });
        manner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_little.setImageResource(R.drawable.checkbox_off);
                text_little.setTextColor(Color.parseColor("#999999"));
                check_unknown.setImageResource(R.drawable.checkbox_off);
                text_unknown.setTextColor(Color.parseColor("#999999"));
                check_no_manner.setImageResource(R.drawable.checkbox_on);
                text_no_manner.setTextColor(Color.parseColor("#036635"));
                check_etc.setImageResource(R.drawable.checkbox_off);
                text_etc.setTextColor(Color.parseColor("#999999"));
                Intent intentNoManner = new Intent(getApplicationContext(), NoMannerActivity.class);
                intentNoManner.putExtra("userId", userId);
                startActivity(intentNoManner);
            }
        });
        etc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_little.setImageResource(R.drawable.checkbox_off);
                text_little.setTextColor(Color.parseColor("#999999"));
                check_unknown.setImageResource(R.drawable.checkbox_off);
                text_unknown.setTextColor(Color.parseColor("#999999"));
                check_no_manner.setImageResource(R.drawable.checkbox_off);
                text_no_manner.setTextColor(Color.parseColor("#999999"));
                check_etc.setImageResource(R.drawable.checkbox_on);
                text_etc.setTextColor(Color.parseColor("#036635"));
                Intent intent = new Intent(getApplicationContext(), EtcActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("userId", userId);
                WithdrawDialogFragment e = WithdrawDialogFragment.getInstance();
                e.setArguments(args);
                e.show(getSupportFragmentManager(), WithdrawDialogFragment.TAG_EVENT_DIALOG);
            }
        });
    }
}