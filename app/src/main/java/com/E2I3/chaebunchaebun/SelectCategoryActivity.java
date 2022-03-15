package com.E2I3.chaebunchaebun;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

public class SelectCategoryActivity extends AppCompatActivity{
    boolean flag = false;
    int cateoryid = 0;
    static final String[] LIST_MENU = {"양파", "마늘", "파", "당근", "버섯", "배추", "무", "감자","고구마","다른 채소"} ;
    ImageButton btn_next;
    ImageView btn_back;
    String userId;
    int locationCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.writing_category));

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        locationCode = intent.getIntExtra("locationCode",0);
        btn_back = (ImageView) findViewById(R.id.btn_back);
        btn_next = (ImageButton) findViewById(R.id.btn_next);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, LIST_MENU) ;

        ListView listview = (ListView) findViewById(R.id.list_item) ;
        listview.setAdapter(adapter) ;

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                btn_next.setImageResource(R.drawable.writing_btn_next);
                String strText = (String) adapterView.getItemAtPosition(i) ;
                System.out.println("clickItem:" + strText);

                switch (strText){
                    case "양파":
                        flag = false;
                        cateoryid = 1;
                        break;
                    case "마늘":
                        flag = false;
                        cateoryid = 2;
                        break;
                    case "파":
                        flag = false;
                        cateoryid = 3;
                        break;
                    case "당근":
                        flag = false;
                        cateoryid = 4;
                        break;
                    case "버섯":
                        flag = false;
                        cateoryid = 5;
                        break;
                    case "배추":
                        flag = false;
                        cateoryid = 7;
                        break;
                    case "무":
                        flag = false;
                        cateoryid = 8;
                        break;
                    case "감자":
                        flag = false;
                        cateoryid = 9;
                        break;
                    case "고구마":
                        flag = false;
                        cateoryid = 10;
                        break;
                    case "다른 채소":
                        flag = true;
                        cateoryid = 11;
                        break;
                    default:
                        break;

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
                    intent.putExtra("locationCode",locationCode);
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
                else if(flag == false && cateoryid != 0){
                    intent = new Intent(getApplicationContext(), WritingChaebunActivity.class);
                    intent.putExtra("categoryId", cateoryid);
                    intent.putExtra("userId", userId);
                    intent.putExtra("locationCode",locationCode);
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);

    }
}