package com.example.chaebunchaebun;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.content.CursorLoader;

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
    private TextView toastText;
    private Toast toast;

    ImageView back, writing, btn_back, writingMainImg, writingSubImg1, writingSubImg2, writingSubImg3, writingSubImg4;
    TextView inputPerPrice,  inputContentCount;
    EditText inputTitle, inputContent, inputAmount, inputGetPrice, inputMemberNum, inputCall;
    Spinner date_spinner, amount_spinner;
    String date_arr[] = {"1일 전", "2일 전", "3일 전", "일주일 이내", "2주 이내"};
    String amount_arr[] ={"kg","g","개"};
    String str, amount_str, userId = null;
    String amount, totalPrice, people = "1";
    String mainImg, subImg1, subImg2, subImg3, subImg4 = null;
    int categoryId, perPrice;
    boolean isPrice, isMember, isAmount = false;

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
        System.out.println("아이디:" + userId);

        inputTitle = (EditText) findViewById(R.id.input_title);
        inputContent = (EditText) findViewById(R.id.input_content);
        inputAmount = (EditText) findViewById(R.id.input_amount);
        inputGetPrice = (EditText) findViewById(R.id.input_getPrice);
        inputMemberNum = (EditText) findViewById(R.id.input_memberNum);
        inputCall = (EditText) findViewById(R.id.input_call);

        inputPerPrice = (TextView) findViewById(R.id.input_per_price);
        inputContentCount = (TextView) findViewById(R.id.input_content_count);

        writingMainImg = (ImageView) findViewById(R.id.writing_main_img);
        writingSubImg1 = (ImageView) findViewById(R.id.writing_sub_img1);
        writingSubImg2 = (ImageView) findViewById(R.id.writing_sub_img2);
        writingSubImg3 = (ImageView) findViewById(R.id.writing_sub_img3);
        writingSubImg4 = (ImageView) findViewById(R.id.writing_sub_img4);

        writingMainImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainImgIntent = new Intent(Intent.ACTION_PICK);
                mainImgIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(mainImgIntent, GET_GALLERY_MAIN_IMAGE);
            }
        });

        writingSubImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainImgIntent = new Intent(Intent.ACTION_PICK);
                mainImgIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(mainImgIntent, GET_GALLERY_SUB1_IMAGE);
            }
        });

        writingSubImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainImgIntent = new Intent(Intent.ACTION_PICK);
                mainImgIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(mainImgIntent, GET_GALLERY_SUB2_IMAGE);
            }
        });

        writingSubImg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainImgIntent = new Intent(Intent.ACTION_PICK);
                mainImgIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(mainImgIntent, GET_GALLERY_SUB3_IMAGE);
            }
        });

        writingSubImg4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainImgIntent = new Intent(Intent.ACTION_PICK);
                mainImgIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(mainImgIntent, GET_GALLERY_SUB4_IMAGE);
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
                isAmount = false;
            }

            @Override
            public void afterTextChanged(Editable editable) {
                isAmount = true;
            }
        });
        inputGetPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                totalPrice = inputGetPrice.getText().toString();
                isPrice = false;
//                if(isAmount == true && isPrice == true && isMember == true){
//                    perPrice = ((Integer.parseInt(totalPrice) / Integer.parseInt(amount)) * (Integer.parseInt(amount) / Integer.parseInt(people)));
//                    inputPerPrice.setText(String.valueOf(perPrice).toString());
//                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                isPrice = true;
            }
        });
        inputMemberNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                people = inputMemberNum.getText().toString();
                isMember = false;
            }

            @Override
            public void afterTextChanged(Editable editable) {
                isMember = true;
                if(inputMemberNum.getText().toString().equals("")){
                    inputMemberNum.setText("0");
                } else if(!inputMemberNum.getText().toString().equals("0")) {
                    if(isAmount == true && isPrice == true && isMember == true){
                        perPrice = ((Integer.parseInt(totalPrice) / Integer.parseInt(amount)) * (Integer.parseInt(amount) / Integer.parseInt(people)));
                        inputPerPrice.setText(String.valueOf(perPrice).toString());
                    }
                }
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
                    || inputMemberNum.getText().toString().isEmpty() || inputCall.getText().toString().isEmpty()
                    || str.isEmpty() || amount_str.isEmpty() || inputPerPrice.getText().toString().isEmpty()) {
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
                    args.putString("inputMemberNum", inputMemberNum.getText().toString());
                    args.putString("inputCall", inputCall.getText().toString());
                    args.putString("inputBuyDate", str);
                    args.putString("inputAmountStr", amount_str);
                    args.putString("inputPerPrice", inputPerPrice.getText().toString());
                    args.putString("img1", mainImg);
                    args.putString("img2", subImg1);
                    args.putString("img3", subImg2);
                    args.putString("img4", subImg3);
                    args.putString("img5", subImg4);
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
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GET_GALLERY_MAIN_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedMainImage = data.getData();
            writingMainImg.setImageURI(selectedMainImage);
            mainImg = createCopyAndReturnRealPath(getApplicationContext(), selectedMainImage);
            System.out.println("아이디:" + userId);
            System.out.println("이미지 경로: " + mainImg);
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
        } else if(requestCode == GET_GALLERY_SUB1_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri selectedMainImage = data.getData();
            writingSubImg1.setImageURI(selectedMainImage);
            subImg1 = createCopyAndReturnRealPath(getApplicationContext(), selectedMainImage);
            System.out.println("아이디:" + userId);
            System.out.println("이미지 경로: " + mainImg);
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
        } else if(requestCode == GET_GALLERY_SUB2_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri selectedMainImage = data.getData();
            writingSubImg2.setImageURI(selectedMainImage);
            subImg2 = createCopyAndReturnRealPath(getApplicationContext(), selectedMainImage);
            System.out.println("아이디:" + userId);
            System.out.println("이미지 경로: " + mainImg);
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
        } else if(requestCode == GET_GALLERY_SUB3_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri selectedMainImage = data.getData();
            writingSubImg3.setImageURI(selectedMainImage);
            subImg3 = createCopyAndReturnRealPath(getApplicationContext(), selectedMainImage);
            System.out.println("아이디:" + userId);
            System.out.println("이미지 경로: " + mainImg);
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
        } else if(requestCode == GET_GALLERY_SUB4_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri selectedMainImage = data.getData();
            writingSubImg4.setImageURI(selectedMainImage);
            subImg4 = createCopyAndReturnRealPath(getApplicationContext(), selectedMainImage);
            System.out.println("아이디:" + userId);
            System.out.println("이미지 경로: " + mainImg);
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