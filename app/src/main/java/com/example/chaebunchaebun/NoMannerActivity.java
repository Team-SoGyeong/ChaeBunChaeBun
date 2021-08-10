package com.example.chaebunchaebun;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NoMannerActivity extends AppCompatActivity {
    ImageView back;
    ImageButton report, inquire;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.withdraw_no_manner);

        back = (ImageView) findViewById(R.id.id_back);
        report = (ImageButton) findViewById(R.id.btn_report);
        inquire = (ImageButton) findViewById(R.id.btn_inquire);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}