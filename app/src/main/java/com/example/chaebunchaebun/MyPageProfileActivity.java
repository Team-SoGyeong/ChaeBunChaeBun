package com.example.chaebunchaebun;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MyPageProfileActivity extends AppCompatActivity {
    ImageView back, profile, addProfile;
    TextView info, logout, withdraw, nicknameLength, nicknameInvalid;
    EditText changeNickname;
    ImageButton nicknameClear, nicknameChangeBtn;
    String userId = "1";
    String nickname, profileImg = null;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_profile);

        getProfile();

        profile = (ImageView) findViewById(R.id.profile_image);
        addProfile = (ImageView) findViewById(R.id.profile_addImage);
        changeNickname = (EditText) findViewById(R.id.profile_nickname);
        nicknameLength = (TextView) findViewById(R.id.profile_nickname_length);
        nicknameClear = (ImageButton) findViewById(R.id.profile_nickname_clear);
        nicknameInvalid = (TextView) findViewById(R.id.profile_invaild);
        nicknameChangeBtn = (ImageButton) findViewById(R.id.profile_btnChange);
        back = (ImageView) findViewById(R.id.id_back);
        info = (TextView) findViewById(R.id.profile_info);
        logout = (TextView) findViewById(R.id.profile_logout);
        withdraw = (TextView) findViewById(R.id.profile_withdraw);

        profile.setBackground(new ShapeDrawable(new OvalShape()));
        profile.setClipToOutline(true);
        Glide.with(getApplicationContext()).load(this.profileImg).into(profile);

        changeNickname.setText(this.nickname);
        nicknameLength.setText(this.nickname.length() + "/10");

        changeNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String getNickname = changeNickname.getText().toString();
                if(getNickname.equals(nickname)){
                    changeNickname.setBackgroundResource(R.drawable.profile_edittext);
                    nicknameLength.setText(getNickname.length() + "/10");
                    nicknameInvalid.setTextColor(Color.parseColor("#9A9792"));
                    nicknameChangeBtn.setImageResource(R.drawable.group_811);
                    nicknameChangeBtn.setClickable(false);
                } else {
                    if(getNickname.length() <= 10 && getNickname.length() > 0){
                        changeNickname.setBackgroundResource(R.drawable.profile_edittext_green);
                        nicknameLength.setText(getNickname.length() + "/10");
                        nicknameInvalid.setTextColor(Color.parseColor("#9A9792"));
                        nicknameChangeBtn.setImageResource(R.drawable.group_812);
                        nicknameChangeBtn.setClickable(true);
                    } else {
                        changeNickname.setBackgroundResource(R.drawable.profile_edittext_red);
                        nicknameLength.setText(getNickname.length() + "/10");
                        nicknameInvalid.setTextColor(Color.parseColor("#BE1700"));
                        nicknameChangeBtn.setImageResource(R.drawable.group_813);
                        nicknameChangeBtn.setClickable(false);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        nicknameClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeNickname.setText("");
            }
        });

        nicknameChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nickname = changeNickname.getText().toString();
                int user = Integer.parseInt(userId);
                setProfile(nickname, profileImg, user);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyInfoActivity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogoutDialogFragment e = LogoutDialogFragment.getInstance();
                e.show(getSupportFragmentManager(), LogoutDialogFragment.TAG_EVENT_DIALOG);
            }
        });

        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WithdrawActivity.class);
                startActivity(intent);
            }
        });

    }

    public void getProfile() {
        String resultText = "[NULL]";

        try {
            resultText = new GetTask("mypage/profile/" + this.userId).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObject = new JSONObject(resultText);
            String data = jsonObject.getString("data");
            JSONArray jsonArray = new JSONArray(data);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject subJsonObject = jsonArray.getJSONObject(i);
                this.nickname = subJsonObject.getString("nickname");
                this.profileImg = subJsonObject.getString("img");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setProfile(String nickname, String profileImg, int userId) {
        PutTask profileTask = new PutTask();
        JSONObject jsonProfileTransfer = new JSONObject();

        try {
            jsonProfileTransfer.put("nickname", nickname);
            jsonProfileTransfer.put("profile_img", profileImg);
            jsonProfileTransfer.put("user_id", userId);
            String jsonString = jsonProfileTransfer.toString();
            profileTask.execute("mypage/profile", jsonString);
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
