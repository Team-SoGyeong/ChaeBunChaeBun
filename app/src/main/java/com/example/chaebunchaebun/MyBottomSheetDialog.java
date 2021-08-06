package com.example.chaebunchaebun;

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

public class MyBottomSheetDialog extends BottomSheetDialogFragment implements View.OnClickListener {
    public static MyBottomSheetDialog getInstance() {
        return new MyBottomSheetDialog();
    }

    private ImageButton modify;
    private ImageButton delete;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_modalbtn_custom, container, false);
        modify = (ImageButton) view.findViewById(R.id.list_modal_modify);
        delete = (ImageButton) view.findViewById(R.id.list_modal_delete);

        modify.setOnClickListener(this);
        delete.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.list_modal_modify:
                Toast.makeText(getContext(),"수정",Toast.LENGTH_SHORT).show();
                break;
            case R.id.list_modal_delete:
                Toast.makeText(getContext(),"삭제",Toast.LENGTH_SHORT).show();
                break;
        }
        dismiss();
    }
}
