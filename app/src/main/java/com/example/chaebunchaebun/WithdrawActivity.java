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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WithdrawActivity extends AppCompatActivity {
    private FirebaseFirestore mDataBase;
    private static final String TAG = "user";
    String password = "";
    String nickname = "";
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.withdrawal_membership));

        mDataBase = FirebaseFirestore.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Button withdraw = (Button) findViewById(R.id.btn_withdraw);
        EditText inputPw = (EditText) findViewById(R.id.edt_id);

        Intent intent = getIntent();
        String id = intent.getStringExtra("ID");
        getNickname(id);

        withdraw.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(flag == true){
                    password = inputPw.getText().toString();
                    System.out.println("input값: " + password);
                    getPassword(id, password);
                }
                else{
                    Toast.makeText(WithdrawActivity.this, "진행 중인 거래가 남아있습니다.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void withDraw(String id){
        System.out.println(id);
        mDataBase.collection("users")
                .document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(WithdrawActivity.this, "탈퇴되셨습니다.", Toast.LENGTH_SHORT).show();
                        Intent first = new Intent(getApplicationContext(), SignInActivity.class);
                        startActivity(first);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }

    private void getPassword(String getUserId, String inputPw) {
        mDataBase.collection("users")
                .whereEqualTo("userId", getUserId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData().get("userPw"));
                                String userPassword = document.getData().get("userPw").toString();
                                Log.d(TAG, userPassword);
                                comparePassword(getUserId, userPassword, inputPw);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void comparePassword(String userId, String userPassword, String inputPw) {
        System.out.println("DbPw: " + userPassword + "password: " + inputPw);
        if(userPassword.equals(inputPw)){
            withDraw(userId);
        }
        else{
            Toast.makeText(WithdrawActivity.this, "일치하지 않습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private void compareDate(String nickname){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date time = new Date();
        String date = format.format(time);
        System.out.println("nickname: " + nickname);

        mDataBase.collection("market")
                .whereEqualTo("nickname", nickname)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()){
                                String getDate = document.getData().get("date").toString();

                                int compare = getDate.compareTo(date);
                                System.out.println("getDate: " + getDate + " date: " + date + " compare: " + compare);

                                if(flag == true && compare > 0) flag = false;

                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void getNickname(String getUserId) {
        mDataBase.collection("users")
                .whereEqualTo("userId", getUserId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Log.d(TAG, document.getId() + " => " + document.getData().get("userPw"));
                                String userNickname = document.getData().get("userNickname").toString();
                                Log.d(TAG, userNickname);
                                setNickname(userNickname);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void setNickname(String nickname) {
        this.nickname = nickname;
        compareDate(nickname);
    }
}
