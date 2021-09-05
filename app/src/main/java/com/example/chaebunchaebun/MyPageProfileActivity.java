package com.example.chaebunchaebun;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutionException;

public class MyPageProfileActivity extends AppCompatActivity {
    private final int GET_GALLERY_PROFILE_IMAGE = 200;
    ImageView back, profile, addProfile;
    TextView info, logout, withdraw, nicknameLength, nicknameInvalid;
    EditText changeNickname;
    ImageButton nicknameClear, nicknameChangeBtn;
    String userId = null;
    String nickname, profileImg = null;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_profile);

        Intent intent = getIntent();
        this.userId = intent.getStringExtra("userId");

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

        addProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainImgIntent = new Intent(Intent.ACTION_PICK);
                mainImgIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(mainImgIntent, GET_GALLERY_PROFILE_IMAGE);
            }
        });

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
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GET_GALLERY_PROFILE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedMainImage = data.getData();
            profile.setImageURI(selectedMainImage);
            profileImg = createCopyAndReturnRealPath(getApplicationContext(), selectedMainImage);
            ImageTask imageTask = new ImageTask();
            try {
                String response = imageTask.execute("image/upload/" + userId, profileImg, userId).get();
                JSONObject jsonObject = new JSONObject(response);
                this.profileImg = jsonObject.getString("data");
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    public static String createCopyAndReturnRealPath(@NonNull Context context, @NonNull Uri uri) {
        final ContentResolver contentResolver = context.getContentResolver();
        if (contentResolver == null)
            return null;

        // 파일 경로를 만듬
        String filePath = context.getApplicationInfo().dataDir + File.separator
                + System.currentTimeMillis();
        File file = new File(filePath);
        try {
            // 매개변수로 받은 uri 를 통해  이미지에 필요한 데이터를 불러 들인다.

            InputStream inputStream = contentResolver.openInputStream(uri);
            if (inputStream == null)
                return null;
            // 이미지 데이터를 다시 내보내면서 file 객체에  만들었던 경로를 이용한다.
            OutputStream outputStream = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0)
                outputStream.write(buf, 0, len);
            outputStream.close();
            inputStream.close();
        } catch (IOException ignore) {
            return null;
        }
        return file.getAbsolutePath();
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
            String response = profileTask.execute("mypage/profile", jsonString).get();
            JSONObject jsonObject = new JSONObject(response);

        }catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
