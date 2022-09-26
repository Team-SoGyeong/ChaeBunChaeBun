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

public class CommunityCmtDeleteDialogFragment extends DialogFragment {
    public static final String TAG_EVENT_DIALOG = "dialog_event";

    String userId, commentId, postId = null;

    public CommunityCmtDeleteDialogFragment() {}
    public static CommunityCmtDeleteDialogFragment getInstance() {
        CommunityCmtDeleteDialogFragment e = new CommunityCmtDeleteDialogFragment();
        return e;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View deleteDialog = inflater.inflate(R.layout.dialog_communitycmt_delete, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageButton cancel = (ImageButton) deleteDialog.findViewById(R.id.community_comment_btn_delete_cancel);
        ImageButton ok = (ImageButton) deleteDialog.findViewById(R.id.community_comment_btn_delete_ok);

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
                deleteTask.execute("community/comment/" + commentId + "/" + userId, commentId, userId);
                Bundle articleBundle = new Bundle();
                articleBundle.putString("userId", userId);
                articleBundle.putString("postId", postId);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
                FragmentTransaction communityCmtTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                CommunityArticleFragment communityArticleFragment = new CommunityArticleFragment();
                communityArticleFragment.setArguments(articleBundle);
                communityCmtTransaction.replace(R.id.community_article_frame, communityArticleFragment);
                communityCmtTransaction.commit();
            }
        });
        setCancelable(false);

        return deleteDialog;
    }
}
