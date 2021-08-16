package com.example.chaebunchaebun;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NoMannerActivity extends AppCompatActivity {
    ImageView back;
    ImageButton report, inquire;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.withdraw_no_manner);

        back = (ImageView) findViewById(R.id.id_back);
        report = (ImageButton) findViewById(R.id.btn_report);
        inquire = (ImageButton) findViewById(R.id.btn_inquire);

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArticleReportDialogFragment e = ArticleReportDialogFragment.getInstance();
                e.show(getSupportFragmentManager(), ArticleReportDialogFragment.TAG_EVENT_DIALOG);
            }
        });
        inquire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("plain/text");
                String[] address = {"email@address.com"};
                email.putExtra(Intent.EXTRA_EMAIL, address);
                email.putExtra(Intent.EXTRA_SUBJECT, "[채분채분 문의하기]");
                email.putExtra(Intent.EXTRA_TEXT, "문의할 내용을 적어주세요!");
                startActivity(email);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}