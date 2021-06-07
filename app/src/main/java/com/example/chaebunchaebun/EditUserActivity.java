package com.example.chaebunchaebun;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

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

import java.util.HashMap;
import java.util.Map;

public class EditUserActivity extends AppCompatActivity {
    private FirebaseFirestore mDataBase;
    private static final String TAG = "user";
    EditText edit_name, edit_nickname, edit_Id, edit_pw, edit_pwchk, edit_address;
    String nickname, title;
    int mileage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.edit_user_info));

        mDataBase = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        String id = intent.getStringExtra("ID");

        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_nickname = (EditText) findViewById(R.id.edit_nickname);
        edit_Id = (EditText) findViewById(R.id.edit_Id);
        edit_pw = (EditText) findViewById(R.id.edit_pw);
        edit_pwchk = (EditText) findViewById(R.id.edit_pwchk);
        edit_address = (EditText) findViewById(R.id.edit_address);

        getUserInfo(id);

        Button btn_editInfo = (Button) findViewById(R.id.btn_editInfo);
        btn_editInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = edit_name.getText().toString();
                String userNickname = edit_nickname.getText().toString();
                String userId = edit_Id.getText().toString();
                String userPw = edit_pw.getText().toString();
                String userPwChk = edit_pwchk.getText().toString();
                String userAddress = edit_address.getText().toString();

                setUpdateUser(userName, userNickname, userId, userPw, userPwChk, userAddress);
            }
        });
    }

    private void getUserInfo(String id){
        mDataBase.collection("users")
                .whereEqualTo("userId", id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                String userName = document.getData().get("userName").toString();
                                String userNickname = document.getData().get("userNickname").toString();
                                String userId = document.getData().get("userId").toString();
                                String userAddress = document.getData().get("userAddress").toString();
                                String s_mileage = document.getData().get("mileage").toString();

                                edit_name.setText(userName);
                                edit_nickname.setText(userNickname);
                                edit_Id.setText(userId);
                                edit_address.setText(userAddress);

                                getMarket(userNickname, s_mileage);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    void getMarket(String nickname, String mileage) {
        this.nickname = nickname;
        this.mileage = Integer.parseInt(mileage);
    }

    private void setUpdateUser(String getUserName, String getUserNickname, String getUserId, String getUserPw, String getUserPwChk, String getUserAddress) {
        Map<String, Object> result = new HashMap<>();
        result.put("userName", getUserName);
        result.put("userNickname", getUserNickname);
        result.put("userId", getUserId);
        result.put("userPw", getUserPw);
        result.put("userAddress", getUserAddress);
        result.put("mileage", this.mileage);

        mDataBase.collection("users")
                .document(getUserId)
                .set(result)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if(getUserName.equals("") || getUserNickname.equals("") || getUserPw.equals("") || getUserPwChk.equals("") || getUserAddress.equals("")){
                            Toast.makeText(EditUserActivity.this, "입력되지 않은 칸이 있습니다", Toast.LENGTH_SHORT).show();
                        }else if(!getUserPw.matches("^(?=.*[a-z]+[0-9]+).{8,20}$")){
                            Toast.makeText(EditUserActivity.this, "비밀번호 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
                        } else if(!getUserPw.equals(getUserPwChk)) {
                            Toast.makeText(EditUserActivity.this, "비밀번호가 일치되지 않았습니다", Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(EditUserActivity.this, "수정되었습니다", Toast.LENGTH_SHORT).show();
                            //finish();
                            setUpdateMarket(nickname, getUserNickname);
                        }
                        /*Intent intent = new Intent(getApplicationContext(), MembershipActivity.class);
                        intent.putExtra("ID", getUserId);
                        startActivity(intent);*/
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditUserActivity.this, "수정에 실패했습니다", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setUpdateMarket(String nickname, String newNickname){
        mDataBase.collection("market")
                .whereEqualTo("nickname", nickname)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                String s_count = document.getData().get("count").toString();
                                int count = Integer.parseInt(s_count);
                                String title = document.getData().get("title").toString();
                                String location = document.getData().get("location").toString();
                                String vegetable = document.getData().get("vegetable").toString();
                                String s_people = document.getData().get("people").toString();
                                int people = Integer.parseInt(s_people);
                                String date = document.getData().get("date").toString();
                                String other = document.getData().get("other").toString();

                                Map<String, Object> market = new HashMap<>();
                                market.put("count", count);
                                market.put("title", title);
                                market.put("location", location);
                                market.put("vegetable", vegetable);
                                market.put("people", people);
                                market.put("date", date);
                                market.put("other", other);
                                market.put("nickname", newNickname);

                                mDataBase.collection("market")
                                        .document(title)
                                        .set(market)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                setUpdateComment(nickname, newNickname);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(EditUserActivity.this, "수정에 실패했습니다", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void setUpdateComment(String nickname, String newNickname) {
        mDataBase.collection("comments")
                .whereEqualTo("nickname", nickname)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            if(task.getResult().isEmpty()){
                                Toast.makeText(EditUserActivity.this, "수정되었습니다", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                for(QueryDocumentSnapshot document : task.getResult()){
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    String getComment = document.getData().get("comment").toString();
                                    String time = document.getData().get("time").toString();

                                    Map<String, Object> comment = new HashMap<>();
                                    comment.put("nickname", newNickname);
                                    comment.put("comment", getComment);
                                    comment.put("time", time);

                                    mDataBase.collection("comments")
                                            .document(nickname)
                                            .set(comment)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(EditUserActivity.this, "수정되었습니다", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(EditUserActivity.this, "수정에 실패했습니다", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}
