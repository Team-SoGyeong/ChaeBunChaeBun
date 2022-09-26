package com.E2I3.chaebunchaebun;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Outline;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class CommunityChangeActivity extends AppCompatActivity {
    private final int GET_GALLERY_MAIN_IMAGE = 200;
    private final int GET_GALLERY_SUB1_IMAGE = 201;
    private final int GET_GALLERY_SUB2_IMAGE = 202;
    private final int GET_GALLERY_SUB3_IMAGE = 203;
    private final int GET_GALLERY_SUB4_IMAGE = 204;
    private TextView toastText;
    private Toast toast;

    HorizontalScrollView picture_view;
    LinearLayout mainImgFrame, subImgFrame1, subImgFrame2, subImgFrame3, subImgFrame4;
    ImageButton add_picture, writing;
    ImageView btn_back, changeMainImg, changeSubImg1, changeSubImg2,
            changeSubImg3, changeSubImg4, mainImgDelete, subImg1Delete, subImg2Delete,
            subImg3Delete, subImg4Delete;
    EditText inputContent;
    String userId = null, postId = null;
    String content, img1, img2, img3, img4, img5;

    String delete_arr[];
    Uri selectedMainImage, selectedSub1Image, selectedSub2Image, selectedSub3Image, selectedSub4Image;

    int locationCode = 0;
    int pictureId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_change_frame);

        LayoutInflater inflater = getLayoutInflater();
        View customToast = inflater.inflate(R.layout.custom_report_toast, (ViewGroup) findViewById(R.id.custom_toast_layout));

        toastText = (TextView) customToast.findViewById(R.id.custom_toast_text);
        toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(customToast);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        postId = intent.getStringExtra("postId");
        System.out.println("아이디:" + userId);

        inputContent = (EditText) findViewById(R.id.input_content);

        add_picture = (ImageButton) findViewById(R.id.add_picture);
        picture_view = (HorizontalScrollView) findViewById(R.id.community_picture_view);
        mainImgFrame = (LinearLayout) findViewById(R.id.main_img_frame);
        subImgFrame1 = (LinearLayout) findViewById(R.id.sub_img1_frame);
        subImgFrame2 = (LinearLayout) findViewById(R.id.sub_img2_frame);
        subImgFrame3 = (LinearLayout) findViewById(R.id.sub_img3_frame);
        subImgFrame4 = (LinearLayout) findViewById(R.id.sub_img4_frame);

        mainImgDelete = (ImageView) findViewById(R.id.etc_main_img_delete);
        subImg1Delete = (ImageView) findViewById(R.id.etc_sub_img1_delete);
        subImg2Delete = (ImageView) findViewById(R.id.etc_sub_img2_delete);
        subImg3Delete = (ImageView) findViewById(R.id.etc_sub_img3_delete);
        subImg4Delete = (ImageView) findViewById(R.id.etc_sub_img4_delete);

        changeMainImg = (ImageView) findViewById(R.id.etc_main_img);
        changeSubImg1 = (ImageView) findViewById(R.id.etc_sub_img1);
        changeSubImg2 = (ImageView) findViewById(R.id.etc_sub_img2);
        changeSubImg3 = (ImageView) findViewById(R.id.etc_sub_img3);
        changeSubImg4 = (ImageView) findViewById(R.id.etc_sub_img4);

        btn_back = (ImageView) findViewById(R.id.community_change_back);
        writing = (ImageButton) findViewById(R.id.community_change_iscomplete);

        changeMainImg.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0,0,view.getWidth(),view.getHeight(), 12);
            }
        });
        changeMainImg.setClipToOutline(true);
        changeSubImg1.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0,0,view.getWidth(),view.getHeight(), 12);
            }
        });
        changeSubImg1.setClipToOutline(true);
        changeSubImg2.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0,0,view.getWidth(),view.getHeight(), 12);
            }
        });
        changeSubImg2.setClipToOutline(true);
        changeSubImg3.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0,0,view.getWidth(),view.getHeight(), 12);
            }
        });
        changeSubImg3.setClipToOutline(true);
        changeSubImg4.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0,0,view.getWidth(),view.getHeight(), 12);
            }
        });
        changeSubImg4.setClipToOutline(true);

        getPostList();

        if(img1.isEmpty() || img1 == null || img1.equals("null")){
            add_picture.setImageResource(R.drawable.communitywriting_btn_picture1);
        } else if(img2.isEmpty() || img2 == null || img2.equals("null")){
            mainImgFrame.setVisibility(View.VISIBLE);
            mainImgDelete.setVisibility(View.VISIBLE);
            add_picture.setImageResource(R.drawable.communitywriting_btn_picture1);
        } else if(img3.isEmpty() || img3 == null || img3.equals("null")){
            mainImgFrame.setVisibility(View.VISIBLE);
            mainImgDelete.setVisibility(View.VISIBLE);
            subImgFrame1.setVisibility(View.VISIBLE);
            subImg1Delete.setVisibility(View.VISIBLE);
            add_picture.setImageResource(R.drawable.communitywriting_btn_picture2);
        } else if(img4.isEmpty() || img4 == null || img4.equals("null")){
            mainImgFrame.setVisibility(View.VISIBLE);
            mainImgDelete.setVisibility(View.VISIBLE);
            subImgFrame1.setVisibility(View.VISIBLE);
            subImg1Delete.setVisibility(View.VISIBLE);
            subImgFrame2.setVisibility(View.VISIBLE);
            subImg2Delete.setVisibility(View.VISIBLE);
            add_picture.setImageResource(R.drawable.communitywriting_btn_picture3);
        } else if(img5.isEmpty() || img5 == null || img5.equals("null")){
            mainImgFrame.setVisibility(View.VISIBLE);
            mainImgDelete.setVisibility(View.VISIBLE);
            subImgFrame1.setVisibility(View.VISIBLE);
            subImg1Delete.setVisibility(View.VISIBLE);
            subImgFrame2.setVisibility(View.VISIBLE);
            subImg2Delete.setVisibility(View.VISIBLE);
            subImgFrame3.setVisibility(View.VISIBLE);
            subImg3Delete.setVisibility(View.VISIBLE);
            add_picture.setImageResource(R.drawable.communitywriting_btn_picture4);
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
            add_picture.setImageResource(R.drawable.communitywriting_btn_picture5);
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
                        add_picture.setImageResource(R.drawable.communitywriting_btn_picture);
                        delete_arr = img1.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img1 = null;
                        mainImgFrame.setVisibility(View.GONE);
                        mainImgDelete.setVisibility(View.GONE);
                        break;
                    case 1:
                        add_picture.setImageResource(R.drawable.communitywriting_btn_picture1);
                        delete_arr = img1.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img1 = img2;
                        img2 = null;
                        Glide.with(getApplicationContext()).load(img1).into(changeMainImg);
                        subImgFrame1.setVisibility(View.GONE);
                        subImg1Delete.setVisibility(View.GONE);
                        break;
                    case 2:
                        add_picture.setImageResource(R.drawable.communitywriting_btn_picture2);
                        delete_arr = img1.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img1 = img2;
                        img2 = img3;
                        img3 = null;
                        Glide.with(getApplicationContext()).load(img1).into(changeMainImg);
                        Glide.with(getApplicationContext()).load(img2).into(changeSubImg1);
                        subImgFrame2.setVisibility(View.GONE);
                        subImg2Delete.setVisibility(View.GONE);
                        break;
                    case 3:
                        add_picture.setImageResource(R.drawable.communitywriting_btn_picture3);
                        delete_arr = img1.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img1 = img2;
                        img2 = img3;
                        img3 = img4;
                        img4 = null;
                        Glide.with(getApplicationContext()).load(img1).into(changeMainImg);
                        Glide.with(getApplicationContext()).load(img2).into(changeSubImg1);
                        Glide.with(getApplicationContext()).load(img3).into(changeSubImg2);
                        subImgFrame3.setVisibility(View.GONE);
                        subImg3Delete.setVisibility(View.GONE);
                        break;
                    case 4:
                        add_picture.setImageResource(R.drawable.communitywriting_btn_picture4);
                        delete_arr = img1.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img1 = img2;
                        img2 = img3;
                        img3 = img4;
                        img4 = img5;
                        img5 = null;
                        Glide.with(getApplicationContext()).load(img1).into(changeMainImg);
                        Glide.with(getApplicationContext()).load(img2).into(changeSubImg1);
                        Glide.with(getApplicationContext()).load(img3).into(changeSubImg2);
                        Glide.with(getApplicationContext()).load(img4).into(changeSubImg3);
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
                        add_picture.setImageResource(R.drawable.communitywriting_btn_picture1);
                        delete_arr = img2.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img2 = null;
                        subImgFrame1.setVisibility(View.GONE);
                        subImg1Delete.setVisibility(View.GONE);
                        break;
                    case 2:
                        add_picture.setImageResource(R.drawable.communitywriting_btn_picture2);
                        delete_arr = img2.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img2 = img3;
                        img3 = null;
                        Glide.with(getApplicationContext()).load(img2).into(changeSubImg1);
                        subImgFrame2.setVisibility(View.GONE);
                        subImg2Delete.setVisibility(View.GONE);
                        break;
                    case 3:
                        add_picture.setImageResource(R.drawable.communitywriting_btn_picture3);
                        delete_arr = img2.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img2 = img3;
                        img3 = img4;
                        img4 = null;
                        Glide.with(getApplicationContext()).load(img2).into(changeSubImg1);
                        Glide.with(getApplicationContext()).load(img3).into(changeSubImg2);
                        subImgFrame3.setVisibility(View.GONE);
                        subImg3Delete.setVisibility(View.GONE);
                        break;
                    case 4:
                        add_picture.setImageResource(R.drawable.communitywriting_btn_picture4);
                        delete_arr = img2.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img2 = img3;
                        img3 = img4;
                        img4 = img5;
                        img5 = null;
                        Glide.with(getApplicationContext()).load(img2).into(changeSubImg1);
                        Glide.with(getApplicationContext()).load(img3).into(changeSubImg2);
                        Glide.with(getApplicationContext()).load(img4).into(changeSubImg3);
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
                        add_picture.setImageResource(R.drawable.communitywriting_btn_picture2);
                        delete_arr = img3.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img3 = null;
                        subImgFrame2.setVisibility(View.GONE);
                        subImg2Delete.setVisibility(View.GONE);
                        break;
                    case 3:
                        add_picture.setImageResource(R.drawable.communitywriting_btn_picture3);
                        delete_arr = img3.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img3 = img4;
                        img4 = null;
                        Glide.with(getApplicationContext()).load(img3).into(changeSubImg2);
                        subImgFrame3.setVisibility(View.GONE);
                        subImg3Delete.setVisibility(View.GONE);
                        break;
                    case 4:
                        add_picture.setImageResource(R.drawable.communitywriting_btn_picture4);
                        delete_arr = img3.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img3 = img4;
                        img4 = img5;
                        img5 = null;
                        Glide.with(getApplicationContext()).load(img3).into(changeSubImg2);
                        Glide.with(getApplicationContext()).load(img4).into(changeSubImg3);
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
                        add_picture.setImageResource(R.drawable.communitywriting_btn_picture3);
                        delete_arr = img4.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img4 = null;
                        subImgFrame3.setVisibility(View.GONE);
                        subImg3Delete.setVisibility(View.GONE);
                        break;
                    case 4:
                        add_picture.setImageResource(R.drawable.communitywriting_btn_picture4);
                        delete_arr = img4.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img4 = img5;
                        img5 = null;
                        Glide.with(getApplicationContext()).load(img4).into(changeSubImg3);
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
                        add_picture.setImageResource(R.drawable.communitywriting_btn_picture4);
                        delete_arr = img5.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img5 = null;
                        subImgFrame4.setVisibility(View.GONE);
                        subImg4Delete.setVisibility(View.GONE);
                        break;
                }
            }
        });

        writing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content = inputContent.getText().toString();

                if(inputContent.getText().toString().isEmpty()) {
                    toastText.setText("입력되지 않은 칸이 있어요!");
                    toast.show();
                }else {
                    PutTask putTask = new PutTask();
                    JSONObject jsonChangeTransfer = new JSONObject();

                    try {
                        jsonChangeTransfer.put("content", content);
                        String imgString = "{\"img1\": \"" + img1 + "\"," + " \"img2\": \"" + img2 + "\", \"img3\": \"" + img3 + "\", \"img4\": \"" + img4 + "\", \"img4\": \"" + img4 + "\", \"img5\": \"" + img5 + "\"}";
                        JSONObject imgs = new JSONObject(imgString);
                        jsonChangeTransfer.put("imgs", imgs);

                        String jsonString = jsonChangeTransfer.toString();
                        String resultText = "[NULL]";

                        try {
                            resultText = putTask.execute("community/" + postId + "/" + userId, jsonString).get();
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
                                toastText.setText("수정에 실패하였습니다!");
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
    }

    public void getPostList() {
        String resultText = "[NULL]";

        try {
            resultText = new GetTask("community/detail/" + this.postId + "/" + this.userId).execute().get();
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

                content = subJsonObject.getString("content");
                inputContent.setText(content);

                img1 = subJsonObject.getString("img1");
                img2 = subJsonObject.getString("img2");
                img3 = subJsonObject.getString("img3");
                img4 = subJsonObject.getString("img4");
                img5 = subJsonObject.getString("img5");

                if(img1.isEmpty() || img1 == null || img1.equals("null")){
                    pictureId = 0;
                } else if(img2.isEmpty() || img2 == null || img2.equals("null")){
                    Glide.with(getApplicationContext()).load(img1).into(changeMainImg);
                    pictureId = 1;
                } else if(img3.isEmpty() || img3 == null || img3.equals("null")){
                    Glide.with(getApplicationContext()).load(img1).into(changeMainImg);
                    Glide.with(getApplicationContext()).load(img2).into(changeSubImg1);
                    pictureId = 2;
                } else if(img4.isEmpty() || img4 == null || img4.equals("null")){
                    Glide.with(getApplicationContext()).load(img1).into(changeMainImg);
                    Glide.with(getApplicationContext()).load(img2).into(changeSubImg1);
                    Glide.with(getApplicationContext()).load(img3).into(changeSubImg2);
                    pictureId = 3;
                } else if(img5.isEmpty() || img5 == null || img5.equals("null")){
                    Glide.with(getApplicationContext()).load(img1).into(changeMainImg);
                    Glide.with(getApplicationContext()).load(img2).into(changeSubImg1);
                    Glide.with(getApplicationContext()).load(img3).into(changeSubImg2);
                    Glide.with(getApplicationContext()).load(img4).into(changeSubImg3);
                    pictureId = 4;
                } else {
                    Glide.with(getApplicationContext()).load(img1).into(changeMainImg);
                    Glide.with(getApplicationContext()).load(img2).into(changeSubImg1);
                    Glide.with(getApplicationContext()).load(img3).into(changeSubImg2);
                    Glide.with(getApplicationContext()).load(img4).into(changeSubImg3);
                    Glide.with(getApplicationContext()).load(img5).into(changeSubImg4);
                    pictureId = 5;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void deleteImg(String filename) {
        PostTask deleteImgTask = new PostTask();
        deleteImgTask.execute("image/delete/" + userId +"/" + filename, filename);
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
            changeMainImg.setImageURI(selectedMainImage);
            img1 = createCopyAndReturnRealPath(getApplicationContext(), selectedMainImage);
            System.out.println("아이디:" + userId);
            System.out.println("이미지 경로: " + img1);
            mainImgFrame.setVisibility(View.VISIBLE);
            mainImgDelete.setVisibility(View.VISIBLE);
            changeMainImg.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0,0,view.getWidth(),view.getHeight(), 12);
                }
            });
            changeMainImg.setClipToOutline(true);
            add_picture.setImageResource(R.drawable.communitywriting_btn_picture1);
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
            changeSubImg1.setImageURI(selectedSub1Image);
            img2 = createCopyAndReturnRealPath(getApplicationContext(), selectedSub1Image);
            System.out.println("아이디:" + userId);
            System.out.println("이미지 경로: " + img2);
            subImgFrame1.setVisibility(View.VISIBLE);
            subImg1Delete.setVisibility(View.VISIBLE);
            changeSubImg1.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0,0,view.getWidth(),view.getHeight(), 12);
                }
            });
            changeSubImg1.setClipToOutline(true);
            add_picture.setImageResource(R.drawable.communitywriting_btn_picture2);
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
            changeSubImg2.setImageURI(selectedSub2Image);
            img3 = createCopyAndReturnRealPath(getApplicationContext(), selectedSub2Image);
            System.out.println("아이디:" + userId);
            System.out.println("이미지 경로: " + img3);
            subImgFrame2.setVisibility(View.VISIBLE);
            subImg2Delete.setVisibility(View.VISIBLE);
            changeSubImg2.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0,0,view.getWidth(),view.getHeight(), 12);
                }
            });
            changeSubImg2.setClipToOutline(true);
            add_picture.setImageResource(R.drawable.communitywriting_btn_picture3);
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
            changeSubImg3.setImageURI(selectedSub3Image);
            img4 = createCopyAndReturnRealPath(getApplicationContext(), selectedSub3Image);
            System.out.println("아이디:" + userId);
            System.out.println("이미지 경로: " + img4);
            subImgFrame3.setVisibility(View.VISIBLE);
            subImg3Delete.setVisibility(View.VISIBLE);
            changeSubImg3.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0,0,view.getWidth(),view.getHeight(), 12);
                }
            });
            changeSubImg3.setClipToOutline(true);
            add_picture.setImageResource(R.drawable.communitywriting_btn_picture4);
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
            changeSubImg4.setImageURI(selectedSub4Image);
            img5 = createCopyAndReturnRealPath(getApplicationContext(), selectedSub4Image);
            System.out.println("아이디:" + userId);
            System.out.println("이미지 경로: " + img5);
            subImgFrame4.setVisibility(View.VISIBLE);
            subImg4Delete.setVisibility(View.VISIBLE);
            changeSubImg4.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0,0,view.getWidth(),view.getHeight(), 12);
                }
            });
            changeSubImg4.setClipToOutline(true);
            add_picture.setImageResource(R.drawable.communitywriting_btn_picture5);
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