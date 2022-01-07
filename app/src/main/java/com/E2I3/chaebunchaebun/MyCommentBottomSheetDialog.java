package com.E2I3.chaebunchaebun;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class MyCommentBottomSheetDialog extends BottomSheetDialogFragment implements View.OnClickListener{
    public static MyCommentBottomSheetDialog getInstance() {
        return new MyCommentBottomSheetDialog();
    }

    private ImageButton delete;

    String userId, commentId, postId = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_mycomment_modalbtn, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        delete = (ImageButton) view.findViewById(R.id.comment_modal_delete);

        Bundle mArgs = getArguments();
        userId = mArgs.getString("userId");
        commentId = mArgs.getString("commentId");
        postId = mArgs.getString("postId");

        delete.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.comment_modal_delete:
                Bundle args = new Bundle();
                args.putString("userId", userId);
                args.putString("commentId", commentId);
                args.putString("postId", postId);
                CommentDeleteDialogFragment commentDeleteDialogFragment = CommentDeleteDialogFragment.getInstance();
                commentDeleteDialogFragment.setArguments(args);
                commentDeleteDialogFragment.show(getChildFragmentManager(), CommentDeleteDialogFragment.TAG_EVENT_DIALOG);
                break;
        }
    }
}
