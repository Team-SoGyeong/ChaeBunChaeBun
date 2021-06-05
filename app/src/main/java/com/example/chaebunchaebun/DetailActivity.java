package com.example.chaebunchaebun;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {
    private FirebaseFirestore mDataBase;
    private static final String TAG = "user";
    private CommentAdapter commentAdapter;
    ListView comment_list;
    View header, footer;
    EditText comment_edit;
    Button comment_btn;
    String nickname = "";
    ArrayList<Map<String, Object>> commentList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {//DB
        //DB
        mDataBase = FirebaseFirestore.getInstance();
        //
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_page);

        Intent intent = getIntent();
        String id = intent.getStringExtra("ID");
        int count = intent.getIntExtra("count", 0);

        System.out.println(count);
        getNickname(id);
        getComment(count);

        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter();

        comment_list = (ListView)findViewById(R.id.jrv_comment_list);
        header = getLayoutInflater().inflate(R.layout.detail_page_header, null, false);
        footer = getLayoutInflater().inflate(R.layout.detail_page_footer, null, false);

        comment_list.addHeaderView(header);
        comment_list.addFooterView(footer);

        /*ArrayAdapter adapter = new ArrayAdapter(this, R.layout.detail_page);
        comment_list.setAdapter(adapter);*/

        setHeader(count);
        setFooter(count);

    }

    private void setHeader(int count){
        TextView title = (TextView)findViewById(R.id.header_title);
        TextView nickname = (TextView)findViewById(R.id.header_nickname);
        TextView location = (TextView)findViewById(R.id.header_location);
        TextView vegetable = (TextView)findViewById(R.id.header_vegetable);
        TextView people = (TextView)findViewById(R.id.header_people);
        TextView date = (TextView) findViewById(R.id.header_date);
        TextView other = (TextView) findViewById(R.id.header_ect);

        mDataBase.collection("market")
                .whereEqualTo("count", count)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()){
                                String s_count = document.getData().get("count").toString();
                                int count = Integer.parseInt(s_count);
                                String getTitle = document.getData().get("title").toString();
                                String getNickname = document.getData().get("nickname").toString();
                                String getLocation = document.getData().get("location").toString();
                                String getVegetable = document.getData().get("vegetable").toString();
                                String getPeople = document.getData().get("people").toString();
                                String getDate = document.getData().get("date").toString();
                                String getOther = document.getData().get("other").toString();
                                System.out.println("title:"+ title + " vegetable" + vegetable);

                                title.setText(getTitle);
                                nickname.setText(getNickname);
                                location.setText(getLocation);
                                vegetable.setText(getVegetable);
                                people.setText(getPeople);
                                date.setText(getDate);
                                other.setText(getOther);

                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private synchronized void setFooter(int count){
        comment_edit = (EditText)footer.findViewById(R.id.footer_edit);
        comment_btn = (Button)footer.findViewById(R.id.footer_btn);

        comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
                String getComment = comment_edit.getText().toString();
                System.out.println(nickname);
                System.out.println(getComment + " 댓글");
                //private void setContent() {
                //getNickname(id);
                Map<String, Object> result = new HashMap<>();
                result.put("nickname", nickname);
                result.put("comment", getComment);
                result.put("time", time);
                //result.put("nickname", nickname);
                System.out.println(result);

                commentList.add(result);
                Log.d(TAG, "comment data: " + commentList);

                Map<String, ArrayList<Map<String, Object>>> comment = new HashMap<>();
                comment.put("comment list", commentList);

                mDataBase.collection("comments")
                        .document(Integer.toString(count))
                        .set(comment)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(DetailActivity.this, "저장되었습니다", Toast.LENGTH_SHORT).show();
                                commentAdapter.addItem(getComment, nickname, time);
                                comment_list.setAdapter(commentAdapter);
                                /*Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                                startActivity(intent);*/
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(DetailActivity.this, "저장에 실패했습니다", Toast.LENGTH_SHORT).show();
                            }
                        });
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
    }

    private void getComment(int count) {
        DocumentReference doc_comment = mDataBase.collection("comments").document(Integer.toString(count));
        doc_comment.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                ArrayList list = (ArrayList) document.getData().get("comment list");
                                setComment(list);
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void setComment(ArrayList list) {
        this.commentList = list;
        for(int i = 0; i < list.size(); i++){
            HashMap<String, Object> map = (HashMap) list.get(i);
            String comment = map.get("comment").toString();
            String nickname = map.get("nickname").toString();
            String time = map.get("time").toString();

            commentAdapter.addItem(comment, nickname, time);
            comment_list.setAdapter(commentAdapter);
        }
    }
}
