package com.example.chaebunchaebun;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
    String receiveMsg;
    String city, gu, dong, code;

    ArrayList<String> lc_list;
    ArrayList<Integer> locationCode;
    ArrayAdapter<String> adpater;
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

        lc_list = new ArrayList<String>();
        locationCode = new ArrayList<Integer>();

        adpater = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lc_list);

        if(location_item == null){
            location_nonlist.setVisibility(View.VISIBLE);
            locationList.setVisibility(View.GONE);
            result.setVisibility(View.GONE);
            location.setText(location_item);
            Toast.makeText(SearchLocationActivity.this, "location item: null", Toast.LENGTH_SHORT).show();
            location_item = "Not null";
        }else{
            search.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                    if((keyCode == KeyEvent.KEYCODE_ENTER)) {
                        String change = search.getText().toString();
                        SearchLocationTask searchLocationTask = new SearchLocationTask();
                        searchLocationTask.execute("home/location/" + change, change);
                        return true;
                    }
                    return false;
                }
            });

            SearchLocationTask searchLocationTask = new SearchLocationTask();
            searchLocationTask.execute("home/location/" + searchLocation, searchLocation);

            locationList.setAdapter(adpater);
            location_nonlist.setVisibility(View.GONE);
            locationList.setVisibility(View.VISIBLE);
            result.setVisibility(View.VISIBLE);
            location.setText(location_item);
            Toast.makeText(SearchLocationActivity.this, "location item: not null", Toast.LENGTH_SHORT).show();

            locationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    String data = (String) adapterView.getItemAtPosition(position);
//                    location.setText(data);
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

    public class SearchLocationTask extends AsyncTask<String, Void, Void>{
        String str;
        String res_json;

        @Override
        protected Void doInBackground(String... params) {
            HttpURLConnection conn = null;
            try {
                URL url = new URL("http://3.37.243.188:8080/" + params[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(60000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true); // InputStream으로 서버로 부터 응답을 받겠다는 옵션.
                conn.setDoOutput(true); // OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.
                conn.setRequestProperty("Accept-Charset", "UTF-8"); // Accept-Charset 설정.
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Connection", "Keep-Alive");

                OutputStream os = conn.getOutputStream();
                os.write(params[1].getBytes("UTF-8"));
                os.flush();
                os.close();

                //BufferedReader br = null;
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = bufferedReader.readLine()) != null){
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();
                    Log.i("receiveMsg: ", receiveMsg);

                    bufferedReader.close();
                    JSONObject jsonObject = new JSONObject(receiveMsg);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for(int i = 0; i < jsonArray.length(); i++){
                        code = jsonArray.getJSONArray(i).get(0).toString();
                        city = jsonArray.getJSONArray(i).get(1).toString();
                        gu = jsonArray.getJSONArray(i).get(2).toString();
                        dong = jsonArray.getJSONArray(i).get(3).toString();

                        System.out.println(city + gu + dong);
                        lc_list.add(city + " " + gu + " " + dong);
                        locationCode.add(Integer.parseInt(code));
                    }
//                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                } else {
                    InputStreamReader tmp = new InputStreamReader(conn.getErrorStream(), "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = bufferedReader.readLine()) != null){
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();
                    Log.i("receiveMsg: ", receiveMsg);

                    bufferedReader.close();
//                br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
                }
            }  catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
