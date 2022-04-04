package com.E2I3.chaebunchaebun;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Outline;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutionException;

public class WritingChaebunActivity extends AppCompatActivity {
    private final int GET_GALLERY_MAIN_IMAGE = 200;
    private final int GET_GALLERY_SUB1_IMAGE = 201;
    private final int GET_GALLERY_SUB2_IMAGE = 202;
    private final int GET_GALLERY_SUB3_IMAGE = 203;
    private final int GET_GALLERY_SUB4_IMAGE = 204;
    private final int GET_GALLERY_BILL1_IMAGE = 205;
    private final int GET_GALLERY_BILL2_IMAGE = 206;
    int pictureId = 0;
    int billId = 0;
    private TextView toastText;
    private Toast toast;

    HorizontalScrollView picture_view, bill_view;
    LinearLayout mainImgFrame, subImgFrame1, subImgFrame2, subImgFrame3, subImgFrame4, billImgFrame1, billImgFrame2;
    ImageButton add_picture, add_receipt;
    ImageView back, writing, btn_back, writingMainImg, writingSubImg1, writingSubImg2,
            writingSubImg3, writingSubImg4, writingBillImg1, writingBillImg2, mainImgDelete, subImg1Delete, subImg2Delete,
            subImg3Delete, subImg4Delete, billImg1Delete, billImg2Delete;
    TextView inputContentCount;
    EditText inputTitle, inputContent, inputAmount, inputGetPrice, inputCall;
    Spinner date_spinner, amount_spinner;
    String date_arr[] = {"1일 전", "2일 전", "3일 전", "일주일 이내", "2주 이내"};
    String amount_arr[] ={"kg","g","개"};
    String str, amount_str, userId = null;
    String amount = "1";
    String mainImg, subImg1, subImg2, subImg3, subImg4, billImg1, billImg2 = null;

    Uri selectedMainImage, selectedSub1Image, selectedSub2Image, selectedSub3Image, selectedSub4Image, selectedBill1Image, selectedBill2Image;
    int categoryId;
    int locationCode = 0;
    boolean isAmount = false;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writing_chaebun_frame);

        LayoutInflater inflater = getLayoutInflater();
        View customToast = inflater.inflate(R.layout.custom_report_toast, (ViewGroup) findViewById(R.id.custom_toast_layout));

        toastText = (TextView) customToast.findViewById(R.id.custom_toast_text);
        toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(customToast);

        Intent intent = getIntent();
        categoryId = intent.getIntExtra("categoryId", 0);
        userId = intent.getStringExtra("userId");
        locationCode = intent.getIntExtra("locationCode",0);
        System.out.println("아이디:" + userId);

        inputTitle = (EditText) findViewById(R.id.input_title);
        inputContent = (EditText) findViewById(R.id.input_content);
        inputAmount = (EditText) findViewById(R.id.input_amount);
        inputGetPrice = (EditText) findViewById(R.id.input_getPrice);
        inputCall = (EditText) findViewById(R.id.input_call);

        inputContentCount = (TextView) findViewById(R.id.input_content_count);

        add_picture = (ImageButton) findViewById(R.id.add_picture);
        add_receipt = (ImageButton) findViewById(R.id.add_receipt);
        picture_view = (HorizontalScrollView) findViewById(R.id.picture_view);
        bill_view = (HorizontalScrollView) findViewById(R.id.bill_view);
        mainImgFrame = (LinearLayout) findViewById(R.id.main_img_frame);
        subImgFrame1 = (LinearLayout) findViewById(R.id.sub_img1_frame);
        subImgFrame2 = (LinearLayout) findViewById(R.id.sub_img2_frame);
        subImgFrame3 = (LinearLayout) findViewById(R.id.sub_img3_frame);
        subImgFrame4 = (LinearLayout) findViewById(R.id.sub_img4_frame);
        billImgFrame1 = (LinearLayout) findViewById(R.id.bill_img1_frame);
        billImgFrame2 = (LinearLayout) findViewById(R.id.bill_img2_frame);

        mainImgDelete = (ImageView) findViewById(R.id.etc_main_img_delete);
        subImg1Delete = (ImageView) findViewById(R.id.etc_sub_img1_delete);
        subImg2Delete = (ImageView) findViewById(R.id.etc_sub_img2_delete);
        subImg3Delete = (ImageView) findViewById(R.id.etc_sub_img3_delete);
        subImg4Delete = (ImageView) findViewById(R.id.etc_sub_img4_delete);
        billImg1Delete = (ImageView) findViewById(R.id.etc_bill1_img_delete);
        billImg2Delete = (ImageView) findViewById(R.id.etc_bill2_img_delete);

        writingMainImg = (ImageView) findViewById(R.id.etc_main_img);
        writingSubImg1 = (ImageView) findViewById(R.id.etc_sub_img1);
        writingSubImg2 = (ImageView) findViewById(R.id.etc_sub_img2);
        writingSubImg3 = (ImageView) findViewById(R.id.etc_sub_img3);
        writingSubImg4 = (ImageView) findViewById(R.id.etc_sub_img4);
        writingBillImg1 = (ImageView) findViewById(R.id.etc_bill1_img);
        writingBillImg2 = (ImageView) findViewById(R.id.etc_bill2_img);

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
        add_receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bill_view.setVisibility(View.VISIBLE);
                switch (billId){
                    case 0:
                        Intent bill1ImgIntent = new Intent(Intent.ACTION_PICK);
                        bill1ImgIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(bill1ImgIntent, GET_GALLERY_BILL1_IMAGE);
                        break;
                    case 1:
                        Intent bill2ImgIntent = new Intent(Intent.ACTION_PICK);
                        bill2ImgIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(bill2ImgIntent, GET_GALLERY_BILL2_IMAGE);
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
                        selectedMainImage = null;
                        mainImg = null;
                        mainImgFrame.setVisibility(View.GONE);
                        mainImgDelete.setVisibility(View.GONE);
                        break;
                    case 1:
                        add_picture.setImageResource(R.drawable.writing_btn_picture1);
                        selectedMainImage = selectedSub1Image;
                        selectedSub1Image = null;
                        mainImg = subImg1;
                        subImg1 = null;
                        writingMainImg.setImageURI(selectedMainImage);
                        subImgFrame1.setVisibility(View.GONE);
                        subImg1Delete.setVisibility(View.GONE);
                        break;
                    case 2:
                        add_picture.setImageResource(R.drawable.writing_btn_picture2);
                        selectedMainImage = selectedSub1Image;
                        selectedSub1Image = selectedSub2Image;
                        selectedSub2Image = null;
                        mainImg = subImg1;
                        subImg1 = subImg2;
                        subImg2 = null;
                        writingMainImg.setImageURI(selectedMainImage);
                        writingSubImg1.setImageURI(selectedSub1Image);
                        subImgFrame2.setVisibility(View.GONE);
                        subImg2Delete.setVisibility(View.GONE);
                        break;
                    case 3:
                        add_picture.setImageResource(R.drawable.writing_btn_picture3);
                        selectedMainImage = selectedSub1Image;
                        selectedSub1Image = selectedSub2Image;
                        selectedSub2Image = selectedSub3Image;
                        writingMainImg.setImageURI(selectedMainImage);
                        writingSubImg1.setImageURI(selectedSub1Image);
                        writingSubImg2.setImageURI(selectedSub2Image);
                        selectedSub3Image = null;
                        mainImg = subImg1;
                        subImg1 = subImg2;
                        subImg2 = subImg3;
                        subImg3 = null;
                        subImgFrame3.setVisibility(View.GONE);
                        subImg3Delete.setVisibility(View.GONE);
                        break;
                    case 4:
                        add_picture.setImageResource(R.drawable.writing_btn_picture4);
                        selectedMainImage = selectedSub1Image;
                        selectedSub1Image = selectedSub2Image;
                        selectedSub2Image = selectedSub3Image;
                        selectedSub3Image = selectedSub4Image;
                        selectedSub4Image = null;
                        mainImg = subImg1;
                        subImg1 = subImg2;
                        subImg2 = subImg3;
                        subImg3 = subImg4;
                        subImg4 = null;
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
                        add_picture.setImageResource(R.drawable.writing_btn_picture1);
                        selectedSub1Image = null;
                        subImg1 = null;
                        subImgFrame1.setVisibility(View.GONE);
                        subImg1Delete.setVisibility(View.GONE);
                        break;
                    case 2:
                        add_picture.setImageResource(R.drawable.writing_btn_picture2);
                        selectedSub1Image = selectedSub2Image;
                        selectedSub2Image = null;
                        subImg1 = subImg2;
                        subImg2 = null;
                        writingSubImg1.setImageURI(selectedSub1Image);
                        subImgFrame2.setVisibility(View.GONE);
                        subImg2Delete.setVisibility(View.GONE);
                        break;
                    case 3:
                        add_picture.setImageResource(R.drawable.writing_btn_picture3);
                        selectedSub1Image = selectedSub2Image;
                        selectedSub2Image = selectedSub3Image;
                        selectedSub3Image = null;
                        subImg1 = subImg2;
                        subImg2 = subImg3;
                        subImg3 = null;
                        writingSubImg1.setImageURI(selectedSub1Image);
                        writingSubImg2.setImageURI(selectedSub2Image);
                        subImgFrame3.setVisibility(View.GONE);
                        subImg3Delete.setVisibility(View.GONE);
                        break;
                    case 4:
                        add_picture.setImageResource(R.drawable.writing_btn_picture4);
                        selectedSub1Image = selectedSub2Image;
                        selectedSub2Image = selectedSub3Image;
                        selectedSub3Image = selectedSub4Image;
                        selectedSub4Image = null;
                        subImg1 = subImg2;
                        subImg2 = subImg3;
                        subImg3 = subImg4;
                        subImg4 = null;
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
                        add_picture.setImageResource(R.drawable.writing_btn_picture2);
                        selectedSub2Image = null;
                        subImg2 = null;
                        subImgFrame2.setVisibility(View.GONE);
                        subImg2Delete.setVisibility(View.GONE);
                        break;
                    case 3:
                        add_picture.setImageResource(R.drawable.writing_btn_picture3);
                        selectedSub2Image = selectedSub3Image;
                        selectedSub3Image = null;
                        subImg2 = subImg3;
                        subImg3 = null;
                        writingSubImg2.setImageURI(selectedSub2Image);
                        subImgFrame3.setVisibility(View.GONE);
                        subImg3Delete.setVisibility(View.GONE);
                        break;
                    case 4:
                        add_picture.setImageResource(R.drawable.writing_btn_picture4);
                        selectedSub2Image = selectedSub3Image;
                        selectedSub3Image = selectedSub4Image;
                        selectedSub4Image = null;
                        subImg2 = subImg3;
                        subImg3 = subImg4;
                        subImg4 = null;
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
                        add_picture.setImageResource(R.drawable.writing_btn_picture3);
                        selectedSub3Image = null;
                        subImg3 = null;
                        subImgFrame3.setVisibility(View.GONE);
                        subImg3Delete.setVisibility(View.GONE);
                        break;
                    case 4:
                        add_picture.setImageResource(R.drawable.writing_btn_picture4);
                        selectedSub3Image = selectedSub4Image;
                        selectedSub4Image = null;
                        subImg3 = subImg4;
                        subImg4 = null;
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
                        add_picture.setImageResource(R.drawable.writing_btn_picture4);
                        selectedSub4Image = null;
                        subImg4 = null;
                        subImgFrame4.setVisibility(View.GONE);
                        subImg4Delete.setVisibility(View.GONE);
                        break;
                }
            }
        });

        billImg1Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billId--;
                switch(billId){
                    case 0:
                        add_receipt.setImageResource(R.drawable.writing_btn_receipt);
                        selectedBill1Image = null;
                        billImg1 = null;
                        billImgFrame1.setVisibility(View.GONE);
                        billImg1Delete.setVisibility(View.GONE);
                        break;
                    case 1:
                        add_receipt.setImageResource(R.drawable.writing_btn_receipt1);
                        selectedBill1Image = selectedBill2Image;
                        selectedBill2Image = null;
                        billImg1 = billImg2;
                        billImg2 = null;
                        writingBillImg1.setImageURI(selectedBill1Image);
                        billImgFrame2.setVisibility(View.GONE);
                        billImg2Delete.setVisibility(View.GONE);
                        break;
                }
            }
        });
        billImg2Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billId--;
                switch(billId){
                    case 1:
                        add_receipt.setImageResource(R.drawable.writing_btn_receipt1);
                        selectedBill2Image = null;
                        billImg2 = null;
                        billImgFrame2.setVisibility(View.GONE);
                        billImg2Delete.setVisibility(View.GONE);
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
                String input = inputContent.getText().toString();
                if(input.length() <= 500){
                    inputContentCount.setText("(" + input.length() + "/500)");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(500)});
        inputAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                amount = inputAmount.getText().toString();
                isAmount = false;
            }

            @Override
            public void afterTextChanged(Editable editable) {
                isAmount = true;
            }
        });

        //스피너 이벤트
        date_spinner = (Spinner) findViewById(R.id.date_spinner2);
        amount_spinner = (Spinner) findViewById(R.id.amount_spinner2);

        ArrayAdapter<String> date_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, date_arr);
        date_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        date_spinner.setAdapter(date_adapter);
        date_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str = date_arr[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> amount_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, amount_arr);
        amount_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        amount_spinner.setAdapter(amount_adapter);
        amount_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                amount_str = amount_arr[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //버튼 이벤트
        back = (ImageView) findViewById(R.id.id_back);
        writing = (ImageView) findViewById(R.id.btn_next);
        btn_back = (ImageView) findViewById(R.id.btn_back);

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
                if(mainImg == null) {
                    toastText.setText("메인 이미지는 필수에요!");
                    toast.show();
                } else if(inputTitle.getText().toString().isEmpty() || inputContent.getText().toString().isEmpty()
                    || inputAmount.getText().toString().isEmpty() || inputCall.getText().toString().isEmpty()
                    || str.isEmpty() || amount_str.isEmpty()) {
                    toastText.setText("입력되지 않은 칸이 있어요!");
                    toast.show();
                }else {
                    Bundle args = new Bundle();
                    args.putString("userId", userId);
                    args.putInt("categoryId", categoryId);
                    args.putString("inputTitle", inputTitle.getText().toString());
                    args.putString("inputContent", inputContent.getText().toString());
                    args.putString("inputAmount", inputAmount.getText().toString());
                    args.putString("inputGetPrice", inputGetPrice.getText().toString());
                    args.putString("inputCall", inputCall.getText().toString());
                    args.putString("inputBuyDate", str);
                    args.putString("inputAmountStr", amount_str);
                    args.putString("bill1", billImg1);
                    args.putString("bill2",billImg2);
                    args.putString("img1", mainImg);
                    args.putString("img2", subImg1);
                    args.putString("img3", subImg2);
                    args.putString("img4", subImg3);
                    args.putString("img5", subImg4);
                    args.putInt("locationCode", locationCode);
                    WritingPopupDialogFragment e = WritingPopupDialogFragment.getInstance();
                    e.setArguments(args);
                    e.show(getSupportFragmentManager(), WritingPopupDialogFragment.TAG_EVENT_DIALOG);
                }
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
            mainImg = createCopyAndReturnRealPath(getApplicationContext(), selectedMainImage);
            System.out.println("아이디:" + userId);
            System.out.println("이미지 경로: " + mainImg);
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
                String response = imageTask.execute("image/upload/" + userId, mainImg, userId).get();
                JSONObject jsonObject = new JSONObject(response);
                this.mainImg = jsonObject.getString("data");
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
            subImg1 = createCopyAndReturnRealPath(getApplicationContext(), selectedSub1Image);
            System.out.println("아이디:" + userId);
            System.out.println("이미지 경로: " + mainImg);
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
                String response = imageTask.execute("image/upload/" + userId, subImg1, userId).get();
                JSONObject jsonObject = new JSONObject(response);
                this.subImg1 = jsonObject.getString("data");
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
            subImg2 = createCopyAndReturnRealPath(getApplicationContext(), selectedSub2Image);
            System.out.println("아이디:" + userId);
            System.out.println("이미지 경로: " + mainImg);
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
                String response = imageTask.execute("image/upload/" + userId, subImg2, userId).get();
                JSONObject jsonObject = new JSONObject(response);
                this.subImg2 = jsonObject.getString("data");
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
            subImg3 = createCopyAndReturnRealPath(getApplicationContext(), selectedSub3Image);
            System.out.println("아이디:" + userId);
            System.out.println("이미지 경로: " + mainImg);
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
                String response = imageTask.execute("image/upload/" + userId, subImg3, userId).get();
                JSONObject jsonObject = new JSONObject(response);
                this.subImg3 = jsonObject.getString("data");
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
            subImg4 = createCopyAndReturnRealPath(getApplicationContext(), selectedSub4Image);
            System.out.println("아이디:" + userId);
            System.out.println("이미지 경로: " + mainImg);
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
                String response = imageTask.execute("image/upload/" + userId, subImg4, userId).get();
                JSONObject jsonObject = new JSONObject(response);
                this.subImg4 = jsonObject.getString("data");
                pictureId++;
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(requestCode == GET_GALLERY_BILL1_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            selectedBill1Image = data.getData();
            writingBillImg1.setImageURI(selectedBill1Image);
            billImg1 = createCopyAndReturnRealPath(getApplicationContext(), selectedBill1Image);
            System.out.println("아이디:" + userId);
            System.out.println("이미지 경로: " + billImg1);
            billImgFrame1.setVisibility(View.VISIBLE);
            billImg1Delete.setVisibility(View.VISIBLE);
            writingBillImg1.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0,0,view.getWidth(),view.getHeight(), 12);
                }
            });
            writingBillImg1.setClipToOutline(true);
            add_receipt.setImageResource(R.drawable.writing_btn_receipt1);
            ImageTask imageTask = new ImageTask();
            try {
                String response = imageTask.execute("image/upload/" + userId, billImg1, userId).get();
                JSONObject jsonObject = new JSONObject(response);
                this.billImg1 = jsonObject.getString("data");
                billId++;
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(requestCode == GET_GALLERY_BILL2_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            selectedBill2Image = data.getData();
            writingBillImg2.setImageURI(selectedBill2Image);
            billImg2 = createCopyAndReturnRealPath(getApplicationContext(), selectedBill2Image);
            System.out.println("아이디:" + userId);
            System.out.println("이미지 경로: " + billImg2);
            billImgFrame2.setVisibility(View.VISIBLE);
            billImg2Delete.setVisibility(View.VISIBLE);
            writingBillImg2.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0,0,view.getWidth(),view.getHeight(), 12);
                }
            });
            writingBillImg2.setClipToOutline(true);
            add_receipt.setImageResource(R.drawable.writing_btn_receipt2);
            ImageTask imageTask = new ImageTask();
            try {
                String response = imageTask.execute("image/upload/" + userId, billImg2, userId).get();
                JSONObject jsonObject = new JSONObject(response);
                this.billImg2 = jsonObject.getString("data");
                billId++;
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