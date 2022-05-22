package com.E2I3.chaebunchaebun;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SetLocationActivity extends AppCompatActivity {
    private static final int TAG_REQUEST_CODE = 1001;
    ImageButton btn_next;
    EditText set_location, search;
    String searchLocation = null;
    boolean flag = false;
    int locationCode = 0;

    private TextView toastText;
    private Toast toast;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_location);
        LayoutInflater inflater = getLayoutInflater();
        View customToast = inflater.inflate(R.layout.custom_report_toast, (ViewGroup) findViewById(R.id.custom_toast_layout));

        toastText = (TextView) customToast.findViewById(R.id.custom_toast_text);
        toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(customToast);

        search = (EditText) findViewById(R.id.set_location);
        btn_next = (ImageButton) findViewById(R.id.btn_next);
        set_location = (EditText) findViewById(R.id.set_location);

        Intent intent = getIntent();
        Long kakao_id = intent.getLongExtra("kakao_id",0);
        String user_id = intent.getStringExtra("user_id");
        String kakao_email = intent.getStringExtra("kakao_email");
        String profile_img = intent.getStringExtra("profile_img");
        String set_profileImage = intent.getStringExtra("set_profileImage");
        String nickname = intent.getStringExtra("nickname");
        String sex = intent.getStringExtra("sex");
        String age_range = intent.getStringExtra("age_range");

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == false){
                    System.out.println("다이얼로그 뜰 위치");
                    flag = true;
                }
                else{
                    System.out.println("시작");
                    searchLocation = set_location.getText().toString();
                    Intent intent = new Intent(getApplicationContext(), SearchLocationActivity.class);
                    intent.putExtra("searchLocation", searchLocation);
                    startActivityForResult(intent, TAG_REQUEST_CODE);
                }
            }
        });

        set_location.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    System.out.println("시작");
                    searchLocation = set_location.getText().toString();
                    Intent intent = new Intent(getApplicationContext(), SearchLocationActivity.class);
                    intent.putExtra("searchLocation", searchLocation);
                    startActivityForResult(intent, TAG_REQUEST_CODE);
                    return true;
                }
                return false;
            }
        });

        set_location.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String getLocation = set_location.getText().toString();
                if(getLocation.length() > 0){
                    set_location.setBackgroundResource(R.drawable.profile_edittext_green);
                    btn_next.setImageResource(R.drawable.btn_signup_next);
                    btn_next.setClickable(true);

                    btn_next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(locationCode == 0) {
                                toastText.setText("주소가 선택되지 않았어요!");
                                toast.show();
                            } else {
                                Intent intent = new Intent(getApplicationContext(), SetStartActivity.class);
                                intent.putExtra("kakao_id", kakao_id);
                                intent.putExtra("user_id", user_id);
                                intent.putExtra("kakao_email", kakao_email);
                                intent.putExtra("profile_img", profile_img);
                                intent.putExtra("set_profileImage", set_profileImage);
                                intent.putExtra("nickname", nickname);
                                intent.putExtra("location", searchLocation);
                                intent.putExtra("locationCode", locationCode);
                                intent.putExtra("sex", sex);
                                intent.putExtra("age_range", age_range);
                                startActivity(intent);
                                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                            }
                        }
                    });
                } else {
                    set_location.setBackgroundResource(R.drawable.profile_edittext);
                    btn_next.setImageResource(R.drawable.btn_next);
                    btn_next.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == TAG_REQUEST_CODE && resultCode == RESULT_OK) {
            searchLocation = data.getStringExtra("dong");
            locationCode = data.getIntExtra("code", 0);

            set_location.setText(searchLocation);
        }
    }
}