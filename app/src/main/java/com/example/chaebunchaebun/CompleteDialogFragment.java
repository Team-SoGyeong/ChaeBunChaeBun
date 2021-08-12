package com.example.chaebunchaebun;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class CompleteDialogFragment extends DialogFragment {
    public static final String TAG_EVENT_DIALOG = "dialog_event";

    public CompleteDialogFragment() {}
    public static CompleteDialogFragment getInstance() {
        CompleteDialogFragment e = new CompleteDialogFragment();

        return e;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View articleCompleteDialog = inflater.inflate(R.layout.dialog_article_complete, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageButton btnComplete = (ImageButton) articleCompleteDialog.findViewById(R.id.article_dialog_complete);
        ImageButton btnCancel = (ImageButton) articleCompleteDialog.findViewById(R.id.article_dialog_cancel);
        setCancelable(false);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return articleCompleteDialog;
    }
}
