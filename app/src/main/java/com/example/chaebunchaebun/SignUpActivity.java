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
    EditText et_user_name, et_user_nickname, et_user_id, et_user_pw, et_user_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("pull_test");
        super.onCreate(savedInstanceState);
        setContentView((R.layout.signup));

        et_user_name = findViewById(R.id.userName);
        et_user_nickname = findViewById(R.id.userNickname);
        et_user_id = findViewById(R.id.userId);
        et_user_pw = findViewById(R.id.userPw);
        et_user_address = findViewById(R.id.userAddress);

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

                Map<String, Object> result = new HashMap<>();
                result.put("userName", getUserName);
                result.put("userNickname", getUserNickname);
                result.put("userId", getUserId);
                result.put("userPw", getUserPw);
                result.put("userAddress", getUserAddress);
                result.put("mileage", 0);

                mDataBase.collection("users")
                        .add(result)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(SignUpActivity.this, "저장되었습니다", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
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
        });
    }
}
