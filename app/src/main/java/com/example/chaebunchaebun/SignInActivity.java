package com.example.chaebunchaebun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class SignInActivity extends AppCompatActivity {
    private FirebaseFirestore mDataBase;
    private static final String TAG = "user";
    EditText et_Id, et_Pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.signin));

        et_Id = findViewById(R.id.edt_id);
        et_Pw = findViewById(R.id.edt_password);

        mDataBase = FirebaseFirestore.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        TextView btn_signup = (TextView) findViewById(R.id.go_signup);
        btn_signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        Button btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String getUserId = et_Id.getText().toString();
                String getUserPw = et_Pw.getText().toString();

                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(getUserId).matches()) {
                    Toast.makeText(SignInActivity.this,"아이디가 이메일 형식이 아닙니다",Toast.LENGTH_SHORT).show();
                    et_Id.setText("");
                    et_Pw.setText("");
                    et_Id.requestFocus();
                } else if(getUserId.equals("") || getUserPw.equals("")) {
                    Toast.makeText(SignInActivity.this, "입력되지 않은 칸이 있습니다", Toast.LENGTH_SHORT).show();
                    et_Id.setText("");
                    et_Pw.setText("");
                } else {
                    Log.d(TAG, getUserId);
                    getData(getUserId, getUserPw);
                }
            }
        });
    }

    private void getData(String getUserId, String getUserPw){
        mDataBase.collection("users")
                .whereEqualTo("userId", getUserId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            Log.d(TAG, "test1");
                            if(task.getResult().isEmpty()){
                                Toast.makeText(SignInActivity.this, "가입되지 않은 아이디 입니다.", Toast.LENGTH_SHORT).show();
                                et_Id.setText("");
                                et_Pw.setText("");
                                et_Id.requestFocus();
                            } else {
                                for(QueryDocumentSnapshot document : task.getResult()){
                                    Log.d(TAG, document.getId() + " => " + document.getData().get("userPw"));
                                    String userPw = document.getData().get("userPw").toString();
                                    Log.d(TAG, userPw);

                                    if(userPw.equals(getUserPw)){
                                        Toast.makeText(SignInActivity.this, "로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                                        et_Id.setText("");
                                        et_Pw.setText("");
                                        et_Id.requestFocus();
                                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                        intent.putExtra("ID", getUserId);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(SignInActivity.this, "아이디 혹은 비밀번호가 틀렸습니다", Toast.LENGTH_SHORT).show();
                                        et_Id.setText("");
                                        et_Pw.setText("");
                                        et_Id.requestFocus();
                                    }
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}