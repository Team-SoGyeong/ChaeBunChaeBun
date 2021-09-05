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
    int locationcode = 0;
    String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);

        Intent intent = getIntent();
        locationcode = intent.getIntExtra("locationCode", 0);
        userId = intent.getStringExtra("userId");

        Bundle myPostDetailBundle = new Bundle();
        myPostDetailBundle.putInt("locationCode", locationcode);
        myPostDetailBundle.putString("userId", userId);
        FragmentTransaction searchPostTransaction = getSupportFragmentManager().beginTransaction();
        SearchDetailFragment searchDetailFragment = new SearchDetailFragment();
        searchDetailFragment.setArguments(myPostDetailBundle);
        searchPostTransaction.replace(R.id.search_frame, searchDetailFragment);
        searchPostTransaction.commit();
    }
}
