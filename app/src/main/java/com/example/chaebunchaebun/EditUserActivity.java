package com.example.chaebunchaebun;

import android.app.AppComponentFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class EditUserActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.edit_user_info));

        Button btn_editInfo = (Button) findViewById(R.id.btn_editInfo);
        btn_editInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast tMsg = Toast.makeText(EditUserActivity.this, "수정되었습니다.", Toast.LENGTH_SHORT);
                tMsg.show();
            }
        });
    }
}
