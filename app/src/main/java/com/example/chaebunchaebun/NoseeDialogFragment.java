package com.example.chaebunchaebun;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class NoseeDialogFragment extends DialogFragment {
    public static final String TAG_EVENT_DIALOG = "dialog_event";

    public NoseeDialogFragment() {}
    public static NoseeDialogFragment getInstance() {
        NoseeDialogFragment e = new NoseeDialogFragment();

        return e;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View noseeDialog = inflater.inflate(R.layout.dialog_article_nosee, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button noseeDialogOk = (Button) noseeDialog.findViewById(R.id.location_dialog_ok);
        Button noseeDialogCancel = (Button) noseeDialog.findViewById(R.id.location_dialog_cancel);

        noseeDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        noseeDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        setCancelable(false);

        return noseeDialog;
    }
}
