package com.E2I3.chaebunchaebun;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class ChangeChaebunEtcActivity extends AppCompatActivity {
    private TextView toastText;
    private Toast toast;

    ImageView back, writing, btn_back, etcMainImg, etcSubImg1, etcSubImg2, etcSubImg3, etcSubImg4;
    TextView inputPerPrice,  inputContentCount;
    EditText inputVegetable, inputTitle, inputContent, inputAmount, inputGetPrice, inputMemberNum, inputCall;
    Spinner date_spinner;
    String date_arr[] = new String[1];
    String amount_arr[] ={"kg","g","개"};
    String str, amount_str, userId, postId = null;
    String amount, totalPrice, people = "1";
    String mainImg, subImg1, subImg2, subImg3, subImg4 = null;
    int categoryId, perPrice;
    boolean isPrice, isMember, isAmount = false;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_chaebun_etc);

        LayoutInflater inflater = getLayoutInflater();
        View customToast = inflater.inflate(R.layout.custom_report_toast, (ViewGroup) findViewById(R.id.custom_toast_layout));

        toastText = (TextView) customToast.findViewById(R.id.custom_toast_text);
        toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(customToast);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        postId = intent.getStringExtra("postId");
        categoryId = intent.getIntExtra("categoryId", 0);

        System.out.println("아이디:" + userId);

        inputVegetable = (EditText) findViewById(R.id.input_vegetable);
        inputTitle = (EditText) findViewById(R.id.input_title);
        inputContent = (EditText) findViewById(R.id.input_content);
        inputAmount = (EditText) findViewById(R.id.input_amount);
        inputGetPrice = (EditText) findViewById(R.id.input_getPrice);
        inputMemberNum = (EditText) findViewById(R.id.input_memberNum);
        inputCall = (EditText) findViewById(R.id.input_call);

        inputPerPrice = (TextView) findViewById(R.id.input_per_price);
        inputContentCount = (TextView) findViewById(R.id.input_content_count);

        etcMainImg = (ImageView) findViewById(R.id.etc_main_img);
        etcSubImg1 = (ImageView) findViewById(R.id.etc_sub_img1);
        etcSubImg2 = (ImageView) findViewById(R.id.etc_sub_img2);
        etcSubImg3 = (ImageView) findViewById(R.id.etc_sub_img3);
        etcSubImg4 = (ImageView) findViewById(R.id.etc_sub_img4);

        back = (ImageView) findViewById(R.id.id_back);
        writing = (ImageView) findViewById(R.id.btn_next);
        btn_back = (ImageView) findViewById(R.id.btn_back);

        date_spinner = (Spinner) findViewById(R.id.date_spinner2);

        getPostList();

        inputVegetable.setClickable(false);
        inputVegetable.setFocusable(false);
        inputTitle.setClickable(false);
        inputTitle.setFocusable(false);
        inputAmount.setClickable(false);
        inputAmount.setFocusable(false);
        inputGetPrice.setClickable(false);
        inputGetPrice.setFocusable(false);
        inputMemberNum.setClickable(false);
        inputMemberNum.setFocusable(false);

        inputPerPrice.setClickable(false);
        inputPerPrice.setFocusable(false);

        etcMainImg.setClickable(false);
        etcSubImg1.setClickable(false);
        etcSubImg2.setClickable(false);
        etcSubImg3.setClickable(false);
        etcSubImg4.setClickable(false);

        date_spinner.setClickable(false);

        ArrayAdapter<String> date_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, date_arr);
        date_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        date_spinner.setAdapter(date_adapter);

        inputContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = inputContent.getText().toString();
                if(input.length() <= 500){
                    inputContentCount.setText("(" + input.length() + "/500)");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        writing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PutTask putTask = new PutTask();
                JSONObject jsonCommentTransfer = new JSONObject();

                try {
                    jsonCommentTransfer.put("author_id", Integer.parseInt(userId));
                    jsonCommentTransfer.put("category_id", categoryId);
                    jsonCommentTransfer.put("contact", inputCall.getText().toString());
                    jsonCommentTransfer.put("contents", inputContent.getText().toString());
                    jsonCommentTransfer.put("post_id", Integer.parseInt(postId));
                    String jsonString = jsonCommentTransfer.toString();
                    putTask.execute("posts/", jsonString);
                    toastText.setText("수정되었어요!");
                    toast.show();
                    Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getPostList() {
        String resultText = "[NULL]";

        try {
            resultText = new GetTask("posts/" + this.postId + "/" + this.userId).execute().get();
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
                inputVegetable.setText(subJsonObject.getString("category_name"));
                String posts = subJsonObject.getString("posts");
                JSONArray jsonLastListArray = new JSONArray(posts);
                for(int j = 0; j < jsonLastListArray.length(); j++){
                    JSONObject subJsonObject2 = jsonLastListArray.getJSONObject(j);

                    inputTitle.setText(subJsonObject2.getString("title"));
                    inputContent.setText(subJsonObject2.getString("contents"));
                    date_arr[0] = subJsonObject2.getString("buy_date");
                    inputMemberNum.setText(subJsonObject2.getString("post_addr"));
                    inputAmount.setText(subJsonObject2.getString("amount"));
                    inputGetPrice.setText(subJsonObject2.getString("total_price"));
                    inputPerPrice.setText(subJsonObject2.getString("per_price"));
                    inputCall.setText(subJsonObject2.getString("contact"));

                    String img1 = null;
                    String img2 = null;
                    String img3 = null;
                    String img4 = null;
                    String img5 = null;

                    String imgs = subJsonObject2.getString("imgs");
                    JSONObject subJsonObject3 = new JSONObject(imgs);
                    img1 = subJsonObject3.getString("img1");
                    img2 = subJsonObject3.getString("img2");
                    img3 = subJsonObject3.getString("img3");
                    img4 = subJsonObject3.getString("img4");
                    img5 = subJsonObject3.getString("img5");

                    if(img2.isEmpty() || img2 == null || img2.equals("null")){
                        Glide.with(getApplicationContext()).load(img1).into(etcMainImg);
                    } else if(img3.isEmpty() || img3 == null || img3.equals("null")){
                        Glide.with(getApplicationContext()).load(img1).into(etcMainImg);
                        Glide.with(getApplicationContext()).load(img1).into(etcSubImg1);
                    } else if(img4.isEmpty() || img4 == null || img4.equals("null")){
                        Glide.with(getApplicationContext()).load(img1).into(etcMainImg);
                        Glide.with(getApplicationContext()).load(img1).into(etcSubImg1);
                        Glide.with(getApplicationContext()).load(img1).into(etcSubImg2);
                    } else if(img5.isEmpty() || img5 == null || img5.equals("null")){
                        Glide.with(getApplicationContext()).load(img1).into(etcMainImg);
                        Glide.with(getApplicationContext()).load(img1).into(etcSubImg1);
                        Glide.with(getApplicationContext()).load(img1).into(etcSubImg2);
                        Glide.with(getApplicationContext()).load(img1).into(etcSubImg3);
                    } else {
                        Glide.with(getApplicationContext()).load(img1).into(etcMainImg);
                        Glide.with(getApplicationContext()).load(img1).into(etcSubImg1);
                        Glide.with(getApplicationContext()).load(img1).into(etcSubImg2);
                        Glide.with(getApplicationContext()).load(img1).into(etcSubImg3);
                        Glide.with(getApplicationContext()).load(img1).into(etcSubImg4);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
