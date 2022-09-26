package com.E2I3.chaebunchaebun;

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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class CommentDeleteDialogFragment extends DialogFragment {
    public static final String TAG_EVENT_DIALOG = "dialog_event";

    String userId, commentId, postId = null;

    public CommentDeleteDialogFragment() {}
    public static CommentDeleteDialogFragment getInstance() {
        CommentDeleteDialogFragment e = new CommentDeleteDialogFragment();
        return e;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View deleteDialog = inflater.inflate(R.layout.dialog_comment_delete, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageButton cancel = (ImageButton) deleteDialog.findViewById(R.id.btn_delete_cancel);
        ImageButton ok = (ImageButton) deleteDialog.findViewById(R.id.btn_delete_ok);

        Bundle mArgs = getArguments();
        userId = mArgs.getString("userId");
        commentId = mArgs.getString("commentId");
        postId = mArgs.getString("postId");

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteTask deleteTask = new DeleteTask();
                deleteTask.execute("posts/comment/" + commentId + "/" + userId, commentId, userId);
                Bundle articleBundle = new Bundle();
                articleBundle.putString("userId", userId);
                articleBundle.putString("postId", postId);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
                FragmentTransaction articleTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                ArticleFragment articleFragment = new ArticleFragment();
                articleFragment.setArguments(articleBundle);
                articleTransaction.replace(R.id.bottom_frame, articleFragment);
                articleTransaction.commit();
            }
        });
        setCancelable(false);

        return deleteDialog;
    }
}
