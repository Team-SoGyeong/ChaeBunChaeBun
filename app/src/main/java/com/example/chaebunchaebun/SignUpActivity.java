package com.example.chaebunchaebun;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseFirestore mDataBase;
    private static final String TAG = "user";
    EditText et_user_name, et_user_nickname, et_user_id, et_user_pw, et_user_address, et_user_PwCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.signup));

        et_user_name = findViewById(R.id.userName);
        et_user_nickname = findViewById(R.id.userNickname);
        et_user_id = findViewById(R.id.userId);
        et_user_pw = findViewById(R.id.userPw);
        et_user_address = findViewById(R.id.userAddress);
        et_user_PwCheck = findViewById(R.id.userPwCheck);

        mDataBase = FirebaseFirestore.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Button btn_signup = (Button) findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String getUserName = et_user_name.getText().toString();
                String getUserNickname = et_user_nickname.getText().toString();
                String getUserId = et_user_id.getText().toString();
                String getUserPw = et_user_pw.getText().toString();
                String getUserAddress = et_user_address.getText().toString();
                String getUserPwChk = et_user_PwCheck.getText().toString();

                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(getUserId).matches()) {
                    Toast.makeText(SignUpActivity.this,"이메일 형식이 아닙니다",Toast.LENGTH_SHORT).show();
                    et_user_id.setText("");
                    et_user_id.requestFocus();
                } else if(getUserName.equals("") || getUserNickname.equals("") || getUserId.equals("") || getUserPw.equals("") || getUserAddress.equals("")){
                    Toast.makeText(SignUpActivity.this, "입력되지 않은 칸이 있습니다", Toast.LENGTH_SHORT).show();
                } else if(!getUserPw.equals(getUserPwChk)) {
                    Toast.makeText(SignUpActivity.this, "비밀번호가 일치되지 않았습니다", Toast.LENGTH_SHORT).show();
                }else {
                    CheckId(getUserName, getUserNickname, getUserId, getUserPw, getUserAddress);
                }
            }
        });
    }

    private void setData(String getUserName, String getUserNickname, String getUserId, String getUserPw, String getUserAddress) {
        Map<String, Object> result = new HashMap<>();
        result.put("userName", getUserName);
        result.put("userNickname", getUserNickname);
        result.put("userId", getUserId);
        result.put("userPw", getUserPw);
        result.put("userAddress", getUserAddress);
        result.put("mileage", 0);

        mDataBase.collection("users")
                .document(getUserId)
                .set(result)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SignUpActivity.this, "저장되었습니다", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUpActivity.this, "저장에 실패했습니다", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void CheckId(String getUserName, String getUserNickname, String getUserId, String getUserPw, String getUserAddress) {
        mDataBase.collection("users")
                .whereEqualTo("userId", getUserId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            if(task.getResult().isEmpty()) {
                                CheckNickname(getUserName, getUserNickname, getUserId, getUserPw, getUserAddress);
                            } else {
                                Toast.makeText(SignUpActivity.this, "이미 있는 아이디입니다.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void CheckNickname(String getUserName, String getUserNickname, String getUserId, String getUserPw, String getUserAddress) {
        mDataBase.collection("users")
                .whereEqualTo("userNickname", getUserNickname)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            if(task.getResult().isEmpty()) {
                                setData(getUserName, getUserNickname, getUserId, getUserPw, getUserAddress);
                            } else {
                                Toast.makeText(SignUpActivity.this, "이미 있는 닉네임입니다.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

}