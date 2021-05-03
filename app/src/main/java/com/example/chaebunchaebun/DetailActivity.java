package com.example.chaebunchaebun;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    ListView comment_list;
    View header, footer;
    EditText comment_edit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_page);

        comment_list = (ListView)findViewById(R.id.jrv_comment_list);
        header = getLayoutInflater().inflate(R.layout.detail_page_header, null, false);
        footer = getLayoutInflater().inflate(R.layout.detail_page_footer, null, false);

        comment_list.addHeaderView(header);
        comment_list.addFooterView(footer);

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.detail_page);
        comment_list.setAdapter(adapter);

        setHeader();
        setFooter();
    }

    private void setHeader(){
        TextView title = (TextView)findViewById(R.id.header_title);
        TextView address = (TextView)findViewById(R.id.header_address);
        TextView vegetable = (TextView)findViewById(R.id.header_vegetable);
        TextView count = (TextView)findViewById(R.id.header_count);
        TextView date = (TextView) findViewById(R.id.header_date);
        TextView etc = (TextView) findViewById(R.id.header_ect);
    }

    private void setFooter(){
        comment_edit = (EditText)footer.findViewById(R.id.footer_edit);
        Button comment_btn = (Button)footer.findViewById(R.id.footer_btn);
    }
}
