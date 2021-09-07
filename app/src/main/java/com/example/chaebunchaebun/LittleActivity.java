package com.example.chaebunchaebun;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LittleActivity extends AppCompatActivity {
    ImageView back;
    ImageButton writing, share;
    String userId = null;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.withdraw_little);

        back = (ImageView) findViewById(R.id.id_back);
        writing = (ImageButton) findViewById(R.id.btn_writing);
        share = (ImageButton) findViewById(R.id.btn_share);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        writing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("userId", userId);
                WarningDialogFragment e  = WarningDialogFragment.getInstance();
                e.setArguments(args);
                e.show(getSupportFragmentManager(), "writing");
            }
        });
    }
}
