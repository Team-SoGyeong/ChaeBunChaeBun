package com.example.chaebunchaebun;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class UncompleteDialogFragment extends DialogFragment {
    public static final String TAG_EVENT_DIALOG = "dialog_event";

    public UncompleteDialogFragment() {}
    public static UncompleteDialogFragment getInstance() {
        UncompleteDialogFragment e = new UncompleteDialogFragment();

        return e;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View unCompleteDialog = inflater.inflate(R.layout.dialog_home_uncomplete, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageButton fin = (ImageButton) unCompleteDialog.findViewById(R.id.btn_fin_now);
        ImageButton donate = (ImageButton) unCompleteDialog.findViewById(R.id.btn_donate);

        fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), NavigationActivity.class));
            }
        });
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UncompleteFinDialogFragment e = UncompleteFinDialogFragment.getInstance();
                e.show(getChildFragmentManager(), UncompleteFinDialogFragment.TAG_EVENT_DIALOG);
                onStop();
            }
        });
        setCancelable(false);

        return unCompleteDialog;
    }
}
