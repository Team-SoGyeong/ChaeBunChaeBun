package com.example.chaebunchaebun;

import android.content.Intent;
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

public class RealCompleteDialogFragment extends DialogFragment {
    public static final String TAG_EVENT_DIALOG = "dialog_event";

    String userId, title, nickname = null;
    int postId = 0;

    public RealCompleteDialogFragment() {}
    public static RealCompleteDialogFragment getInstance() {
        RealCompleteDialogFragment e = new RealCompleteDialogFragment();

        return e;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View okDialog = inflater.inflate(R.layout.dialog_home_complete_complete, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageButton complete = (ImageButton) okDialog.findViewById(R.id.btn_complete);

        Bundle mArgs = getArguments();
        userId = mArgs.getString("userId");
        title = mArgs.getString("title");
        nickname = mArgs.getString("nickname");
        postId = mArgs.getInt("postId");

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PutTask completeTask = new PutTask();
                completeTask.execute("common/processed/" + String.valueOf(postId) + "/" + userId, String.valueOf(postId), userId);
                getActivity().startActivity(new Intent(getActivity(), NavigationActivity.class));
            }
        });
        setCancelable(false);

        return okDialog;
    }
}
