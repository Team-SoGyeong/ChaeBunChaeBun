package com.example.chaebunchaebun;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.concurrent.ExecutionException;

public class CompleteDialogFragment extends DialogFragment {
    public static final String TAG_EVENT_DIALOG = "dialog_event";
    String userId, postId = null;

    public CompleteDialogFragment() {}
    public static CompleteDialogFragment getInstance() {
        CompleteDialogFragment e = new CompleteDialogFragment();

        return e;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View articleCompleteDialog = inflater.inflate(R.layout.dialog_article_complete, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageButton btnComplete = (ImageButton) articleCompleteDialog.findViewById(R.id.article_dialog_complete);
        ImageButton btnCancel = (ImageButton) articleCompleteDialog.findViewById(R.id.article_dialog_cancel);
        setCancelable(false);

        Bundle mArgs = getArguments();
        userId = mArgs.getString("userId");
        postId = mArgs.getString("postId");

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PutTask putTask = new PutTask();
                putTask.execute("common/processed/" + postId + "/" + userId, postId, userId);
                Intent intent = new Intent(getActivity(), NavigationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("userId", userId);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(0, 0);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return articleCompleteDialog;
    }
}
