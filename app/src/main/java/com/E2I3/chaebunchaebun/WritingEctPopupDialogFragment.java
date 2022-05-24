package com.E2I3.chaebunchaebun;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class WritingEctPopupDialogFragment extends DialogFragment {
    public static final String TAG_EVENT_DIALOG = "dialog_event";

    public WritingEctPopupDialogFragment() {
        // Required empty public constructor
    }

    public static WritingEctPopupDialogFragment getInstance() {
        WritingEctPopupDialogFragment e = new WritingEctPopupDialogFragment();

        return e;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View checkEctDialog = inflater.inflate(R.layout.dialog_writing_ect_popup, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageButton btn_ok = (ImageButton) checkEctDialog.findViewById(R.id.btn_ok);
        ImageButton btn_modify = (ImageButton) checkEctDialog.findViewById(R.id.btn_modify);

        Bundle args = getArguments();
        String userId = args.getString("userId");
        int categoryId = args.getInt("categoryId");
        int locationCode = args.getInt("locationCode");
        String title = args.getString("inputTitle");
        String content = args.getString("inputContent");
        String amountString = args.getString("inputAmount");
        String getPrice = args.getString("inputGetPrice");
        String call = args.getString("inputCall");
        String buyDate = args.getString("inputBuyDate");
        String amount_str = args.getString("inputAmountStr");
        String ect_name = args.getString("inputVegetable");

        String bill1 = args.getString("bill1");
        String bill2 = args.getString("bill2");
        String img1 = args.getString("img1");
        String img2 = args.getString("img2");
        String img3 = args.getString("img3");
        String img4 = args.getString("img4");
        String img5 = args.getString("img5");

        EditText check_date = (EditText) checkEctDialog.findViewById(R.id.check_date);
        TextView check_amount_str = (TextView) checkEctDialog.findViewById(R.id.check_amount_str);
        EditText check_amount = (EditText) checkEctDialog.findViewById(R.id.check_amount);
        EditText check_price = (EditText) checkEctDialog.findViewById(R.id.check_price);
        EditText check_category = (EditText) checkEctDialog.findViewById(R.id.check_category);

        check_date.setText(buyDate);
        check_amount.setText(amountString);
        check_amount_str.setText(amount_str);
        check_price.setText(getPrice);
        check_category.setText(ect_name);

        check_date.setEnabled(false);
        check_amount.setEnabled(false);
        check_price.setEnabled(false);
        check_category.setEnabled(false);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buyDate2 = check_date.getText().toString();
                String memberNum2 = "10000";
                String perPrice2 = "10000";
                PostTask postTask = new PostTask();
                JSONObject jsonPostTransfer = new JSONObject();
                try {
                    jsonPostTransfer.put("amount", Integer.parseInt(amountString));
                    jsonPostTransfer.put("author_id", Integer.parseInt(userId));
                    jsonPostTransfer.put("buy_date", buyDate2);
                    jsonPostTransfer.put("category_id", categoryId);
                    jsonPostTransfer.put("contact", call);
                    jsonPostTransfer.put("contents", content);
                    jsonPostTransfer.put("ect_name", ect_name);
                    jsonPostTransfer.put("post_addr", locationCode);

                    String imgString = "{\"bill1\": \"" + bill1 + "\", \"bill2\": \"" + bill2 + "\", \"img1\": \"" + img1 + "\"," +
                            " \"img2\": \"" + img2 + "\", \"img3\": \"" + img3 + "\", \"img4\": \"" + img4 + "\", \"img4\": \"" + img4 + "\", \"img5\": \"" + img5 + "\"}";
                    JSONObject imgs = new JSONObject(imgString);
                    jsonPostTransfer.put("imgs", imgs);
                    jsonPostTransfer.put("per_price", Integer.parseInt(perPrice2));
                    jsonPostTransfer.put("title", title);
                    jsonPostTransfer.put("total_price", Integer.parseInt(getPrice));
                    jsonPostTransfer.put("unit", amount_str);

                    String jsonString = jsonPostTransfer.toString();
                    Log.i("jsonString", jsonString);
                    postTask.execute("posts/etc", jsonString);

                    Bundle args = new Bundle();
                    args.putString("userId", userId);
                    WritingReceiptPopupDialogFragment e = WritingReceiptPopupDialogFragment.getInstance();
                    e.setArguments(args);
                    e.show(getChildFragmentManager(), WritingReceiptPopupDialogFragment.TAG_EVENT_DIALOG);
                    onStop();
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        setCancelable(false);

        return checkEctDialog;
    }
}