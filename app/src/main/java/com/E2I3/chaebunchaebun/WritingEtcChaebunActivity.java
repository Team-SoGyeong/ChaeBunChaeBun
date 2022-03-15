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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutionException;

public class WritingEtcChaebunActivity extends AppCompatActivity {
    private final int GET_GALLARY_MAIN_IMAGE = 200;
    private final int GET_GALLARY_SUB1_IMAGE = 201;
    private final int GET_GALLARY_SUB2_IMAGE = 202;
    private final int GET_GALLARY_SUB3_IMAGE = 203;
    private final int GET_GALLARY_SUB4_IMAGE = 204;
    private final int GET_GALLERY_BILL1_IMAGE = 205;
    private final int GET_GALLERY_BILL2_IMAGE = 206;
    int pictureId = 0;
    int billId = 0;
    private TextView toastText;
    private Toast toast;

    HorizontalScrollView picture_view, bill_view;
    LinearLayout mainImgFrame, subImgFrame1, subImgFrame2, subImgFrame3, subImgFrame4, billImgFrame1, billImgFrame2;
    ImageButton add_picture, add_receipt;
    ImageView back, writing, btn_back, etcMainImg, etcSubImg1, etcSubImg2, etcSubImg3, etcSubImg4, writingBillImg1, writingBillImg2;
    TextView inputContentCount;
    EditText inputVegetable, inputTitle, inputContent, inputAmount, inputGetPrice, inputCall;
    Spinner date_spinner, amount_spinner;
    String date_arr[] = {"1일 전", "2일 전", "3일 전", "일주일 이내", "2주 이내"};
    String amount_arr[] ={"kg","g","개"};
    String str, amount_str, userId = null;
    String amount, totalPrice, people = "1";
    String mainImg, subImg1, subImg2, subImg3, subImg4, billImg1, billImg2 = null;
    int categoryId;
    int locationCode = 0;
    boolean isAmount = false;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writing_chaebun_etc_frame);

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

        inputVegetable = (EditText) findViewById(R.id.input_vegetable);
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


        etcMainImg = (ImageView) findViewById(R.id.etc_main_img);
        etcSubImg1 = (ImageView) findViewById(R.id.etc_sub_img1);
        etcSubImg2 = (ImageView) findViewById(R.id.etc_sub_img2);
        etcSubImg3 = (ImageView) findViewById(R.id.etc_sub_img3);
        etcSubImg4 = (ImageView) findViewById(R.id.etc_sub_img4);
        writingBillImg1 = (ImageView) findViewById(R.id.etc_bill1_img);
        writingBillImg2 = (ImageView) findViewById(R.id.etc_bill2_img);

        add_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pictureId++;
                picture_view.setVisibility(View.VISIBLE);
                switch (pictureId){
                    case 1:
                        Intent mainImgIntent = new Intent(Intent.ACTION_PICK);
                        mainImgIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(mainImgIntent, GET_GALLARY_MAIN_IMAGE);
                        etcMainImg.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        Intent subImg1Intent = new Intent(Intent.ACTION_PICK);
                        subImg1Intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(subImg1Intent, GET_GALLARY_SUB1_IMAGE);
                        etcSubImg1.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        Intent subImg2Intent = new Intent(Intent.ACTION_PICK);
                        subImg2Intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(subImg2Intent, GET_GALLARY_SUB2_IMAGE);
                        etcSubImg2.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        Intent subImg3Intent = new Intent(Intent.ACTION_PICK);
                        subImg3Intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(subImg3Intent, GET_GALLARY_SUB3_IMAGE);
                        etcSubImg3.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        Intent subImg4Intent = new Intent(Intent.ACTION_PICK);
                        subImg4Intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(subImg4Intent, GET_GALLARY_SUB4_IMAGE);
                        etcSubImg4.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        add_receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billId++;
                bill_view.setVisibility(View.VISIBLE);
                switch (billId){
                    case 1:
                        Intent bill1ImgIntent = new Intent(Intent.ACTION_PICK);
                        bill1ImgIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(bill1ImgIntent, GET_GALLERY_BILL1_IMAGE);
                        break;
                    case 2:
                        Intent bill2ImgIntent = new Intent(Intent.ACTION_PICK);
                        bill2ImgIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(bill2ImgIntent, GET_GALLERY_BILL2_IMAGE);
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
                isAmount = true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        back = (ImageView) findViewById(R.id.id_back);
        writing = (ImageView) findViewById(R.id.btn_next);
        btn_back = (ImageView) findViewById(R.id.btn_back);

        date_spinner = (Spinner) findViewById(R.id.date_spinner2);
        amount_spinner = (Spinner) findViewById(R.id.amount_spinner2);

        //스피너 이벤트
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
                        || inputAmount.getText().toString().isEmpty() || inputGetPrice.getText().toString().isEmpty()
                        || inputCall.getText().toString().isEmpty()
                        || str.isEmpty() || amount_str.isEmpty()) {
                    toastText.setText("입력되지 않은 칸이 있어요!");
                    toast.show();
                }else {
                    Bundle args = new Bundle();
                    args.putString("inputVegetable", inputVegetable.getText().toString());
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
                    args.putString("userId", userId);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GET_GALLARY_MAIN_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedMainImage = data.getData();
            etcMainImg.setImageURI(selectedMainImage);
            mainImg = createCopyAndReturnRealPath(getApplicationContext(), selectedMainImage);
            System.out.println("아이디:" + userId);
            System.out.println("이미지 경로: " + mainImg);
            mainImgFrame.setVisibility(View.VISIBLE);
            etcMainImg.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0,0,view.getWidth(),view.getHeight(), 12);
                }
            });
            etcMainImg.setClipToOutline(true);
            add_picture.setImageResource(R.drawable.writing_btn_picture1);
            ImageTask imageTask = new ImageTask();
            try {
                String response = imageTask.execute("image/upload/" + userId, mainImg, userId).get();
                JSONObject jsonObject = new JSONObject(response);
                this.mainImg = jsonObject.getString("data");
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if(requestCode == GET_GALLARY_SUB1_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri selectedMainImage = data.getData();
            etcSubImg1.setImageURI(selectedMainImage);
            subImg1 = createCopyAndReturnRealPath(getApplicationContext(), selectedMainImage);
            System.out.println("아이디:" + userId);
            System.out.println("이미지 경로: " + mainImg);
            subImgFrame1.setVisibility(View.VISIBLE);
            etcSubImg1.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0,0,view.getWidth(),view.getHeight(), 12);
                }
            });
            etcSubImg1.setClipToOutline(true);
            add_picture.setImageResource(R.drawable.writing_btn_picture2);
            ImageTask imageTask = new ImageTask();
            try {
                String response = imageTask.execute("image/upload/" + userId, subImg1, userId).get();
                JSONObject jsonObject = new JSONObject(response);
                this.subImg1 = jsonObject.getString("data");
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if(requestCode == GET_GALLARY_SUB2_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri selectedMainImage = data.getData();
            etcSubImg2.setImageURI(selectedMainImage);
            subImg2 = createCopyAndReturnRealPath(getApplicationContext(), selectedMainImage);
            System.out.println("아이디:" + userId);
            System.out.println("이미지 경로: " + mainImg);
            subImgFrame2.setVisibility(View.VISIBLE);
            etcSubImg2.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0,0,view.getWidth(),view.getHeight(), 12);
                }
            });
            etcSubImg2.setClipToOutline(true);
            add_picture.setImageResource(R.drawable.writing_btn_picture3);
            ImageTask imageTask = new ImageTask();
            try {
                String response = imageTask.execute("image/upload/" + userId, subImg2, userId).get();
                JSONObject jsonObject = new JSONObject(response);
                this.subImg2 = jsonObject.getString("data");
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if(requestCode == GET_GALLARY_SUB3_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri selectedMainImage = data.getData();
            etcSubImg3.setImageURI(selectedMainImage);
            subImg3 = createCopyAndReturnRealPath(getApplicationContext(), selectedMainImage);
            System.out.println("아이디:" + userId);
            System.out.println("이미지 경로: " + mainImg);
            subImgFrame3.setVisibility(View.VISIBLE);
            etcSubImg3.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0,0,view.getWidth(),view.getHeight(), 12);
                }
            });
            etcSubImg3.setClipToOutline(true);
            add_picture.setImageResource(R.drawable.writing_btn_picture4);
            ImageTask imageTask = new ImageTask();
            try {
                String response = imageTask.execute("image/upload/" + userId, subImg3, userId).get();
                JSONObject jsonObject = new JSONObject(response);
                this.subImg3 = jsonObject.getString("data");
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if(requestCode == GET_GALLARY_SUB4_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri selectedMainImage = data.getData();
            etcSubImg4.setImageURI(selectedMainImage);
            subImg4 = createCopyAndReturnRealPath(getApplicationContext(), selectedMainImage);
            System.out.println("아이디:" + userId);
            System.out.println("이미지 경로: " + mainImg);
            subImgFrame4.setVisibility(View.VISIBLE);
            etcSubImg4.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0,0,view.getWidth(),view.getHeight(), 12);
                }
            });
            etcSubImg4.setClipToOutline(true);
            add_picture.setImageResource(R.drawable.writing_btn_picture5);
            ImageTask imageTask = new ImageTask();
            try {
                String response = imageTask.execute("image/upload/" + userId, subImg4, userId).get();
                JSONObject jsonObject = new JSONObject(response);
                this.subImg4 = jsonObject.getString("data");
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if(requestCode == GET_GALLERY_BILL1_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri selectedMainImage = data.getData();
            writingBillImg1.setImageURI(selectedMainImage);
            billImg1 = createCopyAndReturnRealPath(getApplicationContext(), selectedMainImage);
            System.out.println("아이디:" + userId);
            System.out.println("이미지 경로: " + billImg1);
            billImgFrame1.setVisibility(View.VISIBLE);
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
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if(requestCode == GET_GALLERY_BILL2_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri selectedMainImage = data.getData();
            writingBillImg2.setImageURI(selectedMainImage);
            billImg2 = createCopyAndReturnRealPath(getApplicationContext(), selectedMainImage);
            System.out.println("아이디:" + userId);
            System.out.println("이미지 경로: " + billImg2);
            billImgFrame2.setVisibility(View.VISIBLE);
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
