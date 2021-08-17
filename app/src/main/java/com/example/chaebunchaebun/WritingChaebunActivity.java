package com.example.chaebunchaebun;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class WritingChaebunActivity extends AppCompatActivity {
    ImageView back, writing, btn_back;
    TextView inputPerPrice;
    EditText inputTitle, inputContent, inputAmount, inputGetPrice, inputMemberNum, inputCall;
    Spinner date_spinner, amount_spinner;
    String date_arr[] = {"1일 전", "2일 전", "3일 전", "일주일 이내", "2주 이내"};
    String amount_arr[] ={"kg","g","개"};
    String str, amount_str;
    int categoryId;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writing_chaebun_frame);

        Intent intent = getIntent();
        categoryId = intent.getIntExtra("categoryId", 0);

        inputTitle = (EditText) findViewById(R.id.input_title);
        inputContent = (EditText) findViewById(R.id.input_content);
        inputAmount = (EditText) findViewById(R.id.input_amount);
        inputGetPrice = (EditText) findViewById(R.id.input_getPrice);
        inputMemberNum = (EditText) findViewById(R.id.input_memberNum);
        inputCall = (EditText) findViewById(R.id.input_call);

        inputPerPrice = (TextView) findViewById(R.id.input_per_price);
        if(!inputAmount.getText().toString().equals("") && !inputGetPrice.getText().toString().equals("") && !inputMemberNum.getText().toString().equals("")){
            String amount = inputAmount.getText().toString();
            String totalPrice = inputGetPrice.getText().toString();
            String people = inputMemberNum.getText().toString();
            int perPrice = ((Integer.parseInt(totalPrice) / Integer.parseInt(people)) * (Integer.parseInt(amount) / Integer.parseInt(people)));
            inputPerPrice.setText(String.valueOf(perPrice).toString());
        }

        back = (ImageView) findViewById(R.id.id_back);
        writing = (ImageView) findViewById(R.id.btn_next);
        btn_back = (ImageView) findViewById(R.id.btn_back);

        date_spinner = (Spinner) findViewById(R.id.date_spinner2);
        amount_spinner = (Spinner) findViewById(R.id.amount_spinner2);

        //스피너 이벤트
        ArrayAdapter<String> date_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, date_arr);
        date_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        date_spinner.setAdapter(date_adapter);

        date_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str = date_arr[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> amount_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, amount_arr);
        amount_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        amount_spinner.setAdapter(amount_adapter);

        amount_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                amount_str = amount_arr[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //버튼 이벤트
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        writing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putInt("categoryId", categoryId);
                args.putString("inputTitle", inputTitle.getText().toString());
                args.putString("inputContent", inputContent.getText().toString());
                args.putString("inputAmount", inputAmount.getText().toString());
                args.putString("inputGetPrice", inputGetPrice.getText().toString());
                args.putString("inputMemberNum", inputMemberNum.getText().toString());
                args.putString("inputCall", inputCall.getText().toString());
                args.putString("inputBuyDate", str);
                args.putString("inputAmountStr", amount_str);
                WritingPopupDialogFragment e = WritingPopupDialogFragment.getInstance();
                e.setArguments(args);
                e.show(getSupportFragmentManager(), WritingPopupDialogFragment.TAG_EVENT_DIALOG);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
