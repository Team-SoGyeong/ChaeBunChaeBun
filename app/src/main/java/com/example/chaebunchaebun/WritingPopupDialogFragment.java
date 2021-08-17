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

        EditText check_date = (EditText) checkDialog.findViewById(R.id.check_date);
        EditText check_member = (EditText) checkDialog.findViewById(R.id.check_member);
        EditText check_price = (EditText) checkDialog.findViewById(R.id.check_price);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NavigationActivity.class));
            }
        });

        btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        setCancelable(false);

        return checkDialog;
    }
}

