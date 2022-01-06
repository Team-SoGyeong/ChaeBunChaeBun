package com.E2I3.chaebunchaebun;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class SelectCategoryActivity extends AppCompatActivity{
    boolean flag = false;
    int cateoryid = 0;
    ImageButton btn_onion, btn_garlic, btn_greenonion, btn_carrot, btn_mushroom, btn_greenvege, btn_cabbage, btn_radish, btn_potato, btn_sweetpotato, btn_etc, btn_next;
    ImageView back;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.writing_category));

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

        btn_onion = (ImageButton) findViewById(R.id.btn_onion);
        btn_garlic = (ImageButton) findViewById(R.id.btn_garlic);
        btn_greenonion = (ImageButton) findViewById(R.id.btn_greenonion);
        btn_carrot = (ImageButton) findViewById(R.id.btn_carrot);
        btn_mushroom = (ImageButton) findViewById(R.id.btn_mushroom);
        btn_greenvege = (ImageButton) findViewById(R.id.btn_greenvege);
        btn_cabbage = (ImageButton) findViewById(R.id.btn_cabbage);
        btn_radish = (ImageButton) findViewById(R.id.btn_radish);
        btn_potato = (ImageButton) findViewById(R.id.btn_potato);
        btn_sweetpotato = (ImageButton) findViewById(R.id.btn_sweetpotato);
        btn_etc = (ImageButton) findViewById(R.id.btn_etc);
        btn_next = (ImageButton) findViewById(R.id.btn_next);
        back = (ImageView) findViewById(R.id.id_back);

        btn_onion.setOnClickListener(new View.OnClickListener() {
            boolean select = true;
            @Override
            public void onClick(View v) {
                if(select == true){
                    btn_onion.setImageResource(R.drawable.btn_select_onion);
                    btn_garlic.setImageResource(R.drawable.btn_garlic);
                    btn_greenonion.setImageResource(R.drawable.btn_greenonion);
                    btn_carrot.setImageResource(R.drawable.btn_carrot);
                    btn_mushroom.setImageResource(R.drawable.btn_mushroom);
                    btn_greenvege.setImageResource(R.drawable.btn_greenvege);
                    btn_cabbage.setImageResource(R.drawable.btn_cabbage);
                    btn_radish.setImageResource(R.drawable.btn_radish);
                    btn_potato.setImageResource(R.drawable.btn_potato);
                    btn_sweetpotato.setImageResource(R.drawable.btn_sweetpotato);
                    btn_etc.setImageResource(R.drawable.btn_etc);
                    select = false;
                    flag = false;
                    cateoryid = 1;
                }
                else{
                    btn_onion.setImageResource(R.drawable.btn_onion);
                    select = true;
                }
            }
        });
        btn_garlic.setOnClickListener(new View.OnClickListener() {
            boolean select = true;
            @Override
            public void onClick(View v) {
                if(select == true){
                    btn_onion.setImageResource(R.drawable.btn_onion);
                    btn_garlic.setImageResource(R.drawable.btn_select_garlic);
                    btn_greenonion.setImageResource(R.drawable.btn_greenonion);
                    btn_carrot.setImageResource(R.drawable.btn_carrot);
                    btn_mushroom.setImageResource(R.drawable.btn_mushroom);
                    btn_greenvege.setImageResource(R.drawable.btn_greenvege);
                    btn_cabbage.setImageResource(R.drawable.btn_cabbage);
                    btn_radish.setImageResource(R.drawable.btn_radish);
                    btn_potato.setImageResource(R.drawable.btn_potato);
                    btn_sweetpotato.setImageResource(R.drawable.btn_sweetpotato);
                    btn_etc.setImageResource(R.drawable.btn_etc);
                    select = false;
                    flag = false;
                    cateoryid = 2;
                }
                else{
                    btn_garlic.setImageResource(R.drawable.btn_garlic);
                    select = true;
                }
            }
        });
        btn_greenonion.setOnClickListener(new View.OnClickListener() {
            boolean select = true;
            @Override
            public void onClick(View v) {
                if(select == true){
                    btn_onion.setImageResource(R.drawable.btn_onion);
                    btn_garlic.setImageResource(R.drawable.btn_garlic);
                    btn_greenonion.setImageResource(R.drawable.btn_select_greenonion);
                    btn_carrot.setImageResource(R.drawable.btn_carrot);
                    btn_mushroom.setImageResource(R.drawable.btn_mushroom);
                    btn_greenvege.setImageResource(R.drawable.btn_greenvege);
                    btn_cabbage.setImageResource(R.drawable.btn_cabbage);
                    btn_radish.setImageResource(R.drawable.btn_radish);
                    btn_potato.setImageResource(R.drawable.btn_potato);
                    btn_sweetpotato.setImageResource(R.drawable.btn_sweetpotato);
                    btn_etc.setImageResource(R.drawable.btn_etc);
                    select = false;
                    flag = false;
                    cateoryid = 3;
                }
                else{
                    btn_greenonion.setImageResource(R.drawable.btn_greenonion);
                    select = true;
                }
            }
        });
        btn_carrot.setOnClickListener(new View.OnClickListener() {
            boolean select = true;
            @Override
            public void onClick(View v) {
                if(select == true){
                    btn_onion.setImageResource(R.drawable.btn_onion);
                    btn_garlic.setImageResource(R.drawable.btn_garlic);
                    btn_greenonion.setImageResource(R.drawable.btn_greenonion);
                    btn_carrot.setImageResource(R.drawable.btn_select_carrot);
                    btn_mushroom.setImageResource(R.drawable.btn_mushroom);
                    btn_greenvege.setImageResource(R.drawable.btn_greenvege);
                    btn_cabbage.setImageResource(R.drawable.btn_cabbage);
                    btn_radish.setImageResource(R.drawable.btn_radish);
                    btn_potato.setImageResource(R.drawable.btn_potato);
                    btn_sweetpotato.setImageResource(R.drawable.btn_sweetpotato);
                    btn_etc.setImageResource(R.drawable.btn_etc);
                    select = false;
                    flag = false;
                    cateoryid = 4;
                }
                else{
                    btn_carrot.setImageResource(R.drawable.btn_carrot);
                    select = true;
                }
            }
        });

        btn_mushroom.setOnClickListener(new View.OnClickListener() {
            boolean select = true;
            @Override
            public void onClick(View v) {
                if(select == true){
                    btn_onion.setImageResource(R.drawable.btn_onion);
                    btn_garlic.setImageResource(R.drawable.btn_garlic);
                    btn_greenonion.setImageResource(R.drawable.btn_greenonion);
                    btn_carrot.setImageResource(R.drawable.btn_carrot);
                    btn_mushroom.setImageResource(R.drawable.btn_select_mushroom);
                    btn_greenvege.setImageResource(R.drawable.btn_greenvege);
                    btn_cabbage.setImageResource(R.drawable.btn_cabbage);
                    btn_radish.setImageResource(R.drawable.btn_radish);
                    btn_potato.setImageResource(R.drawable.btn_potato);
                    btn_sweetpotato.setImageResource(R.drawable.btn_sweetpotato);
                    btn_etc.setImageResource(R.drawable.btn_etc);
                    select = false;
                    flag = false;
                    cateoryid = 5;
                }
                else{
                    btn_mushroom.setImageResource(R.drawable.btn_mushroom);
                    select = true;
                }
            }
        });
        btn_greenvege.setOnClickListener(new View.OnClickListener() {
            boolean select = true;
            @Override
            public void onClick(View v) {
                if(select == true){
                    btn_onion.setImageResource(R.drawable.btn_onion);
                    btn_garlic.setImageResource(R.drawable.btn_garlic);
                    btn_greenonion.setImageResource(R.drawable.btn_greenonion);
                    btn_carrot.setImageResource(R.drawable.btn_carrot);
                    btn_mushroom.setImageResource(R.drawable.btn_mushroom);
                    btn_greenvege.setImageResource(R.drawable.btn_select_greenvege);
                    btn_cabbage.setImageResource(R.drawable.btn_cabbage);
                    btn_radish.setImageResource(R.drawable.btn_radish);
                    btn_potato.setImageResource(R.drawable.btn_potato);
                    btn_sweetpotato.setImageResource(R.drawable.btn_sweetpotato);
                    btn_etc.setImageResource(R.drawable.btn_etc);
                    select = false;
                    flag = false;
                    cateoryid = 6;
                }
                else{
                    btn_greenvege.setImageResource(R.drawable.btn_greenvege);
                    select = true;
                }
            }
        });
        btn_cabbage.setOnClickListener(new View.OnClickListener() {
            boolean select = true;
            @Override
            public void onClick(View v) {
                if(select == true){
                    btn_onion.setImageResource(R.drawable.btn_onion);
                    btn_garlic.setImageResource(R.drawable.btn_garlic);
                    btn_greenonion.setImageResource(R.drawable.btn_greenonion);
                    btn_carrot.setImageResource(R.drawable.btn_carrot);
                    btn_mushroom.setImageResource(R.drawable.btn_mushroom);
                    btn_greenvege.setImageResource(R.drawable.btn_greenvege);
                    btn_cabbage.setImageResource(R.drawable.btn_select_cabbage);
                    btn_radish.setImageResource(R.drawable.btn_radish);
                    btn_potato.setImageResource(R.drawable.btn_potato);
                    btn_sweetpotato.setImageResource(R.drawable.btn_sweetpotato);
                    btn_etc.setImageResource(R.drawable.btn_etc);
                    select = false;
                    flag = false;
                    cateoryid = 7;
                }
                else{
                    btn_cabbage.setImageResource(R.drawable.btn_cabbage);
                    select = true;
                }
            }
        });
        btn_radish.setOnClickListener(new View.OnClickListener() {
            boolean select = true;
            @Override
            public void onClick(View v) {
                if(select == true){
                    btn_onion.setImageResource(R.drawable.btn_onion);
                    btn_garlic.setImageResource(R.drawable.btn_garlic);
                    btn_greenonion.setImageResource(R.drawable.btn_greenonion);
                    btn_carrot.setImageResource(R.drawable.btn_carrot);
                    btn_mushroom.setImageResource(R.drawable.btn_mushroom);
                    btn_greenvege.setImageResource(R.drawable.btn_greenvege);
                    btn_cabbage.setImageResource(R.drawable.btn_cabbage);
                    btn_radish.setImageResource(R.drawable.btn_select_radish);
                    btn_potato.setImageResource(R.drawable.btn_potato);
                    btn_sweetpotato.setImageResource(R.drawable.btn_sweetpotato);
                    btn_etc.setImageResource(R.drawable.btn_etc);
                    select = false;
                    flag = false;
                    cateoryid = 8;
                }
                else{
                    btn_radish.setImageResource(R.drawable.btn_radish);
                    select = true;
                }
            }
        });

        btn_potato.setOnClickListener(new View.OnClickListener() {
            boolean select = true;
            @Override
            public void onClick(View v) {
                if(select == true){
                    btn_onion.setImageResource(R.drawable.btn_onion);
                    btn_garlic.setImageResource(R.drawable.btn_garlic);
                    btn_greenonion.setImageResource(R.drawable.btn_greenonion);
                    btn_carrot.setImageResource(R.drawable.btn_carrot);
                    btn_mushroom.setImageResource(R.drawable.btn_mushroom);
                    btn_greenvege.setImageResource(R.drawable.btn_greenvege);
                    btn_cabbage.setImageResource(R.drawable.btn_cabbage);
                    btn_radish.setImageResource(R.drawable.btn_radish);
                    btn_potato.setImageResource(R.drawable.btn_select_potato);
                    btn_sweetpotato.setImageResource(R.drawable.btn_sweetpotato);
                    btn_etc.setImageResource(R.drawable.btn_etc);
                    select = false;
                    flag = false;
                    cateoryid = 9;
                }
                else{
                    btn_potato.setImageResource(R.drawable.btn_potato);
                    select = true;
                }
            }
        });
        btn_sweetpotato.setOnClickListener(new View.OnClickListener() {
            boolean select = true;
            @Override
            public void onClick(View v) {
                if(select == true){
                    btn_onion.setImageResource(R.drawable.btn_onion);
                    btn_garlic.setImageResource(R.drawable.btn_garlic);
                    btn_greenonion.setImageResource(R.drawable.btn_greenonion);
                    btn_carrot.setImageResource(R.drawable.btn_carrot);
                    btn_mushroom.setImageResource(R.drawable.btn_mushroom);
                    btn_greenvege.setImageResource(R.drawable.btn_greenvege);
                    btn_cabbage.setImageResource(R.drawable.btn_cabbage);
                    btn_radish.setImageResource(R.drawable.btn_radish);
                    btn_potato.setImageResource(R.drawable.btn_potato);
                    btn_sweetpotato.setImageResource(R.drawable.btn_select_sweetpotato);
                    btn_etc.setImageResource(R.drawable.btn_etc);
                    select = false;
                    flag = false;
                    cateoryid = 10;
                }
                else{
                    btn_sweetpotato.setImageResource(R.drawable.btn_sweetpotato);
                    select = true;
                }
            }
        });


        btn_etc.setOnClickListener(new View.OnClickListener() {
            boolean select = true;
            @Override
            public void onClick(View v) {
                if(select == true){
                    btn_onion.setImageResource(R.drawable.btn_onion);
                    btn_garlic.setImageResource(R.drawable.btn_garlic);
                    btn_greenonion.setImageResource(R.drawable.btn_greenonion);
                    btn_carrot.setImageResource(R.drawable.btn_carrot);
                    btn_mushroom.setImageResource(R.drawable.btn_mushroom);
                    btn_greenvege.setImageResource(R.drawable.btn_greenvege);
                    btn_cabbage.setImageResource(R.drawable.btn_cabbage);
                    btn_radish.setImageResource(R.drawable.btn_radish);
                    btn_potato.setImageResource(R.drawable.btn_potato);
                    btn_sweetpotato.setImageResource(R.drawable.btn_sweetpotato);
                    btn_etc.setImageResource(R.drawable.btn_select_etc);
                    select = false;
                    flag = true;
                    cateoryid = 11;
                }
                else{
                    btn_etc.setImageResource(R.drawable.btn_etc);
                    select = true;
                    flag = false;
                }
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("flag: "+flag);
                Intent intent;
                if(flag == true && cateoryid != 0){
                    intent = new Intent(getApplicationContext(), WritingEtcChaebunActivity.class);
                    intent.putExtra("categoryId", cateoryid);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                }
                else if(flag == false && cateoryid != 0){
                    intent = new Intent(getApplicationContext(), WritingChaebunActivity.class);
                    intent.putExtra("categoryId", cateoryid);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}