package com.example.chaebunchaebun;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SetLocationActivity extends AppCompatActivity {
    private static final int TAG_REQUEST_CODE = 1001;
    ImageButton search, btn_next;
    EditText set_location;
    String searchLocation = null;
    int locationCode = 0;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_location);

        search = (ImageButton) findViewById(R.id.ic_search);
        btn_next = (ImageButton) findViewById(R.id.btn_next);
        set_location = (EditText) findViewById(R.id.set_location);

        Intent intent = getIntent();
        String user_id = intent.getStringExtra("user_id");
        String kakao_email = intent.getStringExtra("kakao_email");
        String profile_img = intent.getStringExtra("profile_img");
        String nickname = intent.getStringExtra("nickname");
        String sex = intent.getStringExtra("sex");
        String age_range = intent.getStringExtra("age_range");

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("시작");
                searchLocation = set_location.getText().toString();
                Intent intent = new Intent(getApplicationContext(), SearchLocationActivity.class);
                intent.putExtra("searchLocation", searchLocation);
                startActivityForResult(intent, TAG_REQUEST_CODE);
                //set_location 값 저장하기
            }
        });

        set_location.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    System.out.println("시작");
                    searchLocation = set_location.getText().toString();
                    Intent intent = new Intent(getApplicationContext(), SearchLocationActivity.class);
                    intent.putExtra("searchLocation", searchLocation);
                    startActivityForResult(intent, TAG_REQUEST_CODE);
                    return true;
                }
                return false;
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SetStartActivity.class);
                intent.putExtra("user_id", user_id);
                intent.putExtra("kakao_email", kakao_email);
                intent.putExtra("profile_img", profile_img);
                intent.putExtra("nickname", nickname);
                intent.putExtra("location", searchLocation);
                intent.putExtra("locationCode", locationCode);
                intent.putExtra("sex", sex);
                intent.putExtra("age_range", age_range);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == TAG_REQUEST_CODE && resultCode == RESULT_OK) {
            searchLocation = data.getStringExtra("dong");
            locationCode = data.getIntExtra("code", 0);

            set_location.setText(searchLocation);
        }
    }
}