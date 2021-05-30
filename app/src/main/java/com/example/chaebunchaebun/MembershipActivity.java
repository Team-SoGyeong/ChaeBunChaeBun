package com.example.chaebunchaebun;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MembershipActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.choose_membership));

        Intent intent = getIntent();
        String id = intent.getStringExtra("ID");

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Button edit = (Button) findViewById(R.id.btn_change);
        Button delete = (Button) findViewById(R.id.btn_withdrawal);

        edit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), EditUserActivity.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WithdrawActivity.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });
    }
}
