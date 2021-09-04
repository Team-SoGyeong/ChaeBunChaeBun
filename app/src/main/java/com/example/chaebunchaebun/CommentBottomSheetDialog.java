package com.example.chaebunchaebun;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CommentBottomSheetDialog extends BottomSheetDialogFragment implements View.OnClickListener{
    private ImageButton report;
    String userId, commentId, postId = null;

    public static CommentBottomSheetDialog getInstance() {
        return new CommentBottomSheetDialog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_comment_modalbtn, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        report = (ImageButton) view.findViewById(R.id.comment_modal_report);

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
            case R.id.comment_modal_report:
                Bundle args = new Bundle();
                args.putString("userId", userId);
                args.putString("commentId", commentId);
                args.putString("postId", postId);
                CommentReportDialogFragment commentReportDialogFragment = CommentReportDialogFragment.getInstance();
                commentReportDialogFragment.setArguments(args);
                commentReportDialogFragment.show(getChildFragmentManager(), CommentReportDialogFragment.TAG_EVENT_DIALOG);
                break;
        }
    }
}
