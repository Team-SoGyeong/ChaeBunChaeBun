package com.example.chaebunchaebun;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class WritingActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.writingpage));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
