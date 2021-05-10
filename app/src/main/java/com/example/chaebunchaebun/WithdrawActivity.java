package com.example.chaebunchaebun;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class WithdrawActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.withdrawal_membership));

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Button withdraw = (Button) findViewById(R.id.btn_withdraw);

        withdraw.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Toast tMsg = Toast.makeText(WithdrawActivity.this, "탈퇴되셨습니다.", Toast.LENGTH_SHORT);
                tMsg.show();
            }
        });

    }
}
