package com.example.chaebunchaebun;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private FirebaseFirestore mDataBase;
    private static final String TAG = "user";
    private MyAdapter myAdapter;
    EditText search_edit;
    ListView search_list;
    Button btn_search;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        mDataBase = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        String id = intent.getStringExtra("ID");

        search_edit = (EditText)findViewById(R.id.search_text);
        search_list = (ListView) findViewById(R.id.jrv_search_list);
        btn_search = (Button) findViewById(R.id.btn_search);

        search_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                int count = ((MyItem)myAdapter.getItem(position)).getCount();
                System.out.println(count);

                Intent content = new Intent(getApplicationContext(), DetailActivity.class);
                content.putExtra("count", count);
                content.putExtra("ID", id);
                System.out.println("Search ID: " + id);
                startActivity(content);
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAdapter = new MyAdapter();
                String search = search_edit.getText().toString();
                System.out.println(search);
                dataSetting(search);
            }
        });
        
    }

    private void dataSetting(String search) {
        System.out.println("검색: " + search);
        mDataBase.collection("market")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            boolean flag = false;
                            for(QueryDocumentSnapshot document : task.getResult()){
                                String title = document.getData().get("title").toString();

                                if(title.contains(search)){
                                    flag = true;
                                    String s_count = document.getData().get("count").toString();
                                    int count = Integer.parseInt(s_count);
                                    String nickname = document.getData().get("nickname").toString();
                                    String location = document.getData().get("location").toString();

                                    myAdapter.addItem(count, title, nickname, location);
                                    search_list.setAdapter(myAdapter);
                                }
                            }
                            if(flag == false) {
                                Toast.makeText(getApplicationContext(), "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}
