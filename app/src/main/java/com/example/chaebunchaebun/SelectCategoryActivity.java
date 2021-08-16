package com.example.chaebunchaebun;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class SelectCategoryActivity extends AppCompatActivity{
    boolean flag = false;
    ImageButton btn_onion, btn_etc, btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.writing_category));

        btn_onion = (ImageButton) findViewById(R.id.btn_onion);
        btn_etc = (ImageButton) findViewById(R.id.btn_etc);
        btn_next = (ImageButton) findViewById(R.id.btn_next);

        btn_onion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_onion.setImageResource(R.drawable.btn_select_onion);
            }
        });
        btn_etc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = true;
                btn_etc.setImageResource(R.drawable.btn_select_etc);
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("flag: "+flag);
                Intent intent;
                if(flag == true){
                    intent = new Intent(getApplicationContext(), WritingEtcChaebunActivity.class);
                }
                else{
                    intent = new Intent(getApplicationContext(), WritingChaebunActivity.class);
                }
                startActivity(intent);
            }
        });

    }
}