package com.example.chaebunchaebun;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MyInfoActivity extends AppCompatActivity {

    ImageView back;
    TextView service, privacy, location_info;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_myinfo);
        back = (ImageView) findViewById(R.id.id_back);
        service = (TextView) findViewById(R.id.service);
        privacy = (TextView) findViewById(R.id.privacy);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://irradiated-mapusaurus-27a.notion.site/34a2eb86c548473b8ea1c9aaa5a72217"));
                startActivity(intent);
            }
        });
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://irradiated-mapusaurus-27a.notion.site/7535a823135d437da69575e15cc49467"));
                startActivity(intent);
            }
        });
    }
}
