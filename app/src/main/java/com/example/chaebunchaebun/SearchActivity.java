package com.example.chaebunchaebun;
import android.content.Intent;
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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "user";

    private RecyclerView searchList;
    private ArrayList<SearchListItem> searchListItems;
    private SearchListAdapter searchListAdapter;
    private LinearLayoutManager sLayoutManager;

    EditText search_text;
    ListView search_list;
    ImageView search_back;
    TextView search_no_tv, search_suggest;
    ImageButton search_no_startbtn;
    int locationcode = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);

        Intent intent = getIntent();
        locationcode = intent.getIntExtra("locationCode", 0);

        search_text = (EditText)findViewById(R.id.search_text);
        search_suggest = (TextView) findViewById(R.id.search_suggest);
        search_list = (ListView) findViewById(R.id.jrv_search_list);
        search_back = (ImageView) findViewById(R.id.search_back);
        search_no_tv = (TextView) findViewById(R.id.search_no_tv);
        search_no_startbtn = (ImageButton) findViewById(R.id.search_no_startbtn);
        searchList = (RecyclerView) findViewById(R.id.main_search_recyclerview);

        search_no_tv.setVisibility(View.GONE);
        search_no_startbtn.setVisibility(View.GONE);

        searchListItems = new ArrayList<SearchListItem>();

        //search_suggest.setVisibility(View.GONE);
        //search_list.setVisibility(View.GONE);

        ArrayList<String> main_list = new ArrayList<String>();
        main_list.add("콩나물");
        main_list.add("토마토");
        main_list.add("호박");
        main_list.add("오이");
        main_list.add("브로콜리");

        ArrayAdapter<String> mainAdapter = new ArrayAdapter<String>(this, R.layout.custom_searchlist_main, R.id.searchlist_main_tv, main_list);
        search_list.setAdapter(mainAdapter);

        search_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String recommend = main_list.get(position);
                search_text.setText(recommend);
                search_list.setVisibility(View.GONE);
                setSearchList(recommend, locationcode);
                if(searchListItems.isEmpty()){
                    search_no_tv.setVisibility(View.VISIBLE);
                    search_no_startbtn.setVisibility(View.VISIBLE);
                    search_suggest.setVisibility(View.GONE);
                    searchList.setVisibility(View.GONE);
                } else {
                    search_no_tv.setVisibility(View.GONE);
                    search_no_startbtn.setVisibility(View.GONE);
                    search_suggest.setVisibility(View.GONE);
                    sLayoutManager = new LinearLayoutManager(getApplicationContext());
                    searchList.setLayoutManager(sLayoutManager);
                    searchListAdapter = new SearchListAdapter(searchListItems);
                    searchList.setAdapter(searchListAdapter);
                }
            }
        });

        search_text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if(keyCode == KeyEvent.KEYCODE_ENTER) {
                    search_list.setVisibility(View.GONE);
                    String searchText = search_text.getText().toString();
                    setSearchList(searchText, locationcode);
                    if(searchListItems.isEmpty()){
                        search_no_tv.setVisibility(View.VISIBLE);
                        search_no_startbtn.setVisibility(View.VISIBLE);
                        search_suggest.setVisibility(View.GONE);
                        searchList.setVisibility(View.GONE);
                    } else {
                        search_no_tv.setVisibility(View.GONE);
                        search_no_startbtn.setVisibility(View.GONE);
                        search_suggest.setVisibility(View.GONE);
                        sLayoutManager = new LinearLayoutManager(getApplicationContext());
                        searchList.setLayoutManager(sLayoutManager);
                        searchListAdapter = new SearchListAdapter(searchListItems);
                        searchList.setAdapter(searchListAdapter);
                        searchListAdapter.setOnItemClickListener(new SearchListAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int pos) {
                                int postId = (int) view.getTag();
                                Bundle articleBundle = new Bundle();
                                articleBundle.putString("postId", String.valueOf(postId));
                                FragmentTransaction articleTransaction = getSupportFragmentManager().beginTransaction();
                                ArticleFragment articleFragment = new ArticleFragment();
                                articleFragment.setArguments(articleBundle);
                                articleTransaction.replace(R.id.bottom_frame, articleFragment);
                                articleTransaction.addToBackStack(null);
                                articleTransaction.commit();
                            }
                        });
                    }
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

    public void setSearchList(String searchText, int locationcode) {
        PostTask searchLocationTask = new PostTask();
        searchListItems.clear();
        try {
            String response = searchLocationTask.execute("home/" + locationcode +"/" + searchText, searchText).get();
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject subJsonObject = jsonArray.getJSONObject(i);
                String title = subJsonObject.getString("title");
                String buyDate = subJsonObject.getString("buy_date");
                String people = subJsonObject.getString("members");
                String price = subJsonObject.getString("per_price");
                int isAuth = subJsonObject.getInt("isAuth");
                int postId = subJsonObject.getInt("post_id");

                System.out.println(title + " " + buyDate + " " + people + " " + price + " " + isAuth);

                searchListItems.add(new SearchListItem(title, buyDate, people, price, isAuth, postId));
            }
        } catch(JSONException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
