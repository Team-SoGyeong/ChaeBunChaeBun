package com.example.chaebunchaebun;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ArticleReportDialogFragment extends DialogFragment {
    public static final String TAG_EVENT_DIALOG = "dialog_event";

    public ArticleReportDialogFragment() {}
    public static ArticleReportDialogFragment getInstance() {
        ArticleReportDialogFragment e = new ArticleReportDialogFragment();
        return e;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View deleteDialog = inflater.inflate(R.layout.dialog_article_report, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageButton cancel = (ImageButton) deleteDialog.findViewById(R.id.btn_delete_cancel);
        ImageButton report = (ImageButton) deleteDialog.findViewById(R.id.btn_report);
        RadioButton money = (RadioButton) deleteDialog.findViewById(R.id.money);
        RadioButton match = (RadioButton) deleteDialog.findViewById(R.id.no_match);
        RadioButton etc = (RadioButton) deleteDialog.findViewById(R.id.etc);
        EditText reason = (EditText) deleteDialog.findViewById(R.id.report_reason);


        etc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reason.setVisibility(View.VISIBLE);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        setCancelable(false);

        return deleteDialog;
    }

}
