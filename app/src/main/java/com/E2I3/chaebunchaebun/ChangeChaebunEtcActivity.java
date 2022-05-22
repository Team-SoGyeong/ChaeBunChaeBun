package com.E2I3.chaebunchaebun;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Outline;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class ChangeChaebunEtcActivity extends AppCompatActivity {
    private final int GET_GALLERY_MAIN_IMAGE = 200;
    private final int GET_GALLERY_SUB1_IMAGE = 201;
    private final int GET_GALLERY_SUB2_IMAGE = 202;
    private final int GET_GALLERY_SUB3_IMAGE = 203;
    private final int GET_GALLERY_SUB4_IMAGE = 204;
    private final int GET_GALLERY_BILL1_IMAGE = 205;
    private final int GET_GALLERY_BILL2_IMAGE = 206;
    private TextView toastText;
    private Toast toast;

    HorizontalScrollView picture_view, bill_view;
    LinearLayout mainImgFrame, subImgFrame1, subImgFrame2, subImgFrame3, subImgFrame4, billImgFrame1, billImgFrame2;
    ImageView back, writing, btn_back, etcMainImg, etcSubImg1, etcSubImg2, etcSubImg3, etcSubImg4,
            writingMainImg, writingSubImg1, writingSubImg2, writingSubImg3, writingSubImg4, writingBillImg1, writingBillImg2,
            mainImgDelete, subImg1Delete, subImg2Delete, subImg3Delete, subImg4Delete, billImg1Delete, billImg2Delete;
    ImageButton add_picture, add_receipt;
    TextView inputPerPrice,  inputContentCount;
    EditText inputVegetable, inputTitle, inputContent, inputAmount, inputGetPrice, inputMemberNum, inputCall;

    Spinner date_spinner;
    String date_arr[] = new String[1];
    String delete_arr[];
    String str, amount_str, userId, postId = null;
    String title, content, amountString, getPrice, call, buyDate,
            bill1, bill2, img1, img2, img3, img4, img5, amountType;

    Uri selectedMainImage, selectedSub1Image, selectedSub2Image, selectedSub3Image, selectedSub4Image, selectedBill1Image, selectedBill2Image;
    int categoryId, totalPrice, amountNum;
    int pictureId = 0;
    Long postAddr;

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
        /*inputMemberNum = (EditText) findViewById(R.id.input_memberNum);*/
        inputCall = (EditText) findViewById(R.id.input_call);

        /*inputPerPrice = (TextView) findViewById(R.id.input_per_price);*/
        inputContentCount = (TextView) findViewById(R.id.input_content_count);

        etcMainImg = (ImageView) findViewById(R.id.etc_main_img);
        etcSubImg1 = (ImageView) findViewById(R.id.etc_sub_img1);
        etcSubImg2 = (ImageView) findViewById(R.id.etc_sub_img2);
        etcSubImg3 = (ImageView) findViewById(R.id.etc_sub_img3);
        etcSubImg4 = (ImageView) findViewById(R.id.etc_sub_img4);

        add_picture = (ImageButton) findViewById(R.id.etc_add_img);
        /*add_receipt = (ImageButton) findViewById(R.id.add_receipt);*/
        picture_view = (HorizontalScrollView) findViewById(R.id.picture_view);
        bill_view = (HorizontalScrollView) findViewById(R.id.bill_view);
        mainImgFrame = (LinearLayout) findViewById(R.id.main_img_frame);
        subImgFrame1 = (LinearLayout) findViewById(R.id.sub_img1_frame);
        subImgFrame2 = (LinearLayout) findViewById(R.id.sub_img2_frame);
        subImgFrame3 = (LinearLayout) findViewById(R.id.sub_img3_frame);
        subImgFrame4 = (LinearLayout) findViewById(R.id.sub_img4_frame);
        /*billImgFrame1 = (LinearLayout) findViewById(R.id.bill_img1_frame);
        billImgFrame2 = (LinearLayout) findViewById(R.id.bill_img2_frame);*/

        mainImgDelete = (ImageView) findViewById(R.id.etc_main_img_delete);
        subImg1Delete = (ImageView) findViewById(R.id.etc_sub_img1_delete);
        subImg2Delete = (ImageView) findViewById(R.id.etc_sub_img2_delete);
        subImg3Delete = (ImageView) findViewById(R.id.etc_sub_img3_delete);
        subImg4Delete = (ImageView) findViewById(R.id.etc_sub_img4_delete);
        /*billImg1Delete = (ImageView) findViewById(R.id.etc_bill1_img_delete);
        billImg2Delete = (ImageView) findViewById(R.id.etc_bill2_img_delete);*/

        writingMainImg = (ImageView) findViewById(R.id.etc_main_img);
        writingSubImg1 = (ImageView) findViewById(R.id.etc_sub_img1);
        writingSubImg2 = (ImageView) findViewById(R.id.etc_sub_img2);
        writingSubImg3 = (ImageView) findViewById(R.id.etc_sub_img3);
        writingSubImg4 = (ImageView) findViewById(R.id.etc_sub_img4);
        /*writingBillImg1 = (ImageView) findViewById(R.id.etc_bill1_img);
        writingBillImg2 = (ImageView) findViewById(R.id.etc_bill2_img);*/

        back = (ImageView) findViewById(R.id.id_back);
        writing = (ImageView) findViewById(R.id.btn_next);
        btn_back = (ImageView) findViewById(R.id.btn_back);

        date_spinner = (Spinner) findViewById(R.id.date_spinner2);

        getPostList();

        inputVegetable.setClickable(false);
        inputVegetable.setFocusable(false);
        inputAmount.setClickable(false);
        inputAmount.setFocusable(false);
        inputGetPrice.setClickable(false);
        inputGetPrice.setFocusable(false);
        /*inputMemberNum.setClickable(false);
        inputMemberNum.setFocusable(false);

        inputPerPrice.setClickable(false);
        inputPerPrice.setFocusable(false);*/

        date_spinner.setClickable(false);

        ArrayAdapter<String> date_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, date_arr);
        date_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        date_spinner.setAdapter(date_adapter);

        String input = inputContent.getText().toString();
        inputContentCount.setText("(" + input.length() + "/500)");

        if(img2.isEmpty() || img2 == null || img2.equals("null")){
            mainImgFrame.setVisibility(View.VISIBLE);
            mainImgDelete.setVisibility(View.VISIBLE);
            add_picture.setImageResource(R.drawable.writing_btn_picture1);
        } else if(img3.isEmpty() || img3 == null || img3.equals("null")){
            mainImgFrame.setVisibility(View.VISIBLE);
            mainImgDelete.setVisibility(View.VISIBLE);
            subImgFrame1.setVisibility(View.VISIBLE);
            subImg1Delete.setVisibility(View.VISIBLE);
            add_picture.setImageResource(R.drawable.writing_btn_picture2);
        } else if(img4.isEmpty() || img4 == null || img4.equals("null")){
            mainImgFrame.setVisibility(View.VISIBLE);
            mainImgDelete.setVisibility(View.VISIBLE);
            subImgFrame1.setVisibility(View.VISIBLE);
            subImg1Delete.setVisibility(View.VISIBLE);
            subImgFrame2.setVisibility(View.VISIBLE);
            subImg2Delete.setVisibility(View.VISIBLE);
            add_picture.setImageResource(R.drawable.writing_btn_picture3);
        } else if(img5.isEmpty() || img5 == null || img5.equals("null")){
            mainImgFrame.setVisibility(View.VISIBLE);
            mainImgDelete.setVisibility(View.VISIBLE);
            subImgFrame1.setVisibility(View.VISIBLE);
            subImg1Delete.setVisibility(View.VISIBLE);
            subImgFrame2.setVisibility(View.VISIBLE);
            subImg2Delete.setVisibility(View.VISIBLE);
            subImgFrame3.setVisibility(View.VISIBLE);
            subImg3Delete.setVisibility(View.VISIBLE);
            add_picture.setImageResource(R.drawable.writing_btn_picture4);
        } else {
            mainImgFrame.setVisibility(View.VISIBLE);
            mainImgDelete.setVisibility(View.VISIBLE);
            subImgFrame1.setVisibility(View.VISIBLE);
            subImg1Delete.setVisibility(View.VISIBLE);
            subImgFrame2.setVisibility(View.VISIBLE);
            subImg2Delete.setVisibility(View.VISIBLE);
            subImgFrame3.setVisibility(View.VISIBLE);
            subImg3Delete.setVisibility(View.VISIBLE);
            subImgFrame4.setVisibility(View.VISIBLE);
            subImg4Delete.setVisibility(View.VISIBLE);
            add_picture.setImageResource(R.drawable.writing_btn_picture5);
        }

        add_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picture_view.setVisibility(View.VISIBLE);
                switch (pictureId){
                    case 0:
                        Intent mainImgIntent = new Intent(Intent.ACTION_PICK);
                        mainImgIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(mainImgIntent, GET_GALLERY_MAIN_IMAGE);
                        break;
                    case 1:
                        Intent subImg1Intent = new Intent(Intent.ACTION_PICK);
                        subImg1Intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(subImg1Intent, GET_GALLERY_SUB1_IMAGE);
                        break;
                    case 2:
                        Intent subImg2Intent = new Intent(Intent.ACTION_PICK);
                        subImg2Intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(subImg2Intent, GET_GALLERY_SUB2_IMAGE);
                        break;
                    case 3:
                        Intent subImg3Intent = new Intent(Intent.ACTION_PICK);
                        subImg3Intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(subImg3Intent, GET_GALLERY_SUB3_IMAGE);
                        break;
                    case 4:
                        Intent subImg4Intent = new Intent(Intent.ACTION_PICK);
                        subImg4Intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(subImg4Intent, GET_GALLERY_SUB4_IMAGE);
                        break;
                }
            }
        });

        //사진 삭제 버튼
        mainImgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pictureId--;
                switch(pictureId){
                    case 0:
                        add_picture.setImageResource(R.drawable.writing_btn_picture);
                        /*selectedMainImage = null;*/
                        delete_arr = img1.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img1 = null;
                        mainImgFrame.setVisibility(View.GONE);
                        mainImgDelete.setVisibility(View.GONE);
                        break;
                    case 1:
                        add_picture.setImageResource(R.drawable.writing_btn_picture1);
                        /*selectedMainImage = selectedSub1Image;
                        selectedSub1Image = null;*/
                        delete_arr = img1.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img1 = img2;
                        img2 = null;
                        Glide.with(getApplicationContext()).load(img1).into(writingMainImg);
                        /*writingMainImg.setImageURI(selectedMainImage);*/
                        subImgFrame1.setVisibility(View.GONE);
                        subImg1Delete.setVisibility(View.GONE);
                        break;
                    case 2:
                        add_picture.setImageResource(R.drawable.writing_btn_picture2);
                        /*selectedMainImage = selectedSub1Image;
                        selectedSub1Image = selectedSub2Image;
                        selectedSub2Image = null;*/
                        delete_arr = img1.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img1 = img2;
                        img2 = img3;
                        img3 = null;
                        Glide.with(getApplicationContext()).load(img1).into(writingMainImg);
                        Glide.with(getApplicationContext()).load(img2).into(writingSubImg1);
                        /*writingMainImg.setImageURI(selectedMainImage);
                        writingSubImg1.setImageURI(selectedSub1Image);*/
                        subImgFrame2.setVisibility(View.GONE);
                        subImg2Delete.setVisibility(View.GONE);
                        break;
                    case 3:
                        add_picture.setImageResource(R.drawable.writing_btn_picture3);
                        /*selectedMainImage = selectedSub1Image;
                        selectedSub1Image = selectedSub2Image;
                        selectedSub2Image = selectedSub3Image;
                        selectedSub3Image = null;*/
                        delete_arr = img1.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img1 = img2;
                        img2 = img3;
                        img3 = img4;
                        img4 = null;
                        Glide.with(getApplicationContext()).load(img1).into(writingMainImg);
                        Glide.with(getApplicationContext()).load(img2).into(writingSubImg1);
                        Glide.with(getApplicationContext()).load(img3).into(writingSubImg2);
                        /*writingMainImg.setImageURI(selectedMainImage);
                        writingSubImg1.setImageURI(selectedSub1Image);
                        writingSubImg2.setImageURI(selectedSub2Image);*/
                        subImgFrame3.setVisibility(View.GONE);
                        subImg3Delete.setVisibility(View.GONE);
                        break;
                    case 4:
                        add_picture.setImageResource(R.drawable.writing_btn_picture4);
                        /*selectedMainImage = selectedSub1Image;
                        selectedSub1Image = selectedSub2Image;
                        selectedSub2Image = selectedSub3Image;
                        selectedSub3Image = selectedSub4Image;
                        selectedSub4Image = null;*/
                        delete_arr = img1.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img1 = img2;
                        img2 = img3;
                        img3 = img4;
                        img4 = img5;
                        img5 = null;
                        Glide.with(getApplicationContext()).load(img1).into(writingMainImg);
                        Glide.with(getApplicationContext()).load(img2).into(writingSubImg1);
                        Glide.with(getApplicationContext()).load(img3).into(writingSubImg2);
                        Glide.with(getApplicationContext()).load(img4).into(writingSubImg3);
                        /*writingMainImg.setImageURI(selectedMainImage);
                        writingSubImg1.setImageURI(selectedSub1Image);
                        writingSubImg2.setImageURI(selectedSub2Image);
                        writingSubImg3.setImageURI(selectedSub3Image);*/
                        subImgFrame4.setVisibility(View.GONE);
                        subImg4Delete.setVisibility(View.GONE);
                        break;
                }
            }
        });

        subImg1Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pictureId--;
                switch(pictureId){
                    case 1:
                        add_picture.setImageResource(R.drawable.writing_btn_picture1);
                        /*selectedSub1Image = null;*/
                        delete_arr = img2.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img2 = null;
                        subImgFrame1.setVisibility(View.GONE);
                        subImg1Delete.setVisibility(View.GONE);
                        break;
                    case 2:
                        add_picture.setImageResource(R.drawable.writing_btn_picture2);
                        /*selectedSub1Image = selectedSub2Image;
                        selectedSub2Image = null;*/
                        delete_arr = img2.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img2 = img3;
                        img3 = null;
                        Glide.with(getApplicationContext()).load(img2).into(writingSubImg1);
                        /*writingSubImg1.setImageURI(selectedSub1Image);*/
                        subImgFrame2.setVisibility(View.GONE);
                        subImg2Delete.setVisibility(View.GONE);
                        break;
                    case 3:
                        add_picture.setImageResource(R.drawable.writing_btn_picture3);
                        /*selectedSub1Image = selectedSub2Image;
                        selectedSub2Image = selectedSub3Image;
                        selectedSub3Image = null;*/
                        delete_arr = img2.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img2 = img3;
                        img3 = img4;
                        img4 = null;
                        Glide.with(getApplicationContext()).load(img2).into(writingSubImg1);
                        Glide.with(getApplicationContext()).load(img3).into(writingSubImg2);
                        /*writingSubImg1.setImageURI(selectedSub1Image);
                        writingSubImg2.setImageURI(selectedSub2Image);*/
                        subImgFrame3.setVisibility(View.GONE);
                        subImg3Delete.setVisibility(View.GONE);
                        break;
                    case 4:
                        add_picture.setImageResource(R.drawable.writing_btn_picture4);
                        /*selectedSub1Image = selectedSub2Image;
                        selectedSub2Image = selectedSub3Image;
                        selectedSub3Image = selectedSub4Image;
                        selectedSub4Image = null;*/
                        delete_arr = img2.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img2 = img3;
                        img3 = img4;
                        img4 = img5;
                        img5 = null;
                        Glide.with(getApplicationContext()).load(img2).into(writingSubImg1);
                        Glide.with(getApplicationContext()).load(img3).into(writingSubImg2);
                        Glide.with(getApplicationContext()).load(img4).into(writingSubImg3);
                        /*writingSubImg1.setImageURI(selectedSub1Image);
                        writingSubImg2.setImageURI(selectedSub2Image);
                        writingSubImg3.setImageURI(selectedSub3Image);*/
                        subImgFrame4.setVisibility(View.GONE);
                        subImg4Delete.setVisibility(View.GONE);
                        break;
                }
            }
        });

        subImg2Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pictureId--;
                switch(pictureId){
                    case 2:
                        add_picture.setImageResource(R.drawable.writing_btn_picture2);
                        /*selectedSub2Image = null;*/
                        delete_arr = img3.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img3 = null;
                        subImgFrame2.setVisibility(View.GONE);
                        subImg2Delete.setVisibility(View.GONE);
                        break;
                    case 3:
                        add_picture.setImageResource(R.drawable.writing_btn_picture3);
                        /*selectedSub2Image = selectedSub3Image;
                        selectedSub3Image = null;*/
                        delete_arr = img3.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img3 = img4;
                        img4 = null;
                        Glide.with(getApplicationContext()).load(img3).into(writingSubImg2);
                        /*writingSubImg2.setImageURI(selectedSub2Image);*/
                        subImgFrame3.setVisibility(View.GONE);
                        subImg3Delete.setVisibility(View.GONE);
                        break;
                    case 4:
                        add_picture.setImageResource(R.drawable.writing_btn_picture4);
                        /*selectedSub2Image = selectedSub3Image;
                        selectedSub3Image = selectedSub4Image;
                        selectedSub4Image = null;*/
                        delete_arr = img3.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img3 = img4;
                        img4 = img5;
                        img5 = null;
                        Glide.with(getApplicationContext()).load(img3).into(writingSubImg2);
                        Glide.with(getApplicationContext()).load(img4).into(writingSubImg3);
                        /*writingSubImg2.setImageURI(selectedSub2Image);
                        writingSubImg3.setImageURI(selectedSub3Image);*/
                        subImgFrame4.setVisibility(View.GONE);
                        subImg4Delete.setVisibility(View.GONE);
                        break;
                }
            }
        });

        subImg3Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pictureId--;
                switch(pictureId){
                    case 3:
                        add_picture.setImageResource(R.drawable.writing_btn_picture3);
                        /*selectedSub3Image = null;*/
                        delete_arr = img4.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img4 = null;
                        subImgFrame3.setVisibility(View.GONE);
                        subImg3Delete.setVisibility(View.GONE);
                        break;
                    case 4:
                        add_picture.setImageResource(R.drawable.writing_btn_picture4);
                        /*selectedSub3Image = selectedSub4Image;
                        selectedSub4Image = null;*/
                        delete_arr = img4.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img4 = img5;
                        img5 = null;
                        Glide.with(getApplicationContext()).load(img4).into(writingSubImg3);
                        /*writingSubImg3.setImageURI(selectedSub3Image);*/
                        subImgFrame4.setVisibility(View.GONE);
                        subImg4Delete.setVisibility(View.GONE);
                        break;
                }
            }
        });

        subImg4Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pictureId--;
                switch(pictureId){
                    case 4:
                        add_picture.setImageResource(R.drawable.writing_btn_picture4);
                        /*selectedSub4Image = null;*/
                        delete_arr = img5.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img5 = null;
                        subImgFrame4.setVisibility(View.GONE);
                        subImg4Delete.setVisibility(View.GONE);
                        break;
                }
            }
        });

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
                call = inputCall.getText().toString();
                content = inputContent.getText().toString();
                title = inputTitle.getText().toString();

                if(img1 == null || img1.equals(null)) {
                    toastText.setText("메인 이미지는 필수에요!");
                    toast.show();
                } else if(inputTitle.getText().toString().isEmpty() || inputContent.getText().toString().isEmpty()
                        || inputCall.getText().toString().isEmpty()) {
                    toastText.setText("입력되지 않은 칸이 있어요!");
                    toast.show();
                }else {
                    PutTask putTask = new PutTask();
                    JSONObject jsonChangeTransfer = new JSONObject();

                    try {
                        jsonChangeTransfer.put("amount", amountNum);
                        jsonChangeTransfer.put("author_id", Integer.parseInt(userId));
                        jsonChangeTransfer.put("buy_date", buyDate);
                        jsonChangeTransfer.put("category_id", categoryId);
                        jsonChangeTransfer.put("contact", call);
                        jsonChangeTransfer.put("contents", content);

                        String imgString = "{\"bill1\": \"" + bill1 + "\", \"bill2\": \"" + bill2 + "\", \"img1\": \"" + img1 + "\"," +
                                " \"img2\": \"" + img2 + "\", \"img3\": \"" + img3 + "\", \"img4\": \"" + img4 + "\", \"img4\": \"" + img4 + "\", \"img5\": \"" + img5 + "\"}";
                        JSONObject imgs = new JSONObject(imgString);
                        jsonChangeTransfer.put("imgs", imgs);
                        jsonChangeTransfer.put("per_price", postAddr);
                        jsonChangeTransfer.put("post_id", Integer.parseInt(postId));
                        jsonChangeTransfer.put("title", title);
                        jsonChangeTransfer.put("total_price", totalPrice);
                        jsonChangeTransfer.put("unit", amountType);

                    /*jsonCommentTransfer.put("author_id", Integer.parseInt(userId));
                    jsonCommentTransfer.put("category_id", categoryId);
                    jsonCommentTransfer.put("contact", inputCall.getText().toString());
                    jsonCommentTransfer.put("contents", inputContent.getText().toString());
                    jsonCommentTransfer.put("post_id", Integer.parseInt(postId));*/

                        String jsonString = jsonChangeTransfer.toString();

                        String resultText = "[NULL]";

                        try {
                            resultText = putTask.execute("posts/", jsonString).get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }

                        try {
                            JSONObject jsonObject = new JSONObject(resultText);
                            boolean success = jsonObject.getBoolean("success");
                            if(success == true) {
                                toastText.setText("수정되었어요!");
                                toast.show();
                                Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("userId", userId);
                                startActivity(intent);
                                overridePendingTransition(0, 0);
                            } else {
                                Log.i("jsonString: ", jsonString);
                                toastText.setText("수정에 실패하셨습니다.");
                                toast.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
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
                    buyDate = subJsonObject2.getString("buy_date");
                    date_arr[0] = buyDate;
                    /*inputMemberNum.setText();*/
                    postAddr = subJsonObject2.getLong("post_addr");
                    inputAmount.setText(subJsonObject2.getString("amount"));
                    amountNum = subJsonObject2.getInt("amount_num");
                    amountType = subJsonObject2.getString("amount_type");
                    inputGetPrice.setText(subJsonObject2.getString("total_price"));
                    totalPrice = subJsonObject2.getInt("total_price_num");
                    /*inputPerPrice.setText(subJsonObject2.getString("per_price"));*/
                    inputCall.setText(subJsonObject2.getString("contact"));

                    String imgs = subJsonObject2.getString("imgs");
                    JSONObject subJsonObject3 = new JSONObject(imgs);
                    bill1 = subJsonObject3.getString("bill1");
                    bill2 = subJsonObject3.getString("bill2");
                    img1 = subJsonObject3.getString("img1");
                    img2 = subJsonObject3.getString("img2");
                    img3 = subJsonObject3.getString("img3");
                    img4 = subJsonObject3.getString("img4");
                    img5 = subJsonObject3.getString("img5");

                    if(img2.isEmpty() || img2 == null || img2.equals("null")){
                        Glide.with(getApplicationContext()).load(img1).into(etcMainImg);
                        pictureId = 1;
                    } else if(img3.isEmpty() || img3 == null || img3.equals("null")){
                        Glide.with(getApplicationContext()).load(img1).into(etcMainImg);
                        Glide.with(getApplicationContext()).load(img1).into(etcSubImg1);
                        pictureId = 2;
                    } else if(img4.isEmpty() || img4 == null || img4.equals("null")){
                        Glide.with(getApplicationContext()).load(img1).into(etcMainImg);
                        Glide.with(getApplicationContext()).load(img1).into(etcSubImg1);
                        Glide.with(getApplicationContext()).load(img1).into(etcSubImg2);
                        pictureId = 3;
                    } else if(img5.isEmpty() || img5 == null || img5.equals("null")){
                        Glide.with(getApplicationContext()).load(img1).into(etcMainImg);
                        Glide.with(getApplicationContext()).load(img1).into(etcSubImg1);
                        Glide.with(getApplicationContext()).load(img1).into(etcSubImg2);
                        Glide.with(getApplicationContext()).load(img1).into(etcSubImg3);
                        pictureId = 4;
                    } else {
                        Glide.with(getApplicationContext()).load(img1).into(etcMainImg);
                        Glide.with(getApplicationContext()).load(img1).into(etcSubImg1);
                        Glide.with(getApplicationContext()).load(img1).into(etcSubImg2);
                        Glide.with(getApplicationContext()).load(img1).into(etcSubImg3);
                        Glide.with(getApplicationContext()).load(img1).into(etcSubImg4);
                        pictureId = 5;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void deleteImg(String filename) {
        PostTask deleteImgTask = new PostTask();
        String response = null;
        try {
            response = deleteImgTask.execute("image/delete/" + userId +"/" + filename, filename).get();
            Log.i("receiveMsg: ", response);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }

    //사진 uri 처리
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GET_GALLERY_MAIN_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedMainImage = data.getData();
            writingMainImg.setImageURI(selectedMainImage);
            img1 = createCopyAndReturnRealPath(getApplicationContext(), selectedMainImage);
            System.out.println("아이디:" + userId);
            System.out.println("이미지 경로: " + img1);
            mainImgFrame.setVisibility(View.VISIBLE);
            mainImgDelete.setVisibility(View.VISIBLE);
            writingMainImg.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0,0,view.getWidth(),view.getHeight(), 12);
                }
            });
            writingMainImg.setClipToOutline(true);
            add_picture.setImageResource(R.drawable.writing_btn_picture1);
            ImageTask imageTask = new ImageTask();
            try {
                String response = imageTask.execute("image/upload/" + userId, img1, userId).get();
                JSONObject jsonObject = new JSONObject(response);
                this.img1 = jsonObject.getString("data");
                pictureId++;
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(requestCode == GET_GALLERY_SUB1_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            selectedSub1Image = data.getData();
            writingSubImg1.setImageURI(selectedSub1Image);
            img2 = createCopyAndReturnRealPath(getApplicationContext(), selectedSub1Image);
            System.out.println("아이디:" + userId);
            System.out.println("이미지 경로: " + img2);
            subImgFrame1.setVisibility(View.VISIBLE);
            subImg1Delete.setVisibility(View.VISIBLE);
            writingSubImg1.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0,0,view.getWidth(),view.getHeight(), 12);
                }
            });
            writingSubImg1.setClipToOutline(true);
            add_picture.setImageResource(R.drawable.writing_btn_picture2);
            ImageTask imageTask = new ImageTask();
            try {
                String response = imageTask.execute("image/upload/" + userId, img2, userId).get();
                JSONObject jsonObject = new JSONObject(response);
                this.img2 = jsonObject.getString("data");
                pictureId++;
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(requestCode == GET_GALLERY_SUB2_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            selectedSub2Image = data.getData();
            writingSubImg2.setImageURI(selectedSub2Image);
            img3 = createCopyAndReturnRealPath(getApplicationContext(), selectedSub2Image);
            System.out.println("아이디:" + userId);
            System.out.println("이미지 경로: " + img3);
            subImgFrame2.setVisibility(View.VISIBLE);
            subImg2Delete.setVisibility(View.VISIBLE);
            writingSubImg2.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0,0,view.getWidth(),view.getHeight(), 12);
                }
            });
            writingSubImg2.setClipToOutline(true);
            add_picture.setImageResource(R.drawable.writing_btn_picture3);
            ImageTask imageTask = new ImageTask();
            try {
                String response = imageTask.execute("image/upload/" + userId, img3, userId).get();
                JSONObject jsonObject = new JSONObject(response);
                this.img3 = jsonObject.getString("data");
                pictureId++;
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(requestCode == GET_GALLERY_SUB3_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            selectedSub3Image = data.getData();
            writingSubImg3.setImageURI(selectedSub3Image);
            img4 = createCopyAndReturnRealPath(getApplicationContext(), selectedSub3Image);
            System.out.println("아이디:" + userId);
            System.out.println("이미지 경로: " + img4);
            subImgFrame3.setVisibility(View.VISIBLE);
            subImg3Delete.setVisibility(View.VISIBLE);
            writingSubImg3.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0,0,view.getWidth(),view.getHeight(), 12);
                }
            });
            writingSubImg3.setClipToOutline(true);
            add_picture.setImageResource(R.drawable.writing_btn_picture4);
            ImageTask imageTask = new ImageTask();
            try {
                String response = imageTask.execute("image/upload/" + userId, img4, userId).get();
                JSONObject jsonObject = new JSONObject(response);
                this.img4 = jsonObject.getString("data");
                pictureId++;
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(requestCode == GET_GALLERY_SUB4_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            selectedSub4Image = data.getData();
            writingSubImg4.setImageURI(selectedSub4Image);
            img5 = createCopyAndReturnRealPath(getApplicationContext(), selectedSub4Image);
            System.out.println("아이디:" + userId);
            System.out.println("이미지 경로: " + img5);
            subImgFrame4.setVisibility(View.VISIBLE);
            subImg4Delete.setVisibility(View.VISIBLE);
            writingSubImg4.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0,0,view.getWidth(),view.getHeight(), 12);
                }
            });
            writingSubImg4.setClipToOutline(true);
            add_picture.setImageResource(R.drawable.writing_btn_picture5);
            ImageTask imageTask = new ImageTask();
            try {
                String response = imageTask.execute("image/upload/" + userId, img5, userId).get();
                JSONObject jsonObject = new JSONObject(response);
                this.img5 = jsonObject.getString("data");
                pictureId++;
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    // 절대경로 파악할 때 사용된 메소드
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
}
