package com.example.chaebunchaebun;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SearchLocationActivity extends AppCompatActivity {
    ImageButton select;
    ImageView back;
    EditText search;

    TextView location;
    LinearLayout result;
    TextView location_nonlist;
    ListView locationList;

    String location_item = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.set_search_location));

        back = (ImageView) findViewById(R.id.search_back);
        search = (EditText) findViewById(R.id.search_text);
        select = (ImageButton) findViewById(R.id.location_okbtn);

        location = (TextView) findViewById(R.id.get_location);
        result = (LinearLayout) findViewById(R.id.search_result);
        location_nonlist = (TextView) findViewById(R.id.location_nonlist);
        locationList = (ListView) findViewById(R.id.location_list);

        location_nonlist.setVisibility(View.GONE);
        result.setVisibility(View.GONE);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(location_item == null){
                    location_nonlist.setVisibility(View.VISIBLE);
                    result.setVisibility(View.GONE);
                }else{
                    location_nonlist.setVisibility(View.GONE);
                    result.setVisibility(View.VISIBLE);
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SetLocationActivity.class);
                startActivity(intent);
            }
        });

    }
}
