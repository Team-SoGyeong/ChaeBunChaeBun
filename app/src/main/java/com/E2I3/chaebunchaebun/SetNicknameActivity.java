package com.E2I3.chaebunchaebun;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

public class SetNicknameActivity extends AppCompatActivity {
    ImageButton btn_next;
    EditText set_nickname;
    String nickname = "";
    TextView nickname_invaild;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_nickname);
        set_nickname = (EditText) findViewById(R.id.set_nickname);
        nickname_invaild = (TextView) findViewById(R.id.set_invaild);
        btn_next = (ImageButton) findViewById(R.id.btn_next);

        Intent intent = getIntent();
        String user_id = intent.getStringExtra("user_id");
        String kakao_email = intent.getStringExtra("kakao_email");
        String profile_img = intent.getStringExtra("profile_img");
        String set_profileImage = intent.getStringExtra("set_profileImage");
        String sex = intent.getStringExtra("sex");
        String age_range = intent.getStringExtra("age_range");


        btn_next.setClickable(false);
        set_nickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String getNickname = set_nickname.getText().toString();

                if (getNickname.length() <= 10 && getNickname.length() > 0) {
                    set_nickname.setBackgroundResource(R.drawable.profile_edittext_green);
                    nickname_invaild.setTextColor(Color.rgb(154, 151, 146));
                    btn_next.setImageResource(R.drawable.btn_signup_next);
                    btn_next.setClickable(true);
                    nickname = getNickname;
                } else {
                    set_nickname.setBackgroundResource(R.drawable.profile_edittext_red);
                    nickname_invaild.setTextColor(Color.rgb(190, 23, 0));
                    btn_next.setImageResource(R.drawable.btn_next);
                    btn_next.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        set_nickname.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int i, int i1, Spanned spanned, int i2, int i3) {
                Pattern ps = Pattern.compile("^[a-zA-Z가-힣ㄱ-ㅎㅏ-ㅣ\\u318D\\u119E\\u11A2\\u2022\\u2025a\\u00B7\\uFE55]+$");
                if(source.equals("") || ps.matcher(source).matches()){
                    return source;
                }
                return "";
            }
        }, new InputFilter.LengthFilter(10)});

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = " ";
                try {
                    System.out.println("btn때 nickname: " + nickname);
                    result = new GetTask("auth2/signin/kakao/" + nickname).execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String data = jsonObject.getString("data");
                    JSONObject subJsonObject = new JSONObject(data);
                    Boolean usable = subJsonObject.getBoolean("isUsable");

                    System.out.println("Usable?: " + usable);
                    if(usable == true){
                        Intent intent = new Intent(getApplicationContext(), SetLocationActivity.class);
                        intent.putExtra("user_id", user_id);
                        intent.putExtra("kakao_email", kakao_email);
                        intent.putExtra("profile_img", profile_img);
                        intent.putExtra("set_profileImage", set_profileImage);
                        intent.putExtra("nickname", nickname);
                        intent.putExtra("sex", sex);
                        intent.putExtra("age_range", age_range);
                        startActivity(intent);
                        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                    }else{
                        Toast myToast = Toast.makeText(getApplicationContext(),"중복된 닉네임이 존재합니다!", Toast.LENGTH_SHORT);
                        myToast.show();
                        set_nickname.setBackgroundResource(R.drawable.profile_edittext_red);
                        nickname_invaild.setTextColor(Color.rgb(190, 23, 0));
                        btn_next.setImageResource(R.drawable.btn_next);
                        btn_next.setClickable(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
