package com.example.chaebunchaebun;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WithdrawActivity extends AppCompatActivity {
    ImageView back;
    LinearLayout little, unknown, manner, etc;
    ImageButton withdraw;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_withdraw);

        back = (ImageView) findViewById(R.id.id_back);
        little = (LinearLayout) findViewById(R.id.little);
        unknown = (LinearLayout) findViewById(R.id.unknown);
        manner = (LinearLayout) findViewById(R.id.no_manner);
        etc = (LinearLayout) findViewById(R.id.etc);
        withdraw = (ImageButton) findViewById(R.id.btn_withdraw);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        little.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LittleActivity.class);
                startActivity(intent);
            }
        });
        unknown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UnknownActivity.class);
                startActivity(intent);
            }
        });
        manner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NoMannerActivity.class);
                startActivity(intent);
            }
        });
        etc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EtcActivity.class);
                startActivity(intent);
            }
        });
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WithdrawPopupActivity.class);
                startActivity(intent);
            }
        });
    }
}