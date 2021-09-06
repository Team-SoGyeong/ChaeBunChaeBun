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

public class WarningDialogFragment extends DialogFragment {
    public static final String TAG_EVENT_DIALOG = "dialog_event";

    String userId = null;

    public WarningDialogFragment() {}
    public static WarningDialogFragment getInstance() {
        WarningDialogFragment e = new WarningDialogFragment();

        return e;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View warningDialog = inflater.inflate(R.layout.dialog_writing_warning, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView warning = (ImageView) warningDialog.findViewById(R.id.warning);

        Bundle mArgs = getArguments();
        userId = mArgs.getString("userId");

        warning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SelectCategoryActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
                dismiss();
            }
        });

        setCancelable(false);

        return warningDialog;
    }
}