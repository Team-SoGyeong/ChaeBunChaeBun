package com.E2I3.chaebunchaebun;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CommunityCmtBottomSheetDialog extends BottomSheetDialogFragment implements View.OnClickListener {
    private ImageButton report;
    String userId, commentId, postId = null;

    public static CommunityCmtBottomSheetDialog getInstance() {
        return new CommunityCmtBottomSheetDialog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_communitycmt_modalbtn, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        report = (ImageButton) view.findViewById(R.id.communitycmt_modal_report);

        Bundle mArgs = getArguments();
        userId = mArgs.getString("userId");
        commentId = mArgs.getString("commentId");
        postId = mArgs.getString("postId");

        report.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.communitycmt_modal_report:
                Bundle args = new Bundle();
                args.putString("userId", userId);
                args.putString("commentId", commentId);
                args.putString("postId", postId);
                CommunityCmtReportDialogFragment communityCmtReportDialogFragment = CommunityCmtReportDialogFragment.getInstance();
                communityCmtReportDialogFragment.setArguments(args);
                communityCmtReportDialogFragment.show(getChildFragmentManager(), CommunityCmtReportDialogFragment.TAG_EVENT_DIALOG);
                break;
        }
    }
}
