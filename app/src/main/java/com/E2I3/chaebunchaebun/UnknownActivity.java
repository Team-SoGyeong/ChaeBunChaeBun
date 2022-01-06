package com.E2I3.chaebunchaebun;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UnknownActivity extends AppCompatActivity {
    ImageView back;
    ImageButton donate;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.withdraw_unknown);

        back = (ImageView) findViewById(R.id.id_back);
        donate = (ImageButton) findViewById(R.id.btn_donate);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.foodbank1377.org/"));
                startActivity(intent);
            }
        });
    }
}
