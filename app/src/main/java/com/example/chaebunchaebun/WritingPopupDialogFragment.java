package com.example.chaebunchaebun;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class WritingPopupDialogFragment extends DialogFragment {
    public static final String TAG_EVENT_DIALOG = "dialog_event";

    public WritingPopupDialogFragment() {}
    public static WritingPopupDialogFragment getInstance() {
        WritingPopupDialogFragment e = new WritingPopupDialogFragment();

        return e;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View checkDialog = inflater.inflate(R.layout.dialog_writing_popup, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageButton btn_ok = (ImageButton) checkDialog.findViewById(R.id.btn_ok);
        ImageButton btn_modify = (ImageButton) checkDialog.findViewById(R.id.btn_modify);

        Bundle args = getArguments();
        int categoryId = args.getInt("categoryId");
        String title = args.getString("inputTitle");
        String content = args.getString("inputContent");
        String amountString = args.getString("inputAmount");
        String getPrice = args.getString("inputGetPrice");
        String memberNum = args.getString("inputMemberNum");
        String call = args.getString("inputCall");
        String buyDate = args.getString("inputBuyDate");
        String amount_str = args.getString("inputAmountStr");
        String perPrice = args.getString("inputPerPrice");

        String bill1 = null;
        String bill2 = null;
        String img1 = args.getString("img1");
        String img2 = args.getString("img2");
        String img3 = args.getString("img3");
        String img4 = args.getString("img4");
        String img5 = args.getString("img5");

        EditText check_date = (EditText) checkDialog.findViewById(R.id.check_date);
        EditText check_member = (EditText) checkDialog.findViewById(R.id.check_member);
        EditText check_price = (EditText) checkDialog.findViewById(R.id.check_price);

        check_date.setText(buyDate);
        check_member.setText(memberNum);
        check_price.setText(perPrice);

        check_date.setEnabled(false);
        check_member.setEnabled(false);
        check_price.setEnabled(false);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(categoryId < 11) {
                    String buyDate2 = check_date.getText().toString();
                    String memberNum2 = check_member.getText().toString();
                    String perPrice2 = check_price.getText().toString();
                    PostTask postTask = new PostTask();
                    JSONObject jsonPostTransfer = new JSONObject();
                    try {
                        jsonPostTransfer.put("amount", Integer.parseInt(amountString));
                        jsonPostTransfer.put("author_id", 1);
                        jsonPostTransfer.put("buy_date", buyDate2);
                        jsonPostTransfer.put("category_id", categoryId);
                        jsonPostTransfer.put("contact", call);
                        jsonPostTransfer.put("contents", content);
                        jsonPostTransfer.put("headcount", Integer.parseInt(memberNum2));

                        String imgString = "{\"bill1\": \"" + bill1 + "\", \"bill2\": \"" + bill2 + "\", \"img1\": \"" + img1 + "\"," +
                                " \"img2\": \"" + img2 + "\", \"img3\": \"" + img3 + "\", \"img4\": \"" + img4 + "\", \"img4\": \"" + img4 + "\", \"img5\": \"" + img5 + "\"}";
                        JSONObject imgs = new JSONObject(imgString);
                        jsonPostTransfer.put("imgs", imgs);
                        jsonPostTransfer.put("per_price", Integer.parseInt(perPrice2));
                        jsonPostTransfer.put("title", title);
                        jsonPostTransfer.put("total_price", Integer.parseInt(getPrice));
                        jsonPostTransfer.put("unit", amount_str);

                        String jsonString = jsonPostTransfer.toString();
                        System.out.println(jsonString);
                        postTask.execute("posts/common", jsonString);
                        startActivity(new Intent(getActivity(), NavigationActivity.class));
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    String buyDate2 = check_date.getText().toString();
                    String memberNum2 = check_member.getText().toString();
                    String perPrice2 = check_price.getText().toString();
                    PostTask postTask = new PostTask();
                    JSONObject jsonPostTransfer = new JSONObject();
                    try {
                        jsonPostTransfer.put("amount", Integer.parseInt(amountString));
                        jsonPostTransfer.put("author_id", 1);
                        jsonPostTransfer.put("buy_date", buyDate2);
                        jsonPostTransfer.put("category_id", categoryId);
                        jsonPostTransfer.put("contact", call);
                        jsonPostTransfer.put("contents", content);
                        String ect_name = args.getString("inputVegetable");
                        jsonPostTransfer.put("ect_name", ect_name);
                        jsonPostTransfer.put("headcount", Integer.parseInt(memberNum2));

                        String imgString = "{\"bill1\": \"" + bill1 + "\", \"bill2\": \"" + bill2 + "\", \"img1\": \"" + img1 + "\"," +
                                " \"img2\": \"" + img2 + "\", \"img3\": \"" + img3 + "\", \"img4\": \"" + img4 + "\", \"img4\": \"" + img4 + "\", \"img5\": \"" + img5 + "\"}";
                        JSONObject imgs = new JSONObject(imgString);
                        jsonPostTransfer.put("imgs", imgs);
                        jsonPostTransfer.put("per_price", Integer.parseInt(perPrice2));
                        jsonPostTransfer.put("title", title);
                        jsonPostTransfer.put("total_price", Integer.parseInt(getPrice));
                        jsonPostTransfer.put("unit", amount_str);

                        String jsonString = jsonPostTransfer.toString();
                        System.out.println(jsonString);
                        postTask.execute("posts/etc", jsonString);
                        startActivity(new Intent(getActivity(), NavigationActivity.class));
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_date.setEnabled(true);
                check_member.setEnabled(true);
                check_price.setEnabled(true);
            }
        });

        setCancelable(false);

        return checkDialog;
    }
}

