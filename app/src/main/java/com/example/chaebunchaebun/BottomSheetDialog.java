package com.example.chaebunchaebun;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

public class BottomSheetDialog extends BottomSheetDialogFragment implements View.OnClickListener {
    public static BottomSheetDialog getInstance() {
        return new BottomSheetDialog();
    }

    private ImageButton nomore;
    private ImageButton report;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.modalbutton_custom, container, false);
        nomore = (ImageButton) view.findViewById(R.id.list_modal_nomore);
        report = (ImageButton) view.findViewById(R.id.list_modal_report);

        nomore.setOnClickListener(this);
        report.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.list_modal_nomore:
                Toast.makeText(getContext(),"더 이상 보지 않기",Toast.LENGTH_SHORT).show();
                break;
            case R.id.list_modal_report:
                Toast.makeText(getContext(),"신고",Toast.LENGTH_SHORT).show();
                break;
        }
        dismiss();
    }
}
