package com.example.chaebunchaebun;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

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
        View view = inflater.inflate(R.layout.custom_modalbutton, container, false);
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
                //NoseeDialogFragment e = NoseeDialogFragment.getInstance();
                //e.show(getChildFragmentManager(), NoseeDialogFragment.TAG_EVENT_DIALOG);
                break;
            case R.id.list_modal_report:
                Toast.makeText(getContext(),"신고",Toast.LENGTH_SHORT).show();
                //ArticleReportDialogFragment e = ArticleReportDialogFragment.getInstance();
                //e.show(getChildFragmentManager(), ArticleReportDialogFragment.TAG_EVENT_DIALOG);
                break;
        }
        dismiss();
    }
}
