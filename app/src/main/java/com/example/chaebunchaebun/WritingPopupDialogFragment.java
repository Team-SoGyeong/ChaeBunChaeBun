package com.example.chaebunchaebun;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

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
        ImageView warning = (ImageView) checkDialog.findViewById(R.id.sample);

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

        System.out.println(categoryId + " " + title + " " + content + " " + amountString + " " + getPrice + " " + memberNum + " " + call + " " + buyDate + " " + amount_str);

        warning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), NavigationActivity.class));
            }
        });

        setCancelable(false);

        return checkDialog;
    }
}

