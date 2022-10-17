package com.E2I3.chaebunchaebun;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class MypageBottomSheetDialog extends BottomSheetDialogFragment implements View.OnClickListener {
    private ImageButton nomore;
    private ImageButton report;

    String userId, postId = null;
    public static MypageBottomSheetDialog getInstance() {
        return new MypageBottomSheetDialog();
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_mypage_modalbtn, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        nomore = (ImageButton) view.findViewById(R.id.mypage_modal_nomore);
        report = (ImageButton) view.findViewById(R.id.mypage_modal_report);

        Bundle mArgs = getArguments();
        userId = mArgs.getString("userId");
        postId = mArgs.getString("postId");

        nomore.setOnClickListener(this);
        report.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mypage_modal_nomore:
                Bundle args = new Bundle();
                args.putString("userId", userId);
                args.putString("postId", postId);
                MypageNoseeDialogFragment e = MypageNoseeDialogFragment.getInstance();
                e.setArguments(args);
                e.show(getChildFragmentManager(), MypageNoseeDialogFragment.TAG_EVENT_DIALOG);
                break;
            case R.id.mypage_modal_report:
                Bundle reportArgs = new Bundle();
                reportArgs.putString("userId", userId);
                reportArgs.putString("postId", postId);
                MypageReportDialogFragment mypageReportDialogFragment = MypageReportDialogFragment.getInstance();
                mypageReportDialogFragment.setArguments(reportArgs);
                mypageReportDialogFragment.show(getChildFragmentManager(), ArticleReportDialogFragment.TAG_EVENT_DIALOG);
                break;
        }
    }
}