package com.example.chaebunchaebun;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private FirebaseFirestore mDataBase;
    private static final String TAG = "user";
    private SearchListAdapter searchListAdapter;
    EditText search_text;
    ListView search_list;
    ImageView search_back;
    TextView search_no_tv, search_suggest;
    ImageButton search_no_startbtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);

        search_text = (EditText)findViewById(R.id.search_text);
        search_suggest = (TextView) findViewById(R.id.search_suggest);
        search_list = (ListView) findViewById(R.id.jrv_search_list);
        search_back = (ImageView) findViewById(R.id.search_back);
        search_no_tv = (TextView) findViewById(R.id.search_no_tv);
        search_no_startbtn = (ImageButton) findViewById(R.id.search_no_startbtn);

        search_no_tv.setVisibility(View.GONE);
        search_no_startbtn.setVisibility(View.GONE);

        //search_suggest.setVisibility(View.GONE);
        //search_list.setVisibility(View.GONE);

        ArrayList<String> main_list = new ArrayList<String>();
        main_list.add("콩나물");
        main_list.add("토마토");
        main_list.add("호박");
        main_list.add("오이");
        main_list.add("브로콜리");

        ArrayAdapter<String> mainAdapter = new ArrayAdapter<String>(this, R.layout.searchlist_main_custom, R.id.searchlist_main_tv, main_list);
        search_list.setAdapter(mainAdapter);

        search_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            }
        });

        search_text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return true;
                }
                return false;
            }
        });

        search_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
