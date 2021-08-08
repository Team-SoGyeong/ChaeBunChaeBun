package com.example.chaebunchaebun;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WithdrawPopupActivity extends AppCompatActivity {
    ImageButton withdraw, cancel;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.withdraw_popup);

        withdraw = (ImageButton) findViewById(R.id.btn_withdraw);
        cancel = (ImageButton) findViewById(R.id.btn_cancel);

        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
