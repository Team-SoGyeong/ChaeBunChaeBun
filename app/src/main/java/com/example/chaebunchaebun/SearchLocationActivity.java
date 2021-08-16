package com.example.chaebunchaebun;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.load.resource.drawable.DrawableResource;

import java.util.ArrayList;
import java.util.List;

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

        Intent intent = getIntent();
        String searchLocation = intent.getStringExtra("searchLocation");
        search.setText(searchLocation);
        location_item = search.getText().toString();

        List<String> lc_list = new ArrayList<>();
        lc_list.add("서울특별시 성북구 돈암동");
        lc_list.add("서울특별시 성북구 동선동");
        lc_list.add("서울특별시 성북구 돈암 1동");
        lc_list.add("서울특별시 성북구 돈암 2동");

        ArrayAdapter<String> adpater = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lc_list);

        if(location_item == null){
            location_nonlist.setVisibility(View.VISIBLE);
            locationList.setVisibility(View.GONE);
            result.setVisibility(View.GONE);
            Toast.makeText(SearchLocationActivity.this, "location item: null", Toast.LENGTH_SHORT).show();
            location_item = "Not null";
        }else{
            location_nonlist.setVisibility(View.GONE);
            locationList.setVisibility(View.VISIBLE);
            result.setVisibility(View.VISIBLE);
            Toast.makeText(SearchLocationActivity.this, "location item: not null", Toast.LENGTH_SHORT).show();

            locationList.setAdapter(adpater);

            locationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    String data = (String) adapterView.getItemAtPosition(position);
                    location.setText(data);
                    view.setBackground(ContextCompat.getDrawable(SearchLocationActivity.this, R.drawable.green_box));
                }
            });

            location_item = null;
        }

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //기본 세팅
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
