package com.E2I3.chaebunchaebun;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Outline;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.AdapterView;
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
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutionException;

public class CommunityWritingActivity extends AppCompatActivity {
    private final int GET_GALLERY_MAIN_IMAGE = 200;
    private final int GET_GALLERY_SUB1_IMAGE = 201;
    private final int GET_GALLERY_SUB2_IMAGE = 202;
    private final int GET_GALLERY_SUB3_IMAGE = 203;
    private final int GET_GALLERY_SUB4_IMAGE = 204;
    private TextView toastText;
    private Toast toast;

    HorizontalScrollView picture_view;
    BottomNavigationView none_picture_view;
    LinearLayout mainImgFrame, subImgFrame1, subImgFrame2, subImgFrame3, subImgFrame4, add_picture_view;
    ImageButton add_picture, add_picture_none, writing;
    ImageView back, writingMainImg, writingSubImg1, writingSubImg2,
            writingSubImg3, writingSubImg4, writingBillImg1, writingBillImg2, mainImgDelete, subImg1Delete, subImg2Delete,
            subImg3Delete, subImg4Delete;
    EditText inputContent;
    String img1, img2, img3, img4, img5, userId = null;

    String delete_arr[];
    Uri selectedMainImage, selectedSub1Image, selectedSub2Image, selectedSub3Image, selectedSub4Image;

    int locationCode = 0;
    int pictureId = 0;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_writing_frame);

        LayoutInflater inflater = getLayoutInflater();
        View customToast = inflater.inflate(R.layout.custom_report_toast, (ViewGroup) findViewById(R.id.custom_toast_layout));

        toastText = (TextView) customToast.findViewById(R.id.custom_toast_text);
        toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(customToast);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        locationCode = intent.getIntExtra("locationCode",0);
        System.out.println("아이디:" + userId);

        inputContent = (EditText) findViewById(R.id.input_content);

        add_picture_none = (ImageButton) findViewById(R.id.community_btn_picture_none);
        add_picture = (ImageButton) findViewById(R.id.add_picture);
        none_picture_view = (BottomNavigationView) findViewById(R.id.community_writing_picture_none);
        add_picture_view = (LinearLayout) findViewById(R.id.community_writing_picture);
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

        writingMainImg = (ImageView) findViewById(R.id.etc_main_img);
        writingSubImg1 = (ImageView) findViewById(R.id.etc_sub_img1);
        writingSubImg2 = (ImageView) findViewById(R.id.etc_sub_img2);
        writingSubImg3 = (ImageView) findViewById(R.id.etc_sub_img3);
        writingSubImg4 = (ImageView) findViewById(R.id.etc_sub_img4);

        add_picture_none.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                none_picture_view.setVisibility(View.GONE);
                add_picture_view.setVisibility(View.VISIBLE);
                picture_view.setVisibility(View.VISIBLE);

                Intent mainImgIntent = new Intent(Intent.ACTION_PICK);
                mainImgIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(mainImgIntent, GET_GALLERY_MAIN_IMAGE);
            }
        });
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
                        selectedMainImage = null;
                        delete_arr = img1.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img1 = null;
                        mainImgFrame.setVisibility(View.GONE);
                        mainImgDelete.setVisibility(View.GONE);
                        break;
                    case 1:
                        add_picture.setImageResource(R.drawable.communitywriting_btn_picture1);
                        selectedMainImage = selectedSub1Image;
                        selectedSub1Image = null;
                        delete_arr = img1.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img1 = img2;
                        img2 = null;
                        writingMainImg.setImageURI(selectedMainImage);
                        subImgFrame1.setVisibility(View.GONE);
                        subImg1Delete.setVisibility(View.GONE);
                        break;
                    case 2:
                        add_picture.setImageResource(R.drawable.communitywriting_btn_picture2);
                        selectedMainImage = selectedSub1Image;
                        selectedSub1Image = selectedSub2Image;
                        selectedSub2Image = null;
                        delete_arr = img1.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img1 = img2;
                        img2 = img3;
                        img3 = null;
                        writingMainImg.setImageURI(selectedMainImage);
                        writingSubImg1.setImageURI(selectedSub1Image);
                        subImgFrame2.setVisibility(View.GONE);
                        subImg2Delete.setVisibility(View.GONE);
                        break;
                    case 3:
                        add_picture.setImageResource(R.drawable.communitywriting_btn_picture3);
                        selectedMainImage = selectedSub1Image;
                        selectedSub1Image = selectedSub2Image;
                        selectedSub2Image = selectedSub3Image;
                        selectedSub3Image = null;
                        delete_arr = img1.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img1 = img2;
                        img2 = img3;
                        img3 = img4;
                        img4 = null;
                        writingMainImg.setImageURI(selectedMainImage);
                        writingSubImg1.setImageURI(selectedSub1Image);
                        writingSubImg2.setImageURI(selectedSub2Image);
                        subImgFrame3.setVisibility(View.GONE);
                        subImg3Delete.setVisibility(View.GONE);
                        break;
                    case 4:
                        add_picture.setImageResource(R.drawable.communitywriting_btn_picture4);
                        selectedMainImage = selectedSub1Image;
                        selectedSub1Image = selectedSub2Image;
                        selectedSub2Image = selectedSub3Image;
                        selectedSub3Image = selectedSub4Image;
                        selectedSub4Image = null;
                        delete_arr = img1.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img1 = img2;
                        img2 = img3;
                        img3 = img4;
                        img4 = img5;
                        img5 = null;
                        writingMainImg.setImageURI(selectedMainImage);
                        writingSubImg1.setImageURI(selectedSub1Image);
                        writingSubImg2.setImageURI(selectedSub2Image);
                        writingSubImg3.setImageURI(selectedSub3Image);
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
                        selectedSub1Image = null;
                        delete_arr = img2.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img2 = null;
                        subImgFrame1.setVisibility(View.GONE);
                        subImg1Delete.setVisibility(View.GONE);
                        break;
                    case 2:
                        add_picture.setImageResource(R.drawable.communitywriting_btn_picture2);
                        selectedSub1Image = selectedSub2Image;
                        selectedSub2Image = null;
                        delete_arr = img2.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img2 = img3;
                        img3 = null;
                        writingSubImg1.setImageURI(selectedSub1Image);
                        subImgFrame2.setVisibility(View.GONE);
                        subImg2Delete.setVisibility(View.GONE);
                        break;
                    case 3:
                        add_picture.setImageResource(R.drawable.communitywriting_btn_picture3);
                        selectedSub1Image = selectedSub2Image;
                        selectedSub2Image = selectedSub3Image;
                        selectedSub3Image = null;
                        delete_arr = img2.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img2 = img3;
                        img3 = img4;
                        img4 = null;
                        writingSubImg1.setImageURI(selectedSub1Image);
                        writingSubImg2.setImageURI(selectedSub2Image);
                        subImgFrame3.setVisibility(View.GONE);
                        subImg3Delete.setVisibility(View.GONE);
                        break;
                    case 4:
                        add_picture.setImageResource(R.drawable.communitywriting_btn_picture4);
                        selectedSub1Image = selectedSub2Image;
                        selectedSub2Image = selectedSub3Image;
                        selectedSub3Image = selectedSub4Image;
                        selectedSub4Image = null;
                        delete_arr = img2.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img2 = img3;
                        img3 = img4;
                        img4 = img5;
                        img5 = null;
                        writingSubImg1.setImageURI(selectedSub1Image);
                        writingSubImg2.setImageURI(selectedSub2Image);
                        writingSubImg3.setImageURI(selectedSub3Image);
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
                        selectedSub2Image = null;
                        delete_arr = img3.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img3 = null;
                        subImgFrame2.setVisibility(View.GONE);
                        subImg2Delete.setVisibility(View.GONE);
                        break;
                    case 3:
                        add_picture.setImageResource(R.drawable.communitywriting_btn_picture3);
                        selectedSub2Image = selectedSub3Image;
                        selectedSub3Image = null;
                        delete_arr = img3.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img3 = img4;
                        img4 = null;
                        writingSubImg2.setImageURI(selectedSub2Image);
                        subImgFrame3.setVisibility(View.GONE);
                        subImg3Delete.setVisibility(View.GONE);
                        break;
                    case 4:
                        add_picture.setImageResource(R.drawable.communitywriting_btn_picture4);
                        selectedSub2Image = selectedSub3Image;
                        selectedSub3Image = selectedSub4Image;
                        selectedSub4Image = null;
                        delete_arr = img3.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img3 = img4;
                        img4 = img5;
                        img5 = null;
                        writingSubImg2.setImageURI(selectedSub2Image);
                        writingSubImg3.setImageURI(selectedSub3Image);
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
                        selectedSub3Image = null;
                        delete_arr = img4.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img4 = null;
                        subImgFrame3.setVisibility(View.GONE);
                        subImg3Delete.setVisibility(View.GONE);
                        break;
                    case 4:
                        add_picture.setImageResource(R.drawable.communitywriting_btn_picture4);
                        selectedSub3Image = selectedSub4Image;
                        selectedSub4Image = null;
                        delete_arr = img4.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img4 = img5;
                        img5 = null;
                        writingSubImg3.setImageURI(selectedSub3Image);
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
                        selectedSub4Image = null;
                        delete_arr = img5.split("/");
                        deleteImg(delete_arr[delete_arr.length - 1]);
                        img5 = null;
                        subImgFrame4.setVisibility(View.GONE);
                        subImg4Delete.setVisibility(View.GONE);
                        break;
                }
            }
        });

        //글 쓰기 내용 칸
        inputContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String input = inputContent.getText().toString();
                if(input.isEmpty())
                    writing.setImageResource(R.drawable.communitywriting_btn_uncomplete);
                else
                    writing.setImageResource(R.drawable.communitywriting_btn_complete);
            }
        });

        //버튼 이벤트
        back = (ImageView) findViewById(R.id.id_back);
        writing = (ImageButton) findViewById(R.id.community_iscomplete);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });
        writing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(inputContent.getText().toString().isEmpty()) {
                    toastText.setText("내용이 입력되지 않았요!");
                    toast.show();
                }else {
                    PostTask postTask = new PostTask();
                    JSONObject jsonPostTransfer = new JSONObject();
                    try {
                        jsonPostTransfer.put("content", inputContent.getText().toString());
                        String imgString = "{\"img1\": \"" + img1 + "\"," + " \"img2\": \"" + img2 + "\", \"img3\": \"" + img3 + "\", \"img4\": \"" + img4 + "\", \"img4\": \"" + img4 + "\", \"img5\": \"" + img5 + "\"}";
                        JSONObject imgs = new JSONObject(imgString);
                       jsonPostTransfer.put("imgs", imgs);

                        String jsonString = jsonPostTransfer.toString();
                        System.out.println(jsonString);
                        postTask.execute("community/" + userId, jsonString);

                        Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                        intent.putExtra("userId", userId);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        Toast.makeText(getApplicationContext(), "글쓰기 완료", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                        onStop();
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
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
