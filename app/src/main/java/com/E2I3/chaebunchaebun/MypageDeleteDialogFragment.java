package com.E2I3.chaebunchaebun;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class MypageDeleteDialogFragment extends DialogFragment {
    public static final String TAG_EVENT_DIALOG = "dialog_event";

    String userId, postId = null;

    public MypageDeleteDialogFragment() {}
    public static MypageDeleteDialogFragment getInstance() {
        MypageDeleteDialogFragment e = new MypageDeleteDialogFragment();
        return e;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View deleteDialog = inflater.inflate(R.layout.dialog_mypage_delete, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageButton cancel = (ImageButton) deleteDialog.findViewById(R.id.mypage_delete_cancel);
        ImageButton ok = (ImageButton) deleteDialog.findViewById(R.id.mypage_delete_ok);

        Bundle mArgs = getArguments();
        userId = mArgs.getString("userId");
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
                deleteTask.execute("posts/" + postId + "/" + userId, postId, userId);
                Intent intent = new Intent(getActivity(), MypageMypostingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("userId", userId);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(0, 0);
            }
        });
        setCancelable(false);

        return deleteDialog;
    }
}